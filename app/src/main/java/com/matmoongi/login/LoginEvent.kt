package com.matmoongi.login

sealed class LoginEvent {
    data class Login(val loginStatus: LoginStatus) : LoginEvent()
    data class Logout(val loginStatus: LoginStatus) : LoginEvent()
}
