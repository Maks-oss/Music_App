package com.maks.musicapp.koin

import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.*
import com.maks.musicapp.retrofit.RetrofitClient
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitAuth() }
    single { RetrofitClient.provideTrackApi(get()) }
}

val repositoryModule = module {
    single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
    single<MusicRepository> { MusicRepositoryImpl(get()) }
}
val viewModelModule = module {
    viewModel { MusicViewModel(get(), MusicMapper()) }
}