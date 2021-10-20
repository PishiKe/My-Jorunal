package com.pishi.mydiary.view.activities

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
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

            Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()){
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)
                        } else{
                            showRationaleDialogForPermission()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationaleDialogForPermission()
                }

            }).onSameThread().check()

            dialog.dismiss()
        }

        binding.ivLaunchGallery.setOnClickListener {

            Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun showRationaleDialogForPermission(){

    }

    companion object{

        private const val CAMERA = 1
    }
}