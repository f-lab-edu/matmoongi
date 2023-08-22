@file:OptIn(ExperimentalFoundationApi::class)

package com.matmoongi.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matmoongi.Destination
import com.matmoongi.R
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant
import com.matmoongi.data.dataclass.SearchRestaurant
import com.matmoongi.restaurantCards.RestaurantCard

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    emitEvent: (SearchViewEvent) -> Unit,
    onNavigateToMyPage: () -> Unit,
    onNavigateToMap: (MapRestaurant, Coordinate) -> Unit,
) {
    val pagerState = rememberPagerState()
    val nextRoute = uiState.nextRoute
    val userLocation = uiState.userLocation

    LaunchedEffect(userLocation) {
        emitEvent(SearchViewEvent.OnUserLocationChanged)
    }

    LaunchedEffect(nextRoute) {
        if (nextRoute != null) {
            emitEvent(SearchViewEvent.OnNavigateTo(nextRoute))
        }

        when (nextRoute) {
            Destination.MY_PAGE_SCREEN -> onNavigateToMyPage()
            Destination.MAP_SCREEN -> uiState.mapRestaurant?.let { restaurant ->
                onNavigateToMap(restaurant, uiState.userLocation)
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TopBar(emitEvent)
        RefreshTextButton(emitEvent)
        RestaurantCardsList(pagerState, uiState.restaurantList, emitEvent)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(onClickUserButton: (SearchViewEvent) -> Unit) {
    CenterAlignedTopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { onClickUserButton(SearchViewEvent.OnTapUserIcon) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                )
            }
        },
        colors = centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
private fun RefreshTextButton(onClickRefreshButton: (SearchViewEvent) -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 36.dp, start = 16.dp)
            .clickable(enabled = true) {
                onClickRefreshButton(SearchViewEvent.OnTapRefreshUserLocationButton)
            },
    ) {
        Text(
            text = stringResource(R.string.search_in_current_location),
        )
        Image(painter = painterResource(id = R.drawable.ic_refresh), contentDescription = null)
    }
}

@ExperimentalFoundationApi
@Composable
private fun RestaurantCardsList(
    pagerState: PagerState,
    searchRestaurantList: List<SearchRestaurant>,
    emitEvent: (SearchViewEvent) -> Unit,
) {
    HorizontalPager(
        pageCount = searchRestaurantList.size,
        modifier = Modifier
            .padding(top = 20.dp)
            .wrapContentSize(),
        state = pagerState,
        contentPadding = PaddingValues(8.dp),
    ) {
        RestaurantCard(searchRestaurant = searchRestaurantList[it], emitEvent)
    }
//    TODO("리뷰카드 재설계 필요)
//    ReviewCard(searchRestaurantList[pagerState.settledPage].review)
}
