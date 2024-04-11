package com.hardus.trueagencyapp.firebase.di

import com.hardus.trueagencyapp.main_content.home.feature_qrcode.data.repo.QrcodeRepoImpl
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindQrcodeRepo(
        qrcodeRepoImpl: QrcodeRepoImpl
    ): QrcodeRepo
}