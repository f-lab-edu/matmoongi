package com.matmoongi

import android.content.Context

sealed class UserViewEvent {

    data class OnTapLoginButton(val context: Context) :
        UserViewEvent()

    data class OnTapSkipLoginButton(val destination: Destination) : UserViewEvent()

    data class OnAutoLogin(val context: Context) : UserViewEvent()

    data class OnNavigateTo(val destination: Destination) : UserViewEvent()
}
