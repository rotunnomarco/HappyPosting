package com.example.happyposting.ui.posts

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.happyposting.api.ApiRepository
import com.example.happyposting.api.adapter.NetworkResponse
import com.example.happyposting.classes.Post
import com.example.happyposting.database.Post.PostDao
import com.example.happyposting.database.MyDatabase
import com.example.happyposting.database.Post.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ApiRepository = ApiRepository()
    private val postDao: PostDao = MyDatabase.getDatabase(application).postDao()
    private val dbRepository: PostRepository
    val trackLiveData: LiveData<List<Post>>

    init {
        dbRepository = PostRepository(postDao)
        trackLiveData = dbRepository.allPosts
    }

    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addPost(post)
        }
    }

    fun addListPost(post: List<Post>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addListPost(post)
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deletePost(postId)
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.updatePost(post)
        }
    }

    val post: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>()
    }

    private val serverError: MutableLiveData<NetworkResponse.ServerError<Error>> by lazy {
        MutableLiveData<NetworkResponse.ServerError<Error>>()
    }

    private val networkError: MutableLiveData<NetworkResponse.NetworkError> by lazy {
        MutableLiveData<NetworkResponse.NetworkError>()
    }

    fun getPost() {
        viewModelScope.launch {
            when (val response = repository.getPost()) {
                is NetworkResponse.Success -> {
                    post.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    Log.e(TAG, "⚠️ $response")
                    serverError.value = response
                }
                is NetworkResponse.NetworkError -> {
                    Log.e(TAG, "⚠️ $response")
                    networkError.value = response
                }
            }
        }
    }
}