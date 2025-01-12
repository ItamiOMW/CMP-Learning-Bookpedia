package com.itami.bookpedia.di

import com.itami.bookpedia.book_feature.data.network.KtorRemoteBookDataSource
import com.itami.bookpedia.book_feature.data.network.RemoteBookDataSource
import com.itami.bookpedia.book_feature.data.repository.DefaultBookRepository
import com.itami.bookpedia.book_feature.domain.repository.BookRepository
import com.itami.bookpedia.book_feature.presentation.book_list.BookListViewModel
import com.itami.bookpedia.book_feature.presentation.SelectedBookViewModel
import com.itami.bookpedia.book_feature.presentation.book_details.BookDetailsViewModel
import com.itami.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }

    singleOf(::DefaultBookRepository).bind<BookRepository>()
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailsViewModel)
}