package com.pishi.mydiary.model.database

import androidx.annotation.WorkerThread
import com.pishi.mydiary.model.entities.MyDiary

class MyDiaryRepository(private val myDiaryDao: MyDiaryDao) {

    @WorkerThread
    suspend fun insertDiaryData(myDiary: MyDiary){

        myDiaryDao.insertDiaryEntry(myDiary)
    }
}