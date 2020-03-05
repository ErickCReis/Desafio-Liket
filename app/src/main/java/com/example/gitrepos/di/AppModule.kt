package com.example.gitrepos.di

import androidx.room.Room
import com.example.gitrepos.iterators.IItemIterator
import com.example.gitrepos.iterators.ItemIterator
import com.example.gitrepos.presenter.ItemPresenter
import com.example.gitrepos.repositories.IItemRepository
import com.example.gitrepos.repositories.ItemRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module{

    single { Room.databaseBuilder(get(), AppDataBase::class.java, "Items")
        .fallbackToDestructiveMigration().build() }

    single { get<AppDataBase>().itemsUpdateDao() }

    single<IItemRepository> { ItemRepository(get()) }

    single<IItemIterator> { ItemIterator(get()) }

    viewModel { ItemPresenter(get()) }
}