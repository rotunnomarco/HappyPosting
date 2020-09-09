package com.example.happyposting.classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.lang.Exception


class BitmapStringAdapter {
    @TypeConverter
    fun bitMapToString(bitmap: Bitmap): String? {
        val byteArrayOutStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutStream)
        val byteArray = byteArrayOutStream.toByteArray()
        val encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)
        return encodedString
    }

    @TypeConverter
    fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            bitmap
        } catch (e: Exception) {
            e.message;
            null;
        }
    }
}