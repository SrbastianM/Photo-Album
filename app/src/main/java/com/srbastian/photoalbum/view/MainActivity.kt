package com.srbastian.photoalbum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.photoalbum.R
import com.srbastian.photoalbum.adapter.MyImagesAdapter
import com.srbastian.photoalbum.databinding.ActivityMainBinding
import com.srbastian.photoalbum.viewmodel.MyImagesViewModel

class MainActivity : AppCompatActivity() {
    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding
    lateinit var myImagesAdapter: MyImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        //Update the recyclerview if the data have changes.
        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        myImagesAdapter = MyImagesAdapter(this)
        mainBinding.recyclerView.adapter = myImagesAdapter

        myImagesViewModel.getAllImages().observe(this, Observer { images ->
            myImagesAdapter.setImage(images)
        })

        mainBinding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myImagesViewModel.delete(myImagesAdapter.returnItemAndGivenPosition(viewHolder.adapterPosition))
                Toast.makeText(applicationContext, "Photo Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(mainBinding.recyclerView)
    }
}