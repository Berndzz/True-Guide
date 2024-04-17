package com.hardus.trueagencyapp.util

import androidx.annotation.DrawableRes
import com.hardus.trueagencyapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,
    val title:String,
    val description: String
) {
    object First : OnBoardingPage(
        image= R.drawable.first_onboarrding,
        title  = "True Partner",
        description = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut"
    )

    object Second : OnBoardingPage(
        image= R.drawable.second_onboarding,
        title  = "True Leader",
        description = "Ut enim ad minim veniam, quis nostrud exercitatio"
    )
    object Third : OnBoardingPage(
        image= R.drawable.third_onboarding,
        title  = "True Gether",
        description = "Ut enim ad minim veniam, quis nostrud exercitation ullamcnisi ut"
    )

}