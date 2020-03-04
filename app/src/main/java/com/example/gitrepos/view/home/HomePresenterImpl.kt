package com.example.gitrepos.view.home

import android.annotation.SuppressLint
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.model.data.ItemsDatabase
import com.example.gitrepos.retrofit.RepositoryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomePresenterImpl(private val viewHome: HomeView, private val database: ItemsDatabase): HomePresenter{

    private val baseUrl: String = "https://api.github.com/"
    private val compositeDisposable = CompositeDisposable()

    override fun getData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RepositoryService::class.java)

        compositeDisposable.add(retrofitClient.getRepositories("language:swift","stars")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({repositories: Repositories? ->
                val itemsList = repositories!!.items
                viewHome.loadList(itemsList)
                database.clearAllTables()
                database.itemsDao().insert(itemsList)

            },{
                val itemsList = database.itemsDao().getAllItems()
                viewHome.loadList(itemsList)
            },{}))
    }

    override fun showData() {
        val itemsList = database.itemsDao().getAllItems()
        viewHome.loadList(itemsList)
    }
}