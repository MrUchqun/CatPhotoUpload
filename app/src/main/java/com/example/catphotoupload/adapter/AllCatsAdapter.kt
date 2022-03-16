package com.example.catphotoupload.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catphotoupload.R
import com.example.catphotoupload.model.Breed
import com.example.catphotoupload.model.Image

class AllCatsAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var breedList = ArrayList<Breed>()

    @SuppressLint("NotifyDataSetChanged")
    fun addBreeds(breeds: ArrayList<Breed>) {
        breedList.addAll(breeds)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_cats, parent, false)
        return AllCatsHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val breed = breedList[position]
        if (holder is AllCatsHolder) {
            Glide.with(context).load(breed.image?.url).placeholder(ColorDrawable(Color.GRAY))
                .into(holder.ivPhoto)

            holder.tvName.text = breed.name
            holder.tvOrigin.text = breed.origin
        }
    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    class AllCatsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvOrigin: TextView = view.findViewById(R.id.tv_origin)
    }
}