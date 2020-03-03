package com.example.gitrepos.view.home

interface HomePresenter {
    fun getData()

    fun getAvatar(avatarUrl: String, id: Int)
}