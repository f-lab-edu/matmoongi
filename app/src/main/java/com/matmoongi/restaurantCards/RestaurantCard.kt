package com.matmoongi.restaurantCards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.matmoongi.R
import com.matmoongi.data.SearchRestaurant
import com.matmoongi.theme.RATING_RED

@Composable
fun RestaurantCard(searchRestaurant: SearchRestaurant) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(340.dp)
            .fillMaxHeight(),
        border = BorderStroke(1.dp, Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column() {
            ImageArea(searchRestaurant)
            TextArea(searchRestaurant)
        }
    }
}

@Composable
private fun ImageArea(searchRestaurant: SearchRestaurant) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = Integer.parseInt(searchRestaurant.thumbnailPhoto)),
            contentDescription = null,
        )
        FavoriteButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 12.dp),
        )
    }
}

@Composable
private fun FavoriteButton(modifier: Modifier) {
    IconButton(
        modifier = modifier,
        onClick = { /*TODO*/ },
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_favoirte_star_hollow),
            contentDescription = null,
        )
    }
}

@Composable
private fun TextArea(searchRestaurant: SearchRestaurant) {
    Row(
        modifier = Modifier
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
        Text(
            text = searchRestaurant.name,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.distance).format(searchRestaurant.distance),
            style = MaterialTheme.typography.bodySmall,
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
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp),
            text = stringResource(R.string.ratingCount).format(searchRestaurant.ratingCount),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun RatingText(searchRestaurant: SearchRestaurant) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = RATING_RED)) {
            append(searchRestaurant.rating)
        }
        append(stringResource(R.string.rating))
    }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = annotatedString,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.End,
    )
}
