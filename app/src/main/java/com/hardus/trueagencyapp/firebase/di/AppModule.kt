package com.hardus.trueagencyapp.firebase.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.hardus.trueagencyapp.firebase.AuthFirebase
import com.hardus.trueagencyapp.firebase.AuthFirebaseImpl
import com.hardus.trueagencyapp.main_content.home.feature_note.data.data_source.NoteDatabase
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.repository.NoteRepository
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.repository.NoteRepositoryImpl
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.AddNote
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.DeleteNote
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.GetNote
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.GetNotes
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthFirebase(impl: AuthFirebaseImpl): AuthFirebase = impl


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }


    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
        )
    }

}