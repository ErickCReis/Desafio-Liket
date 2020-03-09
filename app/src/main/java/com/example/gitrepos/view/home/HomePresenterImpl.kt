package com.example.gitrepos.view.home

import com.example.gitrepos.model.Repositories
import com.example.gitrepos.model.item.Item
import com.example.gitrepos.model.item.ItemsDatabase
import com.example.gitrepos.network.RepositoryService
import com.example.gitrepos.network.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenterImpl(private val viewHome: HomeView, private val database: ItemsDatabase): HomePresenter{

    private val service = RetrofitBuilder().createService(RepositoryService::class.java)

    override fun getData() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(service.getRepositories("language:swift","stars")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({repositories: Repositories? ->
                val itemsList = repositories!!.items
                viewHome.loadList(itemsList)
                database.clearAllTables()
                database.itemsDao().insert(itemsList)
            },{

            },{}))
    }

    override fun showData() {
        val itemsList = database.itemsDao().getAllItems()
        viewHome.loadList(itemsList)
    }

    override fun showFilteredData(items: MutableList<Item>) {
        viewHome.loadList(items)
    }
}