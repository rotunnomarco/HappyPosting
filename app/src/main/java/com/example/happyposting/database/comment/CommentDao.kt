package com.example.happyposting.database.comment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.happyposting.classes.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM table_Comments ORDER BY id DESC LIMIT 1")
    fun getMaxCommentId(): Comment

    @Query("SELECT * FROM table_Comments ORDER BY id ASC")
    fun getAllComments(): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListComment(comment: List<Comment>)

    @Query("DELETE FROM table_Comments WHERE id=:id")
    suspend fun deletePost(id: Int)

    @Query("SELECT table_Comments.* FROM table_Comments INNER JOIN table_Posts ON table_Posts.id = table_Comments.postId WHERE table_Comments.postId=:id ORDER BY table_Comments.id ASC")
    fun getAllPostsComments(id: Int): LiveData<List<Comment>>
}