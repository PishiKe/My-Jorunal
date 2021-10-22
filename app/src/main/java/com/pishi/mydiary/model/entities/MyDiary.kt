package com.pishi.mydiary.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "diary_entries")

data class MyDiary(
        @PrimaryKey(autoGenerate = true) var id : Int = 0,
        @ColumnInfo val image : String?,
        @ColumnInfo (name = "image_src") val imageSrc : String,
        @ColumnInfo val title : String,
        @ColumnInfo val diaryEntry : String
)
