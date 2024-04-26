package com.srbastian.photoalbum.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class ConvertImage {
    // Convert the byteArray to String | Most simply to save the data in the DB |
    companion object {

        // convert the image to string, checks if the images is high or low quality and resized if is the case
        fun convertToString(bitmap: Bitmap): String? {

            val stream = ByteArrayOutputStream()
            val resultCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            if (resultCompress) {
                val byteArray = stream.toByteArray()

                val imagesString = if (byteArray.size > 2000000) {
                    resizeImage(bitmap, 0.1)
                } else if (byteArray.size in 1000000..2000000) {
                    resizeImage(bitmap, 0.5)
                } else {
                    Base64.encodeToString(byteArray, Base64.DEFAULT)
                }
                return imagesString
            } else {
                return null
            }
        }

        // Resize Image from the quality
        fun resizeImage(bitmap: Bitmap, coefficient: Double): String? {
            val resizedBitmap = Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width + coefficient).toInt(),
                (bitmap.height + coefficient).toInt(),
                true
            )
            val newStream = ByteArrayOutputStream()
            val resultCompress = resizedBitmap.compress(Bitmap.CompressFormat.PNG, 180, newStream)
            if (resultCompress) {
                val newByteArray = newStream.toByteArray()
                return Base64.encodeToString(newByteArray, Base64.DEFAULT)
            } else {
                return null
            }
        }

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