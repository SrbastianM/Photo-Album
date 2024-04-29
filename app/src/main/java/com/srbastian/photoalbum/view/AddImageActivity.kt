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
import com.srbastian.photoalbum.model.MyImages
import com.srbastian.photoalbum.util.ControlPermission
import com.srbastian.photoalbum.util.ConvertImage
import com.srbastian.photoalbum.viewmodel.MyImagesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddImageActivity : AppCompatActivity() {
    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLauncherForSelectImage: ActivityResultLauncher<Intent>
    lateinit var selectedImage: Bitmap
    lateinit var myImagesViewModel: MyImagesViewModel

    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        //
        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        // register
        registerActivityForSelectImage()

        // Solicit the permissions to access the Media Gallery

        addImageBinding.ivAddImage.setOnClickListener   {

            if (ControlPermission.checkPermission(this)) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // startActivityForResult
                activityResultLauncherForSelectImage.launch(intent)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }
            }

        }

        addImageBinding.btnAdd.setOnClickListener {

            if (control) {

                addImageBinding.btnAdd.text = "Uploading... Please wait"
                addImageBinding.btnAdd.isEnabled = false

                GlobalScope.launch(Dispatchers.IO) {
                    val title = addImageBinding.etTitle.text.toString()
                    val description = addImageBinding.etDescription.text.toString()
                    val imageAsString = ConvertImage.convertToString(selectedImage)
                    if (imageAsString != null && title.isNotEmpty() && description.isNotEmpty()) {
                        myImagesViewModel.insert(MyImages(title, description, imageAsString))
                        control = false
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "There's a problem, please select another image",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please fill the empty fields or select a photo",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
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
                        addImageBinding.ivAddImage.setImageBitmap(selectedImage)
                        control = true
                    }
                }

            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // startActivityForResult
            activityResultLauncherForSelectImage.launch(intent)
        }
    }
}