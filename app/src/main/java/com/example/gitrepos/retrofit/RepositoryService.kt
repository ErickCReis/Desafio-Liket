package com.example.gitrepos.retrofit

import com.example.gitrepos.model.Repositories
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RepositoryService {
    @GET
    fun getRepositories(@Url url: String): Call<Repositories>

    @GET
    fun getAvatar(@Url url: String): Call<ResponseBody>
}