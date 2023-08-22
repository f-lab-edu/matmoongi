package com.matmoongi.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.matmoongi.R
import com.matmoongi.ResponsiveText
import com.matmoongi.data.dataclass.MapRestaurant
import com.matmoongi.theme.PINKY_RED

@Composable
fun SmallRestaurantCard(restaurant: MapRestaurant, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(16.dp),
            topEnd = CornerSize(16.dp),
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Row {
            ResponsiveText(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = restaurant.name,
                textAlign = TextAlign.Start,
                textStyle = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                minSize = 20.sp,
            )
            FavoriteButton()
        }
        Row {
            RatingText(restaurant)
            Text(
                text = stringResource(R.string.rating_count).format(restaurant.ratingCount),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.Bottom),
                style = MaterialTheme.typography.bodySmall,
            )
        }
        LazyRow(modifier = Modifier.padding(bottom = 12.dp)) {
            if (restaurant.photos != null) {
                items(restaurant.photos.size) {
                    AsyncImage(
                        model = restaurant.photos[it],
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 6.dp, end = 6.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_favoirte_star_hollow),
            contentDescription = null,
        )
    }
}

@Composable
private fun RatingText(restaurant: MapRestaurant) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = PINKY_RED)) {
            append(restaurant.rating)
        }
        append(stringResource(R.string.rating))
    }
    Text(
        text = annotatedString,
        modifier = Modifier.padding(start = 12.dp),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
    )
}
