package com.srbastian.photoalbum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.databinding.ActivityMainBinding
import com.srbastian.photoalbum.viewmodel.MyImagesViewModel

class MainActivity : AppCompatActivity() {
    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding  = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        myImagesViewModel.getAllImages().observe(this, Observer { images ->
            // update UI
        })

        mainBinding.floatingActionButton.setOnClickListener {
            // Open add imageActivity
        }
    }
}