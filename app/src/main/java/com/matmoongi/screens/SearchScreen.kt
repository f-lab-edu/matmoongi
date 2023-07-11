package com.matmoongi.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.matmoongi.R
import com.matmoongi.data.Review
import com.matmoongi.data.SearchRestaurant
import com.matmoongi.restaurantCards.RestaurantCard

@ExperimentalMaterial3Api
@Composable
fun SearchScreen(searchRestaurantList: List<SearchRestaurant>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        TopBar()
        RefreshTextButton()
        RestaurantCardsList(searchRestaurantList)
        ReviewCardsList()
    }
}

@ExperimentalMaterial3Api
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = null)
            }
        },
        colors = centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
fun RefreshTextButton() {
    Row(
        modifier = Modifier
            .padding(top = 36.dp, start = 16.dp)
            .clickable(enabled = true) {},
    ) {
        Text(
            text = stringResource(R.string.searchInCurrentLocation),
        )
        Image(painter = painterResource(id = R.drawable.ic_refresh), contentDescription = null)
    }
}

@Composable
fun RestaurantCardsList(searchRestaurantList: List<SearchRestaurant>) {
    LazyRow(
        modifier = Modifier
            .padding(top = 20.dp)
            .wrapContentSize(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(searchRestaurantList, itemContent = { item ->
            RestaurantCard(item)
        })
    }
}

@Composable
fun ReviewCardsList() {
    LazyRow {}
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun SearchScreenPreview(
    @PreviewParameter(SampleRestaurantCardPreview::class)
    searchRestaurantList: List<SearchRestaurant>,
) {
    SearchScreen(searchRestaurantList)
}

class SampleRestaurantCardPreview : PreviewParameterProvider<List<SearchRestaurant>> {
    override val values: Sequence<List<SearchRestaurant>> = sequenceOf(
        listOf(
            SearchRestaurant(
                "123",
                "크라이치즈버거크라이치즈버거크라이치즈버거",
                R.drawable.example.toString(),
                "4.0",
                "123",
                "123",
                false,
                review = Review("123", "123", "123", "123", "123"),
            ),
        ),
    )
}
