package com.matmoongi.restaurantCards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.matmoongi.R
import com.matmoongi.data.Review

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .padding(top = 52.dp, start = 16.dp, end = 16.dp, bottom = 40.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(1.dp, Color.White),
    ) {
        ProfileAndRating(review)
        Text(
            text = review.text,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp).weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = stringResource(R.string.past_days_from_posted).format(review.timeSinceWriting),
            modifier = Modifier
                .padding(end = 16.dp, bottom = 16.dp)
                .align(Alignment.End),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun ProfileAndRating(review: Review) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Image(
            painter = painterResource(R.drawable.example),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding()
                .size(28.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape),
        )
        Text(
            text = review.author,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        RatingStar(review.rating)
    }
}

@Composable
fun RatingStar(rating: Double) {
    val star = ImageVector.vectorResource(id = R.drawable.ic_rating_star)
    val halfStar = ImageVector.vectorResource(id = R.drawable.ic_rating_star_half)
    val starCount = rating.toInt()
    val halfStarCount = if (rating - starCount < 0.5) 0 else 1
    Row {
        for (i in 1..starCount) {
            Image(imageVector = star, contentDescription = null)
        }
        if (halfStarCount > 0) Image(imageVector = halfStar, contentDescription = null)
    }
}
