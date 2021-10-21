package com.pishi.mydiary.view.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.hardware.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.pishi.mydiary.R
import com.pishi.mydiary.databinding.ActivityDiaryEntryBinding
import com.pishi.mydiary.databinding.ImageSelectionDialogBinding

class DiaryEntry : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityDiaryEntryBinding
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivImageSelection.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.iv_image_selection ->{
                    imageSelectionDialog()
                    return
                }
            }
        }
    }


    private fun imageSelectionDialog(){

        val dialog = Dialog(this)
        val binding : ImageSelectionDialogBinding = ImageSelectionDialogBinding.inflate(layoutInflater)

        dialog.setContentView(binding.root)

        binding.tvCameraSelection.setOnClickListener {

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

        binding.tvGallerySelection.setOnClickListener {

            Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@DiaryEntry, "Gallery Permission Denied", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    showRationaleDialogForPermission()
                }

            }).onSameThread().check()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showRationaleDialogForPermission(){

        AlertDialog.Builder(this).setMessage("Permissions for this feature are turned off. "+
        "It can be enabled on the Application settings")
            .setPositiveButton("GO TO SETTINGS")
                {_,_ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e : ActivityNotFoundException){
                        e.printStackTrace()
                    }
                }
            .setNegativeButton("Cancel")
                {dialog, _ -> dialog.dismiss()}.show()

    }

    companion object{

        private const val CAMERA = 1
        private const val GALLERY = 2
    }


}