package com.example.gitrepos.view.home

import android.annotation.SuppressLint
import com.example.gitrepos.model.Data.Items
import com.example.gitrepos.model.Data.ItemsDao
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.retrofit.RepositoryService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomePresenterImpl(val viewHome: HomeView): HomePresenter, ItemsDao{

    override fun insert(items: Items): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllQuotes(): Observable<List<Items>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val baseUrl: String = "https://api.github.com/"
    val compositeDisposable = CompositeDisposable()

    override fun getData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RepositoryService::class.java)

        compositeDisposable?.add(retrofitClient.getRepositories("language:swift","stars")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponseData))
    }

    @SuppressLint("CheckResult")
    private fun handleResponseData(repositories: Repositories) {
        val itemsList = repositories.items
        viewHome.loadList(itemsList)

        val dataTest = Items(null, "test", 1)
        viewHome.saveList(dataTest)

        itemsList.toObservable()
            .subscribeBy(
                onNext = {
                    getAvatar(it.owner.avatarUrl, itemsList.indexOf(it))
                },

                onError = {
                    it.printStackTrace()
                }
            )
    }

    override fun getAvatar(avatarUrl: String, id: Int){
        
    }

}