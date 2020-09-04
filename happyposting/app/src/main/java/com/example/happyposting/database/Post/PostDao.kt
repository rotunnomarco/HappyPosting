package com.example.happyposting.database.Post

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.happyposting.classes.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM table_Posts ORDER BY id ASC")
    fun getAll(): LiveData<List<Post>>

    @Update
    suspend fun updatePost(post: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListPost(post: List<Post>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: Post)

    @Query("DELETE FROM table_Posts")
    suspend fun deleteAll()

    @Query("DELETE FROM table_Posts WHERE id=:id")
    suspend fun deletePost(id: Int)

}