package com.example.gitrepos.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.R
import com.example.gitrepos.model.Posts
import com.example.gitrepos.retrofit.Endpoint
import com.example.gitrepos.retrofit.NetworkUtils
import com.example.gitrepos.retrofit.RepositoryService
import com.example.gitrepos.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }

    fun getData() {
        val retrofitClient = RetrofitInitializer
            .getRetrofitInstance("https://api.github.com/search/repositories?q=language:swift&sort=stars")

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getRepositories()

        callback.enqueue(object : Callback<Repositories> {

            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                println(response.body())
        }
            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }


        })

    }
}

