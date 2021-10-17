package com.pishi.mydiary.application

import android.app.Application
import com.pishi.mydiary.model.database.DiaryDatabase
import com.pishi.mydiary.model.database.MyDiaryRepository

class MyDiaryApplication : Application() {

    //so as not to start the database when the app starts, it will start only when it's needed
    private val database by lazy {DiaryDatabase.getDatabase(this@MyDiaryApplication)}

    val repository by lazy { MyDiaryRepository(database.myDiaryDao()) }
}