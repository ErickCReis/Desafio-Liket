package com.example.gitrepos.view.home

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.model.data.ItemsDatabase
import com.example.gitrepos.presenter.ItemPresenter
import com.example.gitrepos.retrofit.RepositoryService
import com.example.gitrepos.retrofit.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomePresenterImpl(private val viewHome: HomeView, private val database: ItemsDatabase): HomePresenter{

    val service = RetrofitBuilder("https://api.github.com/").createService(RepositoryService::class.java)
    lateinit var itemPresenter: ItemPresenter

    override fun getData() {
//        val retrofitClient = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(RepositoryService::class.java)
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(service.getRepositories("language:swift","stars")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({repositories: Repositories? ->
                val itemsList = repositories!!.items
                viewHome.loadList(itemsList)
               // database.clearAllTables()
               // database.itemsDao().insert(itemsList)
                itemPresenter.insert(itemsList)

            },{
                val itemsList = database.itemsDao().getAllItems()
                viewHome.loadList(itemsList)
            },{}))
    }

    override fun showData() {
        val itemsList = database.itemsDao().getAllItems()
        viewHome.loadList(itemsList)
    }

    override fun setView(itemPresenter: ItemPresenter, owner: LifecycleOwner) {
        this.itemPresenter = itemPresenter
    }
}