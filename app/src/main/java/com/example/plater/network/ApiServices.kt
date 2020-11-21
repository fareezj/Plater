package com.example.plater.network

import com.example.plater.model.RecipeModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/search")
    fun getRecipes(
        @Query("q") name: String,
        @Query("app_id") appID: String,
        @Query("app_key") appKey: String): Observable<RecipeModel>

    companion object{

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttp = OkHttpClient.Builder().addInterceptor(logger)

        fun getServices(): ApiServices{
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.edamam.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp.build())
                    .build()
            return  retrofit.create(ApiServices::class.java)
        }
    }

}


//https://api.edamam.com/search?q=chicken&app_id=86a3739d&app_key=a3d55bd155416ab9a59340a39c78a4b7