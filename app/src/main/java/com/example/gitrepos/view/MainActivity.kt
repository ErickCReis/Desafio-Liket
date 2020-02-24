package com.example.gitrepos.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitrepos.R
import com.example.gitrepos.model.Item
import com.example.gitrepos.model.Repositories
import com.example.gitrepos.retrofit.RepositoryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val baseUrl: String = "https://api.github.com/"
    private var itemsList: List<Item> = emptyList()
    private var adapter: RepositoryListAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()
        initView()
        getData()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.visibility = View.INVISIBLE
    }

    private fun getData() {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RepositoryService::class.java)

        compositeDisposable?.add(retrofitClient.getRepositories("language:swift","stars")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(repositories: Repositories) {
        itemsList = repositories.items
        adapter = RepositoryListAdapter(itemsList)
        recyclerView.adapter = adapter

        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE

        itemsList.toObservable()
            .subscribeBy(
                onNext = {
                    getAvatar(it.owner.avatarUrl, itemsList.indexOf(it))
                },

                onError = {
                    it.printStackTrace()
                }
            )
    }

    private fun getAvatar(avatarUrl: String, id: Int) {

        val retrofitClient = Retrofit.Builder()
            .baseUrl("http://example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val endpoint = retrofitClient.create(RepositoryService::class.java)
        val callback = endpoint.getAvatar(avatarUrl)

        callback.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val imageStream = response.body()!!.byteStream()
                val image: Bitmap = BitmapFactory.decodeStream(imageStream)
                itemsList[id].owner.avatar = image
                adapter!!.notifyItemChanged(id)
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e( "RepositoryService   ","Erro ao buscar avatar: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}

