package com.matmoongi.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.matmoongi.Destination
import com.matmoongi.MyPageUiState
import com.matmoongi.MyPageViewEvent
import com.matmoongi.R
import com.matmoongi.viewmodels.MyPageMenu

@ExperimentalMaterial3Api
@Composable
fun MyPageScreen(
    uiState: MyPageUiState,
    emitEvent: (MyPageViewEvent) -> Unit,
    onTapMenuItem: (myPageMenu: MyPageMenu) -> Unit,
    onPressBack: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToFavorite: () -> Unit,
    onNavigateToTerms: () -> Unit,
) {
    val menuList = uiState.menuList
    val nextRoute = uiState.nextRoute
    LaunchedEffect(nextRoute) {
        if (nextRoute != null) {
            emitEvent(MyPageViewEvent.OnNavigateTo(nextRoute))
        }
        when (nextRoute) {
            Destination.LOGIN_SCREEN -> onNavigateToLogin()
            Destination.FAVORITE_SCREEN -> onNavigateToFavorite()
            Destination.TERMS_SCREEN -> onNavigateToTerms()
            Destination.SEARCH_SCREEN -> onNavigateToSearch()
            else -> Unit
        }
    }

    BackHandler(enabled = true) {
        onPressBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        MyPageTopBar(onPressBack)
        LazyColumn {
            items(menuList.size) {
                val backgroundColor = if (it % 2 == 0) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                }
                MenuItem(
                    myPageMenu = menuList[it],
                    color = backgroundColor,
                    onClickMenuItem = onTapMenuItem,
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun MyPageTopBar(onclickBackButton: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.my_page),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                onClick = onclickBackButton,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                    )
                },
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
private fun MenuItem(
    myPageMenu: MyPageMenu,
    color: Color,
    onClickMenuItem: (myPageMenu: MyPageMenu) -> Unit,
) {
    TextButton(
        onClick = { onClickMenuItem(myPageMenu) },
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
    ) {
        Text(
            text = myPageMenu.name,
            modifier = Modifier.fillMaxSize(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
