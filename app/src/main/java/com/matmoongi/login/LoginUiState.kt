package com.matmoongi.login

import com.matmoongi.Destination

sealed class LoginUiState {
    abstract val userId: String?
    abstract val loginStatus: LoginStatus
    abstract val nextRoute: Destination?

    data class LoggedIn(
        override val userId: String? = null,
        override val loginStatus: LoginStatus = LoginStatus.LoggedIn,
        override val nextRoute: Destination? = null,
    ) : LoginUiState()

    data class LoggedOut(
        override val userId: String? = null,
        override val loginStatus: LoginStatus = LoginStatus.LoggedOut,
        override val nextRoute: Destination? = null,
    ) : LoginUiState()
}

enum class LoginStatus {
    LoggedIn, LoggedOut
}
