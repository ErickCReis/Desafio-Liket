package com.example.gitrepos.retrofit

import com.example.gitrepos.model.Owner
import com.example.gitrepos.model.Repositories
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RepositoryService {
    @GET("repositories?q=language:swift&sort=stars")
    fun getRepositories(): Call<Repositories>

    @GET
    fun getAvatar(@Url url: String): Call<ResponseBody>
}