package com.pishi.mydiary.model.database

import androidx.annotation.WorkerThread
import com.pishi.mydiary.model.entities.MyDiary
import kotlinx.coroutines.flow.Flow

class MyDiaryRepository(private val myDiaryDao: MyDiaryDao) {

    @WorkerThread
    suspend fun insertDiaryData(myDiary: MyDiary){

        myDiaryDao.insertDiaryEntry(myDiary)
    }

    val allDiaryList : Flow<List<MyDiary>> = myDiaryDao.getAllDiaryList()

    @WorkerThread
    suspend fun deleteSingleDiaryData(myDiary: MyDiary){

        myDiaryDao.deleteDiaryEntry(myDiary)
    }
}