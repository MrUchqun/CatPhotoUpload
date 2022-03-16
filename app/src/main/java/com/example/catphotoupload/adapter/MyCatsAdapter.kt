package com.example.catphotoupload.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catphotoupload.R
import com.example.catphotoupload.model.Image

class MyCatsAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var imageList = ArrayList<Image>()

    @SuppressLint("NotifyDataSetChanged")
    fun addImages(images: ArrayList<Image>) {
        if (imageList != images) imageList.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_cats, parent, false)
        return MyCatsHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = imageList[position]
        if (holder is MyCatsHolder) {
            Glide.with(context).load(image.url).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhoto)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyCatsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
    }
}