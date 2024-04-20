package com.srbastian.photoalbum.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srbastian.photoalbum.model.MyImages

@Database(entities = [MyImages::class], version = 1)
abstract class MyImagesDatabase : RoomDatabase() {
    //1 -> Annotation like a db
    //2 -> Must be a abstract class
    //3 -> Abstract methods returns Dao Interface
    abstract fun myImagesDao(): MyImagesDao

    // add Singleton design pattern
    companion object {
        //Made visible the instance of the class to all threads
        @Volatile
        private var instance: MyImagesDatabase? = null

        fun getDatabaseInstance(context: Context): MyImagesDatabase {
            // if more than one thread tries to create an instance for the db it's gonna block it
            synchronized(this) {
                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyImagesDatabase::class.java, "my_album"
                    ).build()

                }
                return instance as MyImagesDatabase
            }
        }
    }


}