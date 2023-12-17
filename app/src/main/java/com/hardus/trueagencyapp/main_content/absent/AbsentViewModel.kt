package com.hardus.trueagencyapp.main_content.absent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AbsentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AbsentUiState())
    val uiState: StateFlow<AbsentUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = AbsentUiState(absentBreeds = getAbsentBreeds())
        }
    }

    private suspend fun getAbsentBreeds(): List<AbsentBreed> {
        return getAbsentBreed().first()
    }

    fun setSelectedBreed(breed: AbsentBreed) {
        _uiState.update { it.copy(selectedBreed = breed) }
    }

    data class AbsentUiState(
        val absentBreeds: List<AbsentBreed> = emptyList(),
        val selectedBreed: AbsentBreed? = null
    )
}