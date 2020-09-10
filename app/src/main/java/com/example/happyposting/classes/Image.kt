package com.example.happyposting.classes

import android.graphics.Bitmap

data class Image(

    var name: String?,
    var mail: String?,
    var bitmapToLoad: Bitmap?

) : PostAdapterItem