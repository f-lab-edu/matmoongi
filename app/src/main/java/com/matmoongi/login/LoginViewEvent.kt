package com.matmoongi.login

import android.content.Context
import com.matmoongi.Destination

sealed class LoginViewEvent {

    data class OnTapLoginButton(val context: Context) :
        LoginViewEvent()

    data class OnTapSkipLoginButton(val destination: Destination) : LoginViewEvent()

    data class OnAutoLogin(val context: Context) : LoginViewEvent()

    data class OnNavigateTo(val destination: Destination) : LoginViewEvent()
}
