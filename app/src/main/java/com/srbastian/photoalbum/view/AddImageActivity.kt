package com.srbastian.photoalbum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.databinding.ActivityAddImageBinding

class AddImageActivity : AppCompatActivity() {
    lateinit var addImageBinding: ActivityAddImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        addImageBinding.ivAddImage.setOnClickListener {

        }

        addImageBinding.btnAdd.setOnClickListener {

        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }
}