package com.hardus.trueagencyapp.util

data class Post(
    val idTraining: String,
    val headerTitle: String,
    val introParagraph: String,
    val imagePost: String? = null,
    val subTrainingList: List<SubTraining>
)

data class SubTraining(
    val idSubTraining: String,
    val bodyTitle: String,
    val day: String,
    val image: String? = null,
    val paragraph: String
)