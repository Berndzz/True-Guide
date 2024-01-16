package com.hardus.trueagencyapp.main_content.home.feature_members.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.PersonalData
import com.hardus.trueagencyapp.util.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MembersViewModel : ViewModel() {
    private val db = FirestoreService.db
    private val _uiStateMember = MutableStateFlow(MemberUiState())
    val uiStateMember: StateFlow<MemberUiState> = _uiStateMember.asStateFlow()
    val isLoading = mutableStateOf(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadMembersForUnit(unitName: String) {
        isLoading.value = true
        db.collection("organization").document(unitName)
            .collection("members")
            .get()
            .addOnSuccessListener { documents ->
                val members = documents.mapNotNull { doc ->
                    doc.toObject(PersonalData::class.java) // Pastikan kelas PersonalData sudah didefinisikan
                }

                _uiStateMember.update { currentState ->
                    currentState.copy(memberList = members, isShowingMemberListPage = true)
                }
                isLoading.value = false
            }
            .addOnFailureListener { e ->
                _errorMessage.value = e.localizedMessage
                isLoading.value = false
            }
    }

    fun updateCurrentMember(selectedProgram: PersonalData) {
        _uiStateMember.update {
            it.copy(currentMember = selectedProgram)
        }
    }

    fun navigateToListPage() {
        _uiStateMember.update { it.copy(isShowingMemberListPage = true) }
    }

    fun navigateToDetailPage() {
        _uiStateMember.update { it.copy(isShowingMemberListPage = false) }
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message // Memperbarui _errorMessage yang merupakan MutableStateFlow
    }

    data class MemberUiState(
        val memberList: List<PersonalData> = emptyList(),
        val currentMember: PersonalData? = null,
        val isShowingMemberListPage: Boolean = true
    )
}