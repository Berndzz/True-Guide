package com.hardus.trueagencyapp.main_content.home.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
    class CategoryProspect(orderType: OrderType) : NoteOrder(orderType)
    class CategoryRecruit(orderType: OrderType) : NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
            is CategoryProspect -> CategoryProspect(orderType)
            is CategoryRecruit -> CategoryRecruit(orderType)
        }
    }



}