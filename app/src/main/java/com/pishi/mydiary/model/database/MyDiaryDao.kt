package com.pishi.mydiary.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.pishi.mydiary.model.entities.MyDiary

@Dao
interface MyDiaryDao {
    @Insert
    suspend fun insertDiaryEntry (myDiary: MyDiary)
}