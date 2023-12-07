package com.hardus.trueagencyapp.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.hardus.trueagencyapp.firebase.AuthFirebase
import com.hardus.trueagencyapp.firebase.AuthFirebaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AuthModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthFirebase(impl: AuthFirebaseImpl): AuthFirebase = impl
}