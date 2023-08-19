package com.matmoongi.restaurantCards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.matmoongi.data.dataclass.SearchRestaurant
import com.matmoongi.theme.PINKY_RED

@Composable
fun RestaurantCard(searchRestaurant: SearchRestaurant) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.52f)
            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(1.dp, Color.White),
    ) {
        Column() {
            ImageArea(searchRestaurant)
            TextArea(searchRestaurant)
        }
    }
}

@Composable
private fun ColumnScope.ImageArea(searchRestaurant: SearchRestaurant) {
    Box(
        modifier = Modifier
            .weight(0.7f)
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        if (
            searchRestaurant.thumbnailPhoto.isNullOrEmpty()
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.example,
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        } else {
            AsyncImage(
                model = searchRestaurant.thumbnailPhoto,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        FavoriteButton()
    }
}

@Composable
private fun BoxScope.FavoriteButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 12.dp, end = 12.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_favoirte_star_hollow),
            contentDescription = null,
        )
    }
}

@Composable
private fun ColumnScope.TextArea(searchRestaurant: SearchRestaurant) {
    Row(
        modifier = Modifier
            .weight(0.3f)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
    ) {
        RestaurantCardTextContent(searchRestaurant)
    }
}

@Composable
private fun RowScope.RestaurantCardTextContent(searchRestaurant: SearchRestaurant) {
    Column(
        modifier = Modifier
            .weight(2f)
            .padding(top = 12.dp, start = 16.dp),
    ) {
        ResponsiveText(
            text = searchRestaurant.name,
            textAlign = TextAlign.Start,
            textStyle = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            minSize = 16.sp,
        )
        Text(
            text = stringResource(R.string.distance).format(searchRestaurant.distance),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
        )
    }
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(top = 12.dp, end = 16.dp),
    ) {
        RatingText(
            searchRestaurant,
        )
        Text(
            text = stringResource(R.string.rating_count).format(searchRestaurant.ratingCount),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun RatingText(searchRestaurant: SearchRestaurant) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = PINKY_RED)) {
            append(searchRestaurant.rating)
        }
        append(stringResource(R.string.rating))
    }
    Text(
        text = annotatedString,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.End,
    )
}
