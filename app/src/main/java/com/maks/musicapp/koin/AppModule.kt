package com.maks.musicapp.koin

import com.maks.musicapp.repository.*
import com.maks.musicapp.retrofit.RetrofitClient
import com.maks.musicapp.viewmodels.AuthorizationViewModel
import com.maks.musicapp.viewmodels.TrackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitAuth() }
    single { RetrofitClient.provideAuthApi(get()) }
    single { RetrofitClient.provideTrackApi(get()) }
}

val repositoryModule = module {
    single<AuthorizationRepository> { AuthorizationRepositoryImpl(get()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
    single<TrackRepository> { TrackRepositoryImpl(get()) }
}
val viewModelModule = module {
    viewModel { AuthorizationViewModel(get(), get()) }
    viewModel { TrackViewModel(get()) }
}