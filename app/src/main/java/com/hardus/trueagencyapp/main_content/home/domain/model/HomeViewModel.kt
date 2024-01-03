package com.hardus.trueagencyapp.main_content.home.domain.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.data.Aktivitas
import com.hardus.trueagencyapp.main_content.home.data.Program
import com.hardus.trueagencyapp.main_content.home.data.ProgramWithActivities
import com.hardus.trueagencyapp.util.FirestoreService
import com.hardus.trueagencyapp.util.await
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val db = FirestoreService.db
    private val TAG = "YourViewModelTag"
    val isLoading = mutableStateOf(true)
    val activityProgram = mutableStateListOf<ProgramWithActivities>()

    private var dataLoaded = false

    private val specificActivitiesToShow = mapOf(
        "1" to listOf(
            "iILcCZbrGg6H0QmIgXmt",  //1
            "OLxzSIZB4CVc6fe5r2AG",  //2
            "ApAqhcHT7QOjeX3LlXDr"   //3

        ),
        "2" to listOf(
            "cRCsdRprJE76m7bxLjSb",  //1
            "VuTRuOb019hN1DAweKU8",  //2
            "NS4hgrZ2wOiAh25F6QJK",  //3
            //"aEhIje5VG750Eu4PZZYc",  //4
            //"hpZ7KSxwKugG4kzdbfvH",  //5
        ),
        "3" to listOf(
            "frvBHo8Bd8DcOGyTwpFV",  //1
            "WMwurpGLJwW0jXr3p2UC",  //2
            "liA9J0TxWH0uKfjEi5wM",  //3
            "34FpKX09JiQwQaLSBGAx",  //4
            "AnBo8JdDMt8opUqkIryo",  //5
            "CZT3SrKvOBWoAQzkcfPt",  //6
            "ct8yzBN7vKHhE4oyL2PY",  //7
        ),
        "4" to listOf(
            "iQDwJPreHbhX7gBCAPfF",  //1
            "HfBJGrpwvG5ML3ExcXfT",  //2
            "yG4ojuWu0ulZDQR21lcf",  //3
            "YtVUZ6HuTo4PHYU2eFVf",  //4
            "VAevAWgtXYWEsQRstRCF",  //5
            "8fN7eoQOohYyfEHbXgBv",  //6
            "Ez3bbL1jkciXKSzNATAE",  //7
            "1DoeDU0FfbDWpmb3gZ5A",  //8
            "U1W61KORDz4Ql9vyhGCt",  //9
        ),
    )

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
                    .limit(2)
                    .get().await()
                for (programDocument in programDocuments) {
                    val program = programDocument.toObject(Program::class.java)

                    // Menggunakan 'id_program' dari objek Program
                    val programId = program.id_program.toString()

                    // Mendapatkan daftar Document ID aktivitas spesifik dari map berdasarkan ID program
                    val specificDocumentIds = specificActivitiesToShow[programId] ?: listOf()
                    val activities = mutableListOf<Aktivitas>()

                    // Untuk setiap Document ID aktivitas spesifik, lakukan query dan tambahkan ke daftar aktivitas
                    for (documentId in specificDocumentIds) {
                        val activityDocumentSnapshot =
                            programDocument.reference.collection("aktivitas")
                                .document(documentId)
                                .get().await()

                        if (activityDocumentSnapshot.exists()) {
                            val activity = activityDocumentSnapshot.toObject(Aktivitas::class.java)
                            activity?.let { activities.add(it) }
                        }
                    }

                    // Membuat objek ProgramWithActivities dan menambahkannya ke daftar
                    val programWithActivities = ProgramWithActivities(program, activities)
                    activityProgram.add(programWithActivities)
                    isLoading.value = false
                    dataLoaded = true
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error getting documents.", e)
                isLoading.value = false
            }
        }
    }
}