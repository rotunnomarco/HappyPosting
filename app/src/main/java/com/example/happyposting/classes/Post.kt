package com.example.happyposting.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_Posts")
data class Post(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name="id")
    @SerializedName("id") val id: Int,

    @ColumnInfo(name = "userId")
    @SerializedName("userId") val userId: String?,

    @ColumnInfo(name = "title")
    @SerializedName("title") var title: String,

    @ColumnInfo(name = "body")
    @SerializedName("body") var body: String
)
