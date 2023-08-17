package com.matmoongi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matmoongi.Destination
import com.matmoongi.R
import com.matmoongi.UserUiState
import com.matmoongi.UserViewEvent

@Composable
fun LoginScreen(
    uiState: UserUiState,
    emitEvent: (UserViewEvent) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    val context = LocalContext.current
    val nextRoute = uiState.nextRoute

    LaunchedEffect(nextRoute) {
        if (nextRoute != null) {
            emitEvent(UserViewEvent.OnNavigateTo(nextRoute))
        }
        when (nextRoute) {
            Destination.SEARCH_SCREEN -> onNavigateToSearch()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 120.dp, bottom = 120.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
        )
        Text(
            text = stringResource(R.string.matmoongi),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = stringResource(R.string.catch_phrase),
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.bodyMedium,
        )

        Image(
            painter = painterResource(id = R.drawable.ic_naver_login),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp)
                .clickable {
                    emitEvent(UserViewEvent.OnTapLoginButton(context))
                },
        )
        TextButton(
            onClick = { emitEvent(UserViewEvent.OnTapSkipLoginButton(Destination.SEARCH_SCREEN)) },
            modifier = Modifier
                .padding(top = 12.dp)
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = stringResource(R.string.skip_login),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
