package com.srbastian.photoalbum.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class ConvertImage {
    // Convert the byteArray to String | Most simply to save the data in the DB |
    companion object {
        fun convertToBitmap(imageAsString: String): Bitmap {
            val byteArrayAsDecodeString = Base64.decode(imageAsString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(
                byteArrayAsDecodeString,
                0,
                byteArrayAsDecodeString.size
            )
        }
    }

}