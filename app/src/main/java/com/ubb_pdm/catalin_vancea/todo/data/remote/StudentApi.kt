package com.ubb_pdm.catalin_vancea.todo.data.remote

import com.google.gson.GsonBuilder
import com.ubb_pdm.catalin_vancea.core.Api
import com.ubb_pdm.catalin_vancea.todo.data.Student
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object StudentApi {
    private const val URL = "http://192.168.1.13:3000/"

    interface Service {
        @GET("/student")
        suspend fun find(): List<Student>

        @GET("/student/{id}")
        suspend fun read(@Path("id") studentId: String): Student;

        @Headers("Content-Type: application/json")
        @POST("/student")
        suspend fun create(@Body student: Student): Student

        @Headers("Content-Type: application/json")
        @PUT("/student/{id}")
        suspend fun update(@Path("id") studentId: String, @Body student: Student): Student
    }

    val service: Service = Api.retrofit.create(Service::class.java)
}