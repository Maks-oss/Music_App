package com.maks.musicapp.koin

import com.maks.musicapp.repository.SpotifyAuthorizationRepository
import com.maks.musicapp.repository.SpotifyAuthorizationRepositoryImpl
import com.maks.musicapp.retrofit.RetrofitClient
import com.maks.musicapp.viewmodels.MusicViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitSpotifyAuth() }
    single { RetrofitClient.provideSpotifyAuthApi(get()) }
}

val repositoryModule = module {
    single<SpotifyAuthorizationRepository> { SpotifyAuthorizationRepositoryImpl(get()) }
}
val viewModelModule = module {
    viewModel { MusicViewModel(get()) }
}