package com.hardus.trueagencyapp.main_content.program

import androidx.lifecycle.ViewModel
import com.hardus.trueagencyapp.util.LocalProgramDataProvider
import com.hardus.trueagencyapp.util.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProgramViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProgramUiState(
            programList = LocalProgramDataProvider.generateFakeDataProgram(),
            currentProgram = LocalProgramDataProvider.generateFakeDataProgram().getOrElse(0) {
                LocalProgramDataProvider.defaultProgram
            }
        )
    )

    val uiState: StateFlow<ProgramUiState> = _uiState

    fun updateCurrentProgram(selectedProgram: Post) {
        _uiState.update {
            it.copy(currentProgram = selectedProgram)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }


    data class ProgramUiState(
        val programList: List<Post> = emptyList(),
        val currentProgram: Post = LocalProgramDataProvider.defaultProgram,
        val isShowingListPage: Boolean = true
    )
}