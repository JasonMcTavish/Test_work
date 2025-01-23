package ru.test.testwork.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface TestAPI {

    @GET("objects/{id}")
    suspend fun getInfo(
        @Path("id") id: String
    ): ResponseInfoDto


    companion object {
        private const val URL = "https://collectionapi.metmuseum.org/public/collection/v1/"

        fun getInstance(): TestAPI {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TestAPI::class.java)
        }
    }
}