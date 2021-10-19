package com.pishi.mydiary.view.activities

import android.Manifest
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.pishi.mydiary.databinding.ActivityDiaryEntryBinding
import com.pishi.mydiary.databinding.ImageSelectionDialogBinding

class DiaryEntry : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivImageSelection.setOnClickListener {

        }
    }

    private fun imageSelectionDialog(){

        val dialog = Dialog(this)
        val binding : ImageSelectionDialogBinding = ImageSelectionDialogBinding.inflate(layoutInflater)

        dialog.setContentView(binding.root)

        binding.ivLaunchCamera.setOnClickListener {

            Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }

    }
}