package com.pishi.mydiary.viewmodel

import androidx.lifecycle.*
import com.pishi.mydiary.model.database.MyDiaryRepository
import com.pishi.mydiary.model.entities.MyDiary
import kotlinx.coroutines.launch

class MyDiaryViewModel (private val repository: MyDiaryRepository)  : ViewModel() {

    fun insert(diary:MyDiary) = viewModelScope.launch {
        repository.insertDiaryData(diary)
    }

    val allDiaryList : LiveData<List<MyDiary>> = repository.allDiaryList.asLiveData()

    fun delete(diary: MyDiary) = viewModelScope.launch {
        repository.deleteSingleDiaryData(diary)
    }

    fun update(diary: MyDiary) = viewModelScope.launch {

        repository.updateDiaryEntry(diary)
    }
}

class MyDiaryViewModelFactory (private val repository: MyDiaryRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MyDiaryViewModel::class.java)){

            @Suppress("UNCHECKED_CAST")
            return MyDiaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}