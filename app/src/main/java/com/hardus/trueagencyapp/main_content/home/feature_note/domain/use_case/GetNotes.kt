package com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case

import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.Note
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.NoteCategory
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.repository.NoteRepository
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.NoteOrder
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            val filteredNotes = when (noteOrder) {
                is NoteOrder.CategoryProspect -> notes.filter { it.category == NoteCategory.PROSPECT.categoryName }
                is NoteOrder.CategoryRecruit -> notes.filter { it.category == NoteCategory.RECRUIT.categoryName }
                else -> notes // Jika tidak ada filter kategori, gunakan semua catatan
            }

            when (noteOrder.orderType) {

                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        else -> filteredNotes
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        else -> filteredNotes
                    }
                }

            }
        }

    }

}