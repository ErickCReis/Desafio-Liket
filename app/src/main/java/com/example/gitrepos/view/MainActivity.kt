package com.example.gitrepos.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitrepos.R
import com.example.gitrepos.model.Item
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.retrofit.RepositoryService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

//    val data: List<Item> = emptyList()
    var items: List<Item> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        Handler().postDelayed({
            val list: RecyclerView = findViewById(R.id.recyclerView)
            val adapter = RepositoryListAdapter(items)
            list.layoutManager = LinearLayoutManager(this)
            list.adapter = adapter
        }, 5000)
    }

    private fun getData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl("http://example.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getRepositories("https://api.github.com/search/repositories?q=language:swift&sort=stars")

        callback.enqueue(object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {

                items = response.body()!!.items
                val itemsIterator = items.listIterator()

                for (item in itemsIterator){
                    getAvatar(item.owner.avatarUrl, itemsIterator.nextIndex() - 1)
                }
        }
            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                println("Erro getAvatar: ${t.message}")
            }
        })
    }

    private fun getAvatar(url: String, id: Int) {

        val retrofitClient = Retrofit.Builder()
            .baseUrl("http://example.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getAvatar(url)

        callback.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val imageStream = response.body()!!.byteStream()
                val image: Bitmap = BitmapFactory.decodeStream(imageStream)
                items.get(id).owner.avatar = image
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Erro getAvatar: ${t.message}")
            }
        })
    }
}

