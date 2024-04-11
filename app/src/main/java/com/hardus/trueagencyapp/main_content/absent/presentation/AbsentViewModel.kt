package com.hardus.trueagencyapp.main_content.absent.presentation

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.Query
import com.hardus.trueagencyapp.main_content.absent.data.AbsentBreed
import com.hardus.trueagencyapp.main_content.absent.data.SubAbsent
import com.hardus.trueagencyapp.util.FirestoreAuth
import com.hardus.trueagencyapp.util.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AbsentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AbsentUiState(
            absentBreeds = listOf(
                AbsentBreed(idAbsent = "1", name = "Q1"),
                AbsentBreed(idAbsent = "2", name = "Q2"),
                AbsentBreed(idAbsent = "3", name = "Q3"),
                AbsentBreed(idAbsent = "4", name = "Mentality & Attitude"),
                AbsentBreed(idAbsent = "5", name = "PRU Sales Academy"),
                AbsentBreed(idAbsent = "6", name = "Product & Knowledge Academy"),
                AbsentBreed(idAbsent = "7", name = "Sales Skill")
            ),
            selectedBreed = null // atau inisialisasi dengan breed pertama jika diinginkan
        )
    )
    val uiState: StateFlow<AbsentUiState> = _uiState

    // Tambahkan state untuk loading
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        val initialBreed = _uiState.value.absentBreeds.firstOrNull { it.name == "Q1" }
        if (initialBreed != null) {
            _uiState.value = _uiState.value.copy(selectedBreed = initialBreed)
        }
        loadAbsentData()  // Memanggil fungsi untuk memuat data dari Firestore saat ViewModel diinisialisasi
    }

    fun setSelectedBreed(breed: AbsentBreed) {
        _uiState.update { it.copy(selectedBreed = breed) }
    }

    private fun loadAbsentData() {
        val userId = FirestoreAuth.db.currentUser?.uid
        _isLoading.value = true
        userId?.let { uid ->
            FirestoreService.db.collection("scans").document(uid)
                .collection("idScan")
                .orderBy("tanggalAcara", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { documents ->
                    val breedsUpdate = _uiState.value.absentBreeds.toMutableList()
                    for (document in documents) {
                        val subAbsentData = document.data
                        val subAbsent = SubAbsent(
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
                        _uiState.update { currentState ->
                            currentState.copy(absentBreeds = breedsUpdate)
                        }
                        _isLoading.value = false
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                    viewModelScope.launch {
                        _isLoading.value = false
                    }
                }
        }
    }

    data class AbsentUiState(
        val absentBreeds: List<AbsentBreed> = emptyList(),
        val selectedBreed: AbsentBreed? = null
    )
}