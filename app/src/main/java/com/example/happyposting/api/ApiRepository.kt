package com.example.happyposting.api


class ApiRepository {

    private val client: ApiService = RetrofitClient().client

    suspend fun getPost() = client.getPost()

    suspend fun getEditPost(id: Int) = client.getEditPost(id)

    suspend fun getCommentsWithId(id: Int) = client.getCommentsWithId(id)

}