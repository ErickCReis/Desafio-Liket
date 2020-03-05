package com.example.gitrepos.retrofit

import com.example.gitrepos.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by RonnyeryDev on 06/03/2018.
 */
class RetrofitBuilder (val url: String)
{

    /**
     * URL REQUEST
     */
    private  var BASE_URL = "https://api.github.com/"

    /**
     * Init Client
     */
    private val client = builderClient()

    /**
     * Retrofit
     */
    private val retrofit = buildRetrofit(client)


    /**
     * Request
     */
    fun builderClient(): OkHttpClient
    {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor{

                    chain -> var request = chain.request()

                    val builder = request.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Connection", "close")

                    request = builder.build()

                    chain.proceed(request)
                }

        if (BuildConfig.DEBUG)
        {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        return builder.build()

    }

    /**
     * Build Retrofit
     */
    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


    /**
     * Get Retrofit
     */
    fun getRetrofit(): Retrofit {
        return retrofit
    }

    /**
     * Sevice
     */
    fun <T> createService(service: Class<T>): T
    {
        return retrofit.create(service)
    }
}