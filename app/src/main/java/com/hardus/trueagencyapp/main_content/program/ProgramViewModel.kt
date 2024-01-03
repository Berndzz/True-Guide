package com.hardus.trueagencyapp.main_content.program

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.data.Aktivitas
import com.hardus.trueagencyapp.main_content.home.data.Program
import com.hardus.trueagencyapp.main_content.home.data.ProgramWithActivities
import com.hardus.trueagencyapp.util.FirestoreService
import com.hardus.trueagencyapp.util.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel() {
    private val db = FirestoreService.db
    private val TAG = "YourViewModelTag"
    private val _uiState = MutableStateFlow(ProgramUiState())
    val uiState: StateFlow<ProgramUiState> = _uiState
    val isLoading = mutableStateOf(true)

    private var dataLoaded = false

    init {
        fetchProgramsWithActivities()
    }

    private fun fetchProgramsWithActivities() {
        if (dataLoaded) {
            return // Jika data sudah dimuat, tidak perlu memuat lagi
        }
        viewModelScope.launch {
            isLoading.value = true
            try {
                val programDocuments = db.collection("programs")
                    .orderBy("id_program")
                    .get()
                    .await()
                val programList = programDocuments.map { document ->
                    val program = document.toObject(Program::class.java)
                    val activityDocuments = document.reference.collection("aktivitas")
                        .orderBy("id_aktivitas")
                        .get().await()
                    val activities = activityDocuments.map { it.toObject(Aktivitas::class.java) }
                    ProgramWithActivities(program, activities)
                }
                _uiState.value = uiState.value.copy(programList = programList)
                isLoading.value = false
                dataLoaded = true
            } catch (e: Exception) {
                Log.w(TAG, "Error getting documents.", e)
                isLoading.value = false
            }
        }
    }

    fun updateCurrentProgram(selectedProgram: ProgramWithActivities) {
        _uiState.update {
            it.copy(currentProgram = selectedProgram)
        }
    }

    fun navigateToListPage() {
        _uiState.update { it.copy(isShowingListPage = true) }
    }

    fun navigateToDetailPage() {
        _uiState.update { it.copy(isShowingListPage = false) }
    }

    data class ProgramUiState(
        val programList: List<ProgramWithActivities> = emptyList(),
        val currentProgram: ProgramWithActivities? = null,
        val isShowingListPage: Boolean = true
    )
}