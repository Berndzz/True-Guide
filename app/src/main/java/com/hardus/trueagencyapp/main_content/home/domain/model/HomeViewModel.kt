package com.hardus.trueagencyapp.main_content.home.domain.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hardus.trueagencyapp.main_content.home.domain.AktivitasApi

class HomeViewModel : ViewModel() {
    private val TAG = "HomeViewModel"

    val isLoading = mutableStateOf(true)
    val activityProgram = mutableStateListOf<AktivitasApi>()

    init {
        fetchProgramsWithActivities("SM7")
        fetchProgramsWithActivities("PRU_SALES_ACADEMY")
        fetchProgramsWithActivities("PERSONAL_EXCELLENT_MENTALITY_ATTITUDE")
        fetchProgramsWithActivities("PRODUCT_&_KNOWLEDGE")
        fetchProgramsWithActivities("SALES_SKILL")
        // Tambahkan fungsi fetchProgramsWithActivities() untuk kategori lainnya di sini jika diperlukan
    }

    private fun fetchProgramsWithActivities(category: String) {
        val database = Firebase.database
        val myRef = database.getReference(category)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Loop melalui setiap child di bawah node kategori
                    for (childSnapshot in snapshot.children) {
                        // Ambil data dari setiap child dan tambahkan ke activityProgram
                        val aktivitas = childSnapshot.getValue(AktivitasApi::class.java)
                        aktivitas?.let {
                            activityProgram.add(it)
                        }
                    }

                    // Set isLoading menjadi false setelah mendapatkan data
                    isLoading.value = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle jika terjadi error saat mengambil data
                isLoading.value = false
                Log.e(TAG, "Failed to read value for category: $category", error.toException())
            }
        })
    }
}
