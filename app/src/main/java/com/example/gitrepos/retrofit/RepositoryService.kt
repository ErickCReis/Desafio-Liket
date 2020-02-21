package com.example.gitrepos.retrofit

import com.example.gitrepos.model.Owner
import com.example.gitrepos.model.Repositories
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RepositoryService {
    @GET("repositories?q=language:swift&sort=stars")
    fun getRepositories(): Call<Repositories>

    @GET()
    fun getAvatar(): Call<Owner>
}