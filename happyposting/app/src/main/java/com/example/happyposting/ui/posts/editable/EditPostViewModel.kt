package com.example.happyposting.ui.posts.editable

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.happyposting.api.ApiRepository
import com.example.happyposting.api.adapter.NetworkResponse
import com.example.happyposting.classes.Post

import kotlinx.coroutines.launch

class EditPostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ApiRepository = ApiRepository()

    val post: MutableLiveData<List<Post>> by lazy {
        MutableLiveData<List<Post>>()
    }

    private val serverError: MutableLiveData<NetworkResponse.ServerError<Error>> by lazy {
        MutableLiveData<NetworkResponse.ServerError<Error>>()
    }

    private val networkError: MutableLiveData<NetworkResponse.NetworkError> by lazy {
        MutableLiveData<NetworkResponse.NetworkError>()
    }
    private val context: Context by lazy {
        getApplication<Application>().applicationContext
    }

    fun getEditPost(id: Int) {
        viewModelScope.launch {
            when (val response = repository.getEditPost(id)) {
                is NetworkResponse.Success -> {
                    post.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    Log.e(ContentValues.TAG, "⚠️ $response")
                    serverError.value = response
                }
                is NetworkResponse.NetworkError -> {
                    Log.e(ContentValues.TAG, "⚠️ $response")
                    networkError.value = response
                }
            }
        }
    }
}