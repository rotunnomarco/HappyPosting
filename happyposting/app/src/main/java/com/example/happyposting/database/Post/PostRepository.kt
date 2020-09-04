package com.example.happyposting.database.Post

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.happyposting.classes.Post

class PostRepository(private val postDao: PostDao) {

    val allPosts: LiveData<List<Post>> = postDao.getAll()

    suspend fun addListPost(post: List<Post>) {
        try {
            postDao.addListPost(post)
            Log.e("SELECT", postDao.getAll().toString())
            Log.e("SELECT ALLPOSTS", allPosts.value.toString())
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }

    suspend fun addPost(post: Post) {
        try {
            postDao.addPost(post)
            Log.e("SELECT", postDao.getAll().toString())
            Log.e("SELECT ALLPOSTS", allPosts.value.toString())
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }
    suspend fun deletePost(postId: Int) {
        try {
            postDao.deletePost(postId)
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }
    suspend fun updatePost(post:Post){
        try {
            postDao.updatePost(post)
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }
}