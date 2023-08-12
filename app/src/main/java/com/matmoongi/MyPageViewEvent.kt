package com.matmoongi

import com.matmoongi.viewmodels.MyPageMenu
import com.navercorp.nid.oauth.NidOAuthLoginState

sealed class MyPageViewEvent {

    data class OnLogout(val userState: NidOAuthLoginState) : MyPageViewEvent()
    data class OnLogin(val userState: NidOAuthLoginState) : MyPageViewEvent()
    data class OnNavigateTo(val destination: Destination) : MyPageViewEvent()
    data class OnPressBack(val destination: Destination) : MyPageViewEvent()
    data class OnTapLoginItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapLogoutItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapFavoriteItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapTermsItem(val menuItem: MyPageMenu) : MyPageViewEvent()
    data class OnTapSignOutItem(val menuItem: MyPageMenu) : MyPageViewEvent()
}
