package com.hardus.trueagencyapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.R

@Composable
fun AppbarAuthentication(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.20f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_banner),
            contentDescription = stringResource(R.string.top_banner),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 35.dp, end = 35.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
fun AppbarAddOne(name: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.30f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.top_banner),
            contentDescription = stringResource(R.string.top_banner),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 35.dp, end = 35.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 32.sp,
                    lineHeight = 41.6.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(700),
                    textAlign = TextAlign.Justify,
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CheckScreenAppbarAuthentication() {
    AppbarAuthentication(name = "Login")
}

@Preview(showBackground = true)
@Composable
fun CheckScreenAppbarAddOne() {
    AppbarAddOne(
        stringResource(id = R.string.forgot_password),
        stringResource(id = R.string.forgot_password_description)
    )
}