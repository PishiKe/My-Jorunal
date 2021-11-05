package com.pishi.mydiary.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pishi.mydiary.model.entities.MyDiary

@Database (entities = [MyDiary::class], version = 2)

abstract class DiaryDatabase : RoomDatabase() {

    abstract fun myDiaryDao() : MyDiaryDao

    companion object{
        @Volatile
        private var INSTANCE : DiaryDatabase? = null

        fun getDatabase(context : Context) : DiaryDatabase{
            //if the INSTANCE is not null, return the database
            //if the INSTANCE is null, create the database
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "fav_dish_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance

                instance
            }
        }
    }
}