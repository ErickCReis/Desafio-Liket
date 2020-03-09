package com.example.gitrepos.view.profile

import com.example.gitrepos.model.profile.Profile
import com.example.gitrepos.model.profile.ProfilesDatabase
import com.example.gitrepos.network.RepositoryService
import com.example.gitrepos.network.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilePresenterImpl(private val viewProfile: ProfileView,
                           private val database: ProfilesDatabase): ProfilePresenter{

    private val service = RetrofitBuilder().createService(RepositoryService::class.java)
    private val user = "ErickCReis"

    override fun getData() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(service.getUser(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ profile: Profile? ->
                viewProfile.loadProfile(profile!!)
                database.clearAllTables()
                database.profilesDao().insert(profile)
            },{

            },{}))
    }

    override fun showData() {
        val profile = database.profilesDao().getProfile()
        viewProfile.loadProfile(profile)
    }

}