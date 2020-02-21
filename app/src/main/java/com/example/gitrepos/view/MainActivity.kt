package com.example.gitrepos.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gitrepos.R
import com.example.gitrepos.model.Item
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.retrofit.RepositoryService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import okhttp3.internal.http2.Http2Stream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
        getAvatar("https://avatars2.githubusercontent.com/u/484656")
    }

    fun getData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getRepositories()

        callback.enqueue(object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                val items: List<Item> = response.body()!!.items

                for (item in items.listIterator()){
                    println(item.name)
                    //getAvatar(item.owner.avatarUrl)
                }
                text.text = "Complete"
        }
            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                text.text = t.message
            }
        })

    }

    fun getAvatar(url: String) {

        val retrofitClient = Retrofit.Builder()
            .baseUrl("http://example.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getAvatar(url)

        callback.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response.body()!!.byteStream())
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                text.text = t.message
            }
        })
    }
}
