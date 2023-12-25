package com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case

import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.InvalidNoteException
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.Note
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        if (note.category.isEmpty()) {
            throw InvalidNoteException("The category must be selected one")
        }
        repository.insertNote(note)
    }
}