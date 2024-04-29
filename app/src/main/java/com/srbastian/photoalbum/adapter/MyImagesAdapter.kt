package com.srbastian.photoalbum.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.photoalbum.databinding.ImageItemBinding
import com.srbastian.photoalbum.model.MyImages
import com.srbastian.photoalbum.util.ConvertImage
import com.srbastian.photoalbum.view.UpdateImageActivity

class MyImagesAdapter(val activity: Activity) :
    RecyclerView.Adapter<MyImagesAdapter.myImagesViewHolder>() {

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

            itemBinding.cardView.setOnClickListener {
                val intent = Intent(activity, UpdateImageActivity::class.java)
                intent.putExtra("id", myImage.imageId)
                activity.startActivity(intent)
            }

        }

    }

    // return item and given position
    fun returnItemAndGivenPosition(position: Int): MyImages {
        return imageList[position]
    }

}