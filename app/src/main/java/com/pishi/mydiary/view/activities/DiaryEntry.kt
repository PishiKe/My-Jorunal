package com.pishi.mydiary.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.hardware.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.pishi.mydiary.R
import com.pishi.mydiary.application.MyDiaryApplication
import com.pishi.mydiary.databinding.ActivityDiaryEntryBinding
import com.pishi.mydiary.databinding.ImageSelectionDialogBinding
import com.pishi.mydiary.model.entities.MyDiary
import com.pishi.mydiary.utils.Constants
import com.pishi.mydiary.viewmodel.MyDiaryViewModel
import com.pishi.mydiary.viewmodel.MyDiaryViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class DiaryEntry : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityDiaryEntryBinding
    private var diaryEntryInfo : MyDiary? = null
    private var imagePath : String = ""


    private val diaryViewModel : MyDiaryViewModel by viewModels{
        MyDiaryViewModelFactory((application as MyDiaryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDiaryEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.FILLED_DIARY_DATA)){
            diaryEntryInfo = intent.getParcelableExtra(Constants.FILLED_DIARY_DATA)
        }


        binding.fabSaveEntry.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){

                R.id.fab_save_entry ->{

                    val title = binding.etDiaryTitle.text.toString().trim{ it <=' '}
                    val dearDiary = binding.etDiaryEntry.text.toString().trim { it <=' '}

                    when{
                        TextUtils.isEmpty(title) ->{
                            Toast.makeText(this, resources.getString(R.string.err_title_empty),
                            Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(dearDiary) ->{
                            Toast.makeText(this, resources.getString(R.string.err_diary_entry_empty),
                            Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            var entryID = 0
                            var imageSource = Constants.IMAGE_SOURCE_LOCAL

                            diaryEntryInfo?.let {
                                if (it.id != 0){
                                    entryID = it.id
                                    imageSource = it.imageSrc
                                }
                            }

                            val diaryEntryInfo : MyDiary = MyDiary(
                                entryID,
                                imagePath,
                                imagePath,
                                title,
                                dearDiary)

                            if (entryID == 0){
                                diaryViewModel.insert(diaryEntryInfo)
                                Toast.makeText(this,"Diary entry added",Toast.LENGTH_SHORT).show()
                                Log.i("Insertion", "Diary Added")
                            }
                            else{
                                Log.e("Insertion","Diary Entry Failed")
                            }

                            finish()
                        }
                    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA){
                data?.extras?.let {
                    val thumbnail : Bitmap = data.extras!!.get("data")as Bitmap

                    Glide.with(this)
                        .load(thumbnail)
                        .centerCrop()

                    imagePath = saveToInternalStorage(thumbnail)
                    Log.i("Image Path", imagePath)
                }
            } else if (requestCode == GALLERY){
                data?.let {
                    val selectedPhotoUri =data.data

                    Glide.with(this)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.i("TAG","Image Loading Error")

                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap : Bitmap =resource.toBitmap()
                                    imagePath = saveToInternalStorage(bitmap)
                                    Log.i("Image Path",imagePath)

                                }
                                return true
                            }

                        })

                }
            }
        } else if (resultCode == RESULT_CANCELED){
            Log.e("cancelled", "cancelled image seletion")
        }
    }

    private fun saveToInternalStorage(bitmap: Bitmap): String{

        val wrapper = ContextWrapper(applicationContext)

        var file =wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        return file.absolutePath
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.add_image, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.add_photo_menu ->{
                imageSelectionDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object{

        private const val CAMERA = 1
        private const val GALLERY = 2

        private const val IMAGE_DIRECTORY = "MyDiary Images"
    }


}