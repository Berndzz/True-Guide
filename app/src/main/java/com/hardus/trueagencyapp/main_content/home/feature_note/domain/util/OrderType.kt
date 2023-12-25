package com.hardus.trueagencyapp.main_content.home.feature_note.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}