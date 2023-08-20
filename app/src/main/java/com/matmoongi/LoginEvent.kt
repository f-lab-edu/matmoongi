package com.matmoongi

sealed class LoginEvent {
    data class Login(val loginStatus: LoginStatus) : LoginEvent()
    data class Logout(val loginStatus: LoginStatus) : LoginEvent()
}
