package com.hardus.trueagencyapp.main_content.home.feature_members.presentation

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.feature_members.data.AbsentBreedMember
import com.hardus.trueagencyapp.main_content.home.feature_members.data.SubAbsentMember
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.PersonalData
import com.hardus.trueagencyapp.util.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MembersViewModel : ViewModel() {
    private val db = FirestoreService.db

    private val _uiStateTab = MutableStateFlow(
        AbsentMemberUiState(
            absentBreedsMember = listOf(
                AbsentBreedMember(idAbsent = "1", name = "Q1"),
                AbsentBreedMember(idAbsent = "2", name = "Q2"),
                AbsentBreedMember(idAbsent = "3", name = "Q3"),
                AbsentBreedMember(idAbsent = "4", name = "Mentality & Attitude"),
                AbsentBreedMember(idAbsent = "5", name = "PRU Sales Academy"),
                AbsentBreedMember(idAbsent = "6", name = "Product & Knowledge Academy"),
                AbsentBreedMember(idAbsent = "7", name = "Sales Skill")
            ),
            selectedBreedMember = null // atau inisialisasi dengan breed pertama jika diinginkan
        )
    )
    val uiStateTab: StateFlow<AbsentMemberUiState> = _uiStateTab

    private val _isLoadingTab = MutableStateFlow(true)

    private val _uiStateMember = MutableStateFlow(MemberUiState())
    val uiStateMember: StateFlow<MemberUiState> = _uiStateMember.asStateFlow()
    val isLoading = mutableStateOf(true)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    init {
        val initialBreed = _uiStateTab.value.absentBreedsMember.firstOrNull { it.name == "Q1" }
        if (initialBreed != null) {
            _uiStateTab.value = _uiStateTab.value.copy(selectedBreedMember = initialBreed)
        }
    }

    fun loadScansForUnit(unitName: String,userIdAsal:String) {
        isLoading.value = true
        val scansCollection = db.collection("organization").document(unitName)
            .collection("members").document(userIdAsal)
            .collection("PersonalData").document("Scans").collection("idScans")

        scansCollection.get()
            .addOnSuccessListener { documents ->
                val breedsUpdate = _uiStateTab.value.absentBreedsMember.toMutableList()
                for (document in documents) {
                    val subAbsentData = document.data
                    val subAbsent = SubAbsentMember(
                        idUser = subAbsentData["idUser"] as? String,
                        idSubAbsent = document.id,
                        headerTitle = subAbsentData["namaAcara"] as? String ?: "",
                        tabItem = subAbsentData["tabName"] as? String ?: "",
                        bodyTitle = subAbsentData["program"] as? String ?: "",
                        date = subAbsentData["tanggalAcara"] as? String ?: "",
                        location = subAbsentData["lokasi"] as? String ?: "",
                        present = (subAbsentData["kehadiran"] as? String ?: "") == "Hadir"
                    )
                    // Cari AbsentBreed dengan tabName yang sesuai dan perbarui subAbsentList
                    val breedIndex = breedsUpdate.indexOfFirst { it.name == subAbsent.tabItem }
                    if (breedIndex != -1) {
                        breedsUpdate[breedIndex] = breedsUpdate[breedIndex].copy(
                            subAbsentList = breedsUpdate[breedIndex].subAbsentList + subAbsent
                        )
                    }
                }
                viewModelScope.launch {
                    _uiStateTab.update { currentState ->
                        currentState.copy(absentBreedsMember = breedsUpdate)
                    }
                    _isLoadingTab.value = false
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                viewModelScope.launch {
                    _isLoadingTab.value = false
                }
            }
    }

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

    fun setSelectedBreedMember(breed: AbsentBreedMember) {
        _uiStateTab.update { it.copy(selectedBreedMember = breed) }
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

    data class AbsentMemberUiState(
        val absentBreedsMember: List<AbsentBreedMember> = emptyList(),
        val selectedBreedMember: AbsentBreedMember? = null
    )
}