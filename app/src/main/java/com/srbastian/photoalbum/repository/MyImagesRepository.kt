package com.srbastian.photoalbum.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.srbastian.photoalbum.model.MyImages
import com.srbastian.photoalbum.room.MyImagesDao
import com.srbastian.photoalbum.room.MyImagesDatabase

class MyImagesRepository(application: Application) {

    var myImagesDao: MyImagesDao
    var imagesList: LiveData<List<MyImages>>

    init {
        val database = MyImagesDatabase.getDatabaseInstance(application)
        myImagesDao = database.myImagesDao()
        imagesList = myImagesDao.getAllImages()
    }

    suspend fun insert(myImages: MyImages) {
        myImagesDao.insert(myImages)
    }

    suspend fun update(myImages: MyImages) {
        myImagesDao.update(myImages)
    }

    suspend fun delete(myImages: MyImages) {
        myImagesDao.delete(myImages)
    }

    fun getAllImages(): LiveData<List<MyImages>> {
        return imagesList
    }
}