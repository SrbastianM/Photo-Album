package com.srbastian.photoalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.photoalbum.databinding.ImageItemBinding
import com.srbastian.photoalbum.model.MyImages
import com.srbastian.photoalbum.util.ConvertImage

class MyImagesAdapter : RecyclerView.Adapter<MyImagesAdapter.myImagesViewHolder>() {

    var imageList: List<MyImages> = ArrayList()

    fun setImage(images: List<MyImages>) {
        this.imageList = images
        notifyDataSetChanged()
    }

    class myImagesViewHolder(val itemBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myImagesViewHolder {

        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return myImagesViewHolder(view)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    // set the data into the Array List using the model
    override fun onBindViewHolder(holder: myImagesViewHolder, position: Int) {

        var myImage = imageList[position]
        with(holder) {
            itemBinding.tvTitle.text = myImage.imageTitle
            itemBinding.tvDescription.text = myImage.imageDescription
            val imageAsBitMap = ConvertImage.convertToBitmap(myImage.imageAsString)
            itemBinding.imageView.setImageBitmap(imageAsBitMap)
        }

    }

}