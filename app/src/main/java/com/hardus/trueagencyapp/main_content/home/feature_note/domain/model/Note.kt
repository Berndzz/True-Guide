package com.hardus.trueagencyapp.main_content.home.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hardus.trueagencyapp.ui.theme.BabyBlue
import com.hardus.trueagencyapp.ui.theme.DefaultColor
import com.hardus.trueagencyapp.ui.theme.LightGreen
import com.hardus.trueagencyapp.ui.theme.RedOrange
import com.hardus.trueagencyapp.ui.theme.RedPink
import com.hardus.trueagencyapp.ui.theme.Violet


enum class NoteCategory(val categoryName: String) {
    PROSPECT("Prospect"),
    RECRUIT("Recruit"),
    Category("");
    // Tambahkan kategori lain jika ada

    override fun toString(): String {
        return categoryName
    }
}
@Entity
data class Note(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "category") val category: String,
    @PrimaryKey val id: Int? = null
){
    companion object {
        val noteColors = listOf(DefaultColor, RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)