package com.maks.musicapp.koin

import android.app.Application
import androidx.room.Room
import com.maks.musicapp.mappers.FeedsMapper
import com.maks.musicapp.mappers.MusicMapper
import com.maks.musicapp.repository.*
import com.maks.musicapp.retrofit.RetrofitClient
import com.maks.musicapp.room.AppDatabase
import com.maks.musicapp.room.FeedDao
import com.maks.musicapp.ui.viewmodels.FeedsViewModel
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.maks.musicapp.ui.viewmodels.TrackViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val retrofitModule = module {
    factory { RetrofitClient.provideRetrofitAuth() }
    single { RetrofitClient.provideTrackApi(get()) }
}
val databaseModule = module {
    fun provideDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "MusicDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AppDatabase): FeedDao {
        return dataBase.feedDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    single<DataStoreRepository> { DataStoreRepositoryImpl(androidContext()) }
    single<MusicRepository> { MusicRepositoryImpl(get()) }
    single<FeedsRepository> { FeedsRepositoryImpl(get(),get(),FeedsMapper()) }
}
val viewModelModule = module {
    viewModel { MusicViewModel(get(), MusicMapper()) }
    viewModel { TrackViewModel() }
    viewModel { LoginViewModel() }
    viewModel { FeedsViewModel(get()) }
}