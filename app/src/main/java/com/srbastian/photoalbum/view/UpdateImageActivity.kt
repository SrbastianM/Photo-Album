package com.srbastian.photoalbum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.databinding.ActivityAddImageBinding
import com.srbastian.photoalbum.databinding.ActivityUpdateImageBinding

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