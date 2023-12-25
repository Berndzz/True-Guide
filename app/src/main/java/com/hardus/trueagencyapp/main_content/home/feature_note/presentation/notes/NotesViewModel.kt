package com.hardus.trueagencyapp.main_content.home.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.Note
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.NoteCategory
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.NoteUseCases
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.NoteOrder
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery


    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        getNotes(state.value.noteOrder) // Jika Anda ingin segera memfilter setelah pencarian diubah
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return // Jika order tidak berubah, tidak ada yang perlu dilakukan
                }
                // Hanya perlu memanggil getNotes jika order benar-benar berubah
                getNotes(event.noteOrder)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach { notes ->

            // Terapkan filter kategori terlebih dahulu
            val filteredNotes = when (noteOrder) {
                is NoteOrder.CategoryProspect -> notes.filter { it.category == NoteCategory.PROSPECT.categoryName }
                is NoteOrder.CategoryRecruit -> notes.filter { it.category == NoteCategory.RECRUIT.categoryName }
                else -> notes
            }

            // Kemudian terapkan filter pencarian
            val searchQuery = _searchQuery.value
            val filteredBySearchQuery = if (searchQuery.isEmpty()) {
                filteredNotes
            } else {
                filteredNotes.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                            it.content.contains(searchQuery, ignoreCase = true)
                }
            }

            // Akhirnya, urutkan catatan yang telah difilter
            val sortedNotes = when (noteOrder.orderType) {
                is OrderType.Ascending -> filteredBySearchQuery.sortedBy { it.timestamp }
                is OrderType.Descending -> filteredBySearchQuery.sortedByDescending { it.timestamp }
                else -> filteredBySearchQuery
            }

            _state.value = state.value.copy(
                notes = sortedNotes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}