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
        image = R.drawable.first_onboarrding,
        title = "True Partner",
        description = "TrueGuide adalah aplikasi mobile untuk parar bisnis partner di TrueAgency. Training, info kegiatan, dan notepad untuk pengembangan."
    )

    object Second : OnBoardingPage(
        image = R.drawable.second_onboarding,
        title = "True Leader",
        description = "TrueGuide, Jadi pemimpin! Akses pelatihan, info terbaru, dan kembangkan kepemimpinanmu."
    )

    object Third : OnBoardingPage(
        image = R.drawable.third_onboarding,
        title = "True Gether",
        description = "TrueGuide, tetap terhubung! Notepad untuk catatan prospek, rekrutmen, dan pertumbuhan."
    )


}