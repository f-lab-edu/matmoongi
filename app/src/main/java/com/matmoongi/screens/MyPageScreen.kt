package com.matmoongi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.matmoongi.R
import com.matmoongi.viewmodels.MyPageItem

@ExperimentalMaterial3Api
@Composable
fun MyPageScreen(onclickBackButton: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        MyPageTopBar(onclickBackButton)
        LazyColumn {
            items(MyPageItem.values().size) {
                if (it % 2 == 0) {
                    MyPageItemButton(itemIndex = it, color = MaterialTheme.colorScheme.background)
                } else {
                    MyPageItemButton(
                        itemIndex = it,
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun MyPageTopBar(onclickBackButton: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.my_page),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(
                onClick = onclickBackButton,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                    )
                },
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
private fun MyPageItemButton(itemIndex: Int, color: Color) {
    TextButton(
        onClick = { TODO("마이페이지 아이템 클릭 이벤트 구현") },
        modifier = Modifier
            .fillMaxWidth()
            .background(color),
    ) {
        Text(
            text = MyPageItem.values()[itemIndex].toString(),
            modifier = Modifier.fillMaxSize(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
