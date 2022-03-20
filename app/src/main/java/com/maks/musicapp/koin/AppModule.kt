package com.maks.musicapp.koin

import com.maks.musicapp.repository.AuthorizationRepository
import com.maks.musicapp.repository.AuthorizationRepositoryImpl
import com.maks.musicapp.repository.DataStoreRepository
import com.maks.musicapp.repository.DataStoreRepositoryImpl
import com.maks.musicapp.retrofit.RetrofitClient
import com.maks.musicapp.viewmodels.MusicViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitSpotifyAuth() }
    single { RetrofitClient.provideSpotifyAuthApi(get()) }
}

val repositoryModule = module {
    single<AuthorizationRepository> { AuthorizationRepositoryImpl(get()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
}
val viewModelModule = module {
    viewModel { MusicViewModel(get(), get()) }
}