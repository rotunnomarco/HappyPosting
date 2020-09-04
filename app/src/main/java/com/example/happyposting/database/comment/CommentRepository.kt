package com.example.happyposting.database.comment

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.happyposting.classes.Comment

class CommentRepository(private val commentDao: CommentDao) {

    fun getAllCommentsWithId(id: Int): LiveData<List<Comment>> {
        return commentDao.getAllPostsComments(id)
    }
    fun getMaxCommentId():Comment{
        return commentDao.getMaxCommentId()
    }
    suspend fun addListComment(comment: List<Comment>) {
        try {
            commentDao.addListComment(comment)
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }

    suspend fun addComment(comment: Comment) {
        try {
            commentDao.addComment(comment)
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }

    suspend fun deleteComment(id: Int) {
        try {
            commentDao.deletePost(id)
        } catch (e: Exception) {
            Log.e("Db exception", e.message ?: "")
        }
    }
}