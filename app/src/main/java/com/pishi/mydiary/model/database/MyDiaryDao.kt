package com.pishi.mydiary.model.database

import androidx.room.*
import com.pishi.mydiary.model.entities.MyDiary
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDiaryDao {
    @Insert
    suspend fun insertDiaryEntry (myDiary: MyDiary)

    @Query ("SELECT * FROM diary_entries ORDER BY id")
    fun getAllDiaryList () : Flow<List<MyDiary>>

    @Delete
    suspend fun deleteDiaryEntry (myDiary: MyDiary)

    @Update
    suspend fun updateDiaryEntry (myDiary: MyDiary)

}