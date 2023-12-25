package com.hardus.trueagencyapp.main_content.home.feature_note.presentation.notes

import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.Note
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.NoteOrder
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val activeCategory: String? = null
)