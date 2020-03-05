package com.example.gitrepos.view.home

import androidx.lifecycle.LifecycleOwner
import com.example.gitrepos.presenter.ItemPresenter
import java.security.acl.Owner

interface HomePresenter {
    fun getData()
    fun showData()
    fun setView(itemPresenter: ItemPresenter, owner: LifecycleOwner)
}