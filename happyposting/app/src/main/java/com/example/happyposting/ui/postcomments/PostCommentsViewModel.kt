package com.example.happyposting.ui.postcomments

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.happyposting.api.ApiRepository
import com.example.happyposting.api.adapter.NetworkResponse
import com.example.happyposting.classes.Comment
import com.example.happyposting.database.PostDatabase
import com.example.happyposting.database.comment.CommentDao
import com.example.happyposting.database.comment.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostCommentsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ApiRepository = ApiRepository()

    private val commentDao: CommentDao = PostDatabase.getDatabase(application).commentDao()
    private val dbRepository: CommentRepository
    var trackLiveComments: LiveData<List<Comment>>

    init {
        dbRepository = CommentRepository(commentDao)
        trackLiveComments = MutableLiveData()
    }

    fun getAllCommentsWithId(id: Int) {
        trackLiveComments = dbRepository.getAllCommentsWithId(id)
    }

    fun getMaxCommentId(): Comment {
        return dbRepository.getMaxCommentId()
    }

    fun addListComment(comment: List<Comment>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addListComment(comment)
        }
    }

    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addComment(comment)
        }
    }

    val comment: MutableLiveData<List<Comment>> by lazy {
        MutableLiveData<List<Comment>>()
    }
    private val serverError: MutableLiveData<NetworkResponse.ServerError<Error>> by lazy {
        MutableLiveData<NetworkResponse.ServerError<Error>>()
    }

    private val networkError: MutableLiveData<NetworkResponse.NetworkError> by lazy {
        MutableLiveData<NetworkResponse.NetworkError>()
    }

    fun getNetworkCommentsWithId(id: Int) {
        viewModelScope.launch {
            when (val response = repository.getCommentsWithId(id)) {
                is NetworkResponse.Success -> {
                    comment.value = response.body
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