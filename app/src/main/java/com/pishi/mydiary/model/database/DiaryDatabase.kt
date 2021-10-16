package com.pishi.mydiary.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pishi.mydiary.model.entities.MyDiary

@Database (entities = [MyDiary::class], version = 1)

abstract class DiaryDatabase : RoomDatabase() {

}