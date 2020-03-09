package com.example.gitrepos.view.profile

import com.example.gitrepos.model.profile.Profile

interface ProfileView {
    fun loadProfile(profile: Profile)
}