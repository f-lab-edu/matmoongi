package com.matmoongi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matmoongi.R

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 120.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth().height(180.dp),
        )
        Text(
            text = stringResource(R.string.matmoongi),
            modifier = Modifier.fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.bagel_fat_one_regular)),
            fontSize = 64.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.catch_phrase),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(id = R.drawable.ic_naver_login),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().padding(top = 120.dp),
        )
        Text(
            text = stringResource(R.string.skip_login),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
