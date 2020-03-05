package com.example.gitrepos.retrofit

import com.example.gitrepos.model.Repositories
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RepositoryService {
    @GET("search/repositories")
    fun getRepositories(@Query("q") query: String,
                        @Query("sort") sort: String ): Observable<Repositories>


}