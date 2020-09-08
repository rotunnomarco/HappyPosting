package com.example.happyposting.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "table_Comments")
data class Comment(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    @SerializedName("id") val id: Int?,

    @ColumnInfo(name = "postId")
    @SerializedName("postId") val postId: String,

    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String,

    @ColumnInfo(name = "email")
    @SerializedName("email") val email: String,

    @ColumnInfo(name = "body")
    @SerializedName("body") val body: String

) : PostAdapterItem