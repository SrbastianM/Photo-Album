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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.databinding.ActivityAddImageBinding
import com.srbastian.photoalbum.databinding.ActivityUpdateImageBinding
import com.srbastian.photoalbum.util.ControlPermission

class UpdateImageActivity : AppCompatActivity() {
    lateinit var updateImageBinding: ActivityUpdateImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)

        updateImageBinding.ivUpdateImage.setOnClickListener {

        }

        updateImageBinding.btnUpdate.setOnClickListener {

        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }

    }
}