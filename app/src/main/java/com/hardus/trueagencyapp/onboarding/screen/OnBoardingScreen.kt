package com.hardus.trueagencyapp.onboarding.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.navigations.Route
import com.hardus.trueagencyapp.onboarding.viewmodel.OnBoardingViewModel
import com.hardus.trueagencyapp.util.OnBoardingPage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreenOne(
    navController: NavHostController,
    onboardingViewModel: OnBoardingViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color(0xFFFFC107),
            inactiveColor = Color.LightGray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),

            )
        FinishButton(
            modifier = Modifier.weight(1f),
            pagerState = pagerState
        ) {
            onboardingViewModel.saveOnBoardingState(completed = true)
            navController.popBackStack()
            navController.navigate(route = Route.screenLogin)
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = onBoardingPage.title,
            fontSize = 35.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit,
) {
    Row(
        Modifier.padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage == 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckFirstOnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Preview(showBackground = true)
@Composable
fun CheckSecondOnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

@Preview(showBackground = true)
@Composable
fun CheckThirdOnBoardingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Third)
    }
}