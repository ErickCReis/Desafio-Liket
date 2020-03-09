package com.example.gitrepos.network

import com.example.gitrepos.model.Repositories
import com.example.gitrepos.model.profile.Profile
import io.reactivex.Observable
import retrofit2.http.*

interface RepositoryService {
    @GET("search/repositories")
    fun getRepositories(@Query("q") query: String,
                        @Query("sort") sort: String ): Observable<Repositories>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Observable<Profile>
}