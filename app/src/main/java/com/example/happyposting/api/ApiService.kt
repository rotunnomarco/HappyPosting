package com.example.happyposting.api

import com.example.happyposting.api.adapter.NetworkResponse
import com.example.happyposting.classes.Comment
import com.example.happyposting.classes.Post

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts/")
    suspend fun getPost(): NetworkResponse<List<Post>, Error>

    @GET("posts/{id}")
    suspend fun getEditPost(@Path(value = "id")id:Int): NetworkResponse<List<Post>, Error>

    @GET("comments")
    suspend fun getCommentsWithId(@Query("postId")id :Int): NetworkResponse<List<Comment>, Error>
}