package com.rave.bobsburgers.model.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
object RetrofitClass {
    private const val BASE_URL = "https://bobsburgers-api.herokuapp.com"

    private val mediaType = "application/json".toMediaType()

    private val loggingInterceptor:HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client:OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        // .addConverterFactory(Json.asConverterFactory(mediaType))
        .build()

    fun getCharacterFetcher(): CharacterFetcher = retrofit.create()

}