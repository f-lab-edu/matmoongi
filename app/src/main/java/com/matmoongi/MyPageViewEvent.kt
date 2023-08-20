package com.matmoongi

import com.matmoongi.viewmodels.MyPageMenu

sealed class MyPageViewEvent {
    data class OnLogin(val loginStatus: LoginStatus) : MyPageViewEvent()
    data class OnLogout(val loginStatus: LoginStatus) : MyPageViewEvent()
    data class OnTapMenuItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapLoginItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapLogoutItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapFavoriteItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapTermsItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapSignOutItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnPressBack(val destination: Destination) : MyPageViewEvent()
    data class OnNavigateTo(val destination: Destination) : MyPageViewEvent()
}
