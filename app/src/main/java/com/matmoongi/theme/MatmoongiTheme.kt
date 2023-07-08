package com.matmoongi.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.matmoongi.R

@Composable
fun MatmoongiTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = matmoongiColorScheme,
        typography = matmoongiTypography,
        content = content,
    )
}

private val matmoongiColorScheme = lightColorScheme(
    primary = Color(0xFFFF8A48),
    secondary = Color(0xFFFF6565),
    primaryContainer = Color(0xFFFFD8BC),
    onPrimaryContainer = Color(0xFF595959),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
)

// TODO("compose 1.6.0에서 includeFontPadding=flase가 default, 후에 업데이트 하면 제거")
private val matmoongiTypography = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.bagel_fat_one_regular)),
        fontSize = 64.sp,
        color = Color.White,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.notosans_kr_regular)),
        fontSize = 16.sp,
        color = Color.Black,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.notosans_kr_regular)),
        fontSize = 12.sp,
        color = FONT_GRAY,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.notosans_kr_bold)),
        fontSize = 24.sp,
        color = Color.Black,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
)
