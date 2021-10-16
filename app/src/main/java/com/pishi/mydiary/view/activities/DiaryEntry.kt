package com.pishi.mydiary.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pishi.mydiary.databinding.ActivityDiaryEntryBinding

class DiaryEntry : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}