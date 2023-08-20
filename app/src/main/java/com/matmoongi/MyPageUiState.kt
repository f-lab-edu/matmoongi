package com.matmoongi

import com.matmoongi.viewmodels.MyPageMenu

sealed class MyPageUiState {
    abstract val menuList: List<MyPageMenu>
    abstract val nextRoute: Destination?

    data class LoggedIn(
        override val menuList: List<MyPageMenu> = listOf(
            MyPageMenu.Logout,
            MyPageMenu.Favorite,
            MyPageMenu.Version,
            MyPageMenu.Terms,
            MyPageMenu.SignOut,
        ),
        override val nextRoute: Destination? = null,
    ) : MyPageUiState()

    data class LoggedOut(
        override val menuList: List<MyPageMenu> = listOf(
            MyPageMenu.Login,
            MyPageMenu.Version,
            MyPageMenu.Terms,
        ),
        override val nextRoute: Destination? = null,
    ) : MyPageUiState()
}
