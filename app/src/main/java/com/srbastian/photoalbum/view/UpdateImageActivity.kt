package com.srbastian.photoalbum.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.databinding.ActivityAddImageBinding
import com.srbastian.photoalbum.databinding.ActivityUpdateImageBinding
import com.srbastian.photoalbum.model.MyImages
import com.srbastian.photoalbum.util.ControlPermission
import com.srbastian.photoalbum.util.ConvertImage
import com.srbastian.photoalbum.viewmodel.MyImagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateImageActivity : AppCompatActivity() {
    lateinit var updateImageBinding: ActivityUpdateImageBinding
    lateinit var activityResultLauncherForSelectImage: ActivityResultLauncher<Intent>
    lateinit var viewModel: MyImagesViewModel
    lateinit var selectedImage: Bitmap

    var id = -1
    var imageAsString = ""
    var control = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)
        viewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        getAndSetData()
        // register
        registerActivityForSelectImage()

        updateImageBinding.ivUpdateImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // startActivityForResult
            activityResultLauncherForSelectImage.launch(intent)
        }

        updateImageBinding.btnUpdate.setOnClickListener {
            updateImageBinding.btnUpdate.text = "Updating... Please wait"
            updateImageBinding.btnUpdate.isEnabled = false

            GlobalScope.launch(Dispatchers.IO) {

                val updatedTitle = updateImageBinding.etUpdateTitle.text.toString()
                val updateDescription = updateImageBinding.etUpdateDescription.text.toString()
                if (control) {
                    val newImagesAsString = ConvertImage.convertToString(selectedImage)
                    if (newImagesAsString != null) {
                        imageAsString = newImagesAsString
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "There's a problem, please select another image",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                val myUpdateImage = MyImages(updatedTitle, updateDescription, imageAsString)
                myUpdateImage.imageId = id
                viewModel.update(myUpdateImage)
                finish()
            }
        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }

    }

    private fun getAndSetData() {
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val myImage = viewModel.getItemById(id)

                withContext(Dispatchers.Main) {
                    updateImageBinding.etUpdateTitle.setText(myImage.imageTitle)
                    updateImageBinding.etUpdateDescription.setText(myImage.imageDescription)
                    imageAsString = myImage.imageAsString
                    val imageAsBitmap = ConvertImage.convertToBitmap(imageAsString)
                    updateImageBinding.ivUpdateImage.setImageBitmap(imageAsBitmap)
                }
            }

        }
    }

    private fun registerActivityForSelectImage() {
        activityResultLauncherForSelectImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // result of the intent
                val resultCode = result.resultCode
                val imageData = result.data

                if (resultCode == RESULT_OK && imageData != null) {

                    val imageUri = imageData.data

                    imageUri?.let {
                        selectedImage = if (Build.VERSION.SDK_INT >= 28) {

                            val imageSource =
                                ImageDecoder.createSource(this.contentResolver, it)
                            ImageDecoder.decodeBitmap(imageSource)
                        } else {
                            MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        }
                        updateImageBinding.ivUpdateImage.setImageBitmap(selectedImage)
                        control = true
                    }
                }

            }
    }
}