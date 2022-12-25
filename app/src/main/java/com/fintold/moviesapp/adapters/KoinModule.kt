package com.fintold.moviesapp.adapters

import com.fintold.moviesapp.dataSource.LocalSource
import com.fintold.moviesapp.dataSource.RemoteSource
import com.fintold.moviesapp.dataSource.localSource.LocalDataSource
import com.fintold.moviesapp.dataSource.localSource.MoviesDatabase
import com.fintold.moviesapp.dataSource.remoteSource.RemoteDataSource
import com.fintold.moviesapp.intermediateLayer.MoviesRepository
import com.fintold.moviesapp.intermediateLayer.RepositoryInterface
import com.fintold.moviesapp.uI.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MoviesDatabase.getInstance(get()).databaseDao }
    single<LocalSource> { LocalDataSource(get()) }
    single<RemoteSource> { RemoteDataSource() }
    single<RepositoryInterface> { MoviesRepository(get(),get()) }
    viewModel { MoviesViewModel(get()) }
}