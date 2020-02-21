package com.example.gitrepos.retrofit

import com.example.gitrepos.model.Repositories
import retrofit2.Call
import retrofit2.http.GET

interface RepositoryService {
    @GET("repositories")
    fun getRepositories(): Call<Repositories>
}