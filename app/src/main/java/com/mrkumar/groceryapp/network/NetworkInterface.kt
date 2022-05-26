package com.mrkumar.groceryapp.network

import com.mrkumar.groceryapp.model.UserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkInterface {
    @GET("userdata")
    fun getUserData() : Call<List<UserModel>>

    companion object {

        var BASE_URL = "https://crudcrud.com/api/63fcdf377c204df69e1314a7139ea9d7/"

        fun create() : NetworkInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NetworkInterface::class.java)

        }
    }
}