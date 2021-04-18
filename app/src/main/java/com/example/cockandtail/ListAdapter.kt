package com.example.cockandtail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.Target
import com.example.cockandtail.UIdata.Model.cocktail
import java.lang.Boolean


class ListAdapter(): RecyclerView.Adapter<ListAdapter.Viewholder>() {
    var cocktail: ArrayList<cocktail> = ArrayList()
    private var mutableItemClickListener:MutableLiveData<Int> = MutableLiveData(-1)
    val itemClickListener:LiveData<Int> get() = mutableItemClickListener

     inner class Viewholder(view: View):RecyclerView.ViewHolder(view) {
       var name_text=view.findViewById<TextView>(R.id.name_cocktail)
        var thumbnail=view.findViewById<ImageView>(R.id.thuumbnail)
        init {
            view.setOnClickListener {
                mutableItemClickListener.value=adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view= LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {


        holder.name_text.setText(cocktail[position].name)
        val manager: RequestManager = Glide.with(holder.thumbnail)
        manager.load(cocktail[position].imageurl).into(holder.thumbnail)




    }



    override fun getItemCount(): Int = cocktail.size

    fun resetItemPosition(){
        mutableItemClickListener.value = -1
    }
}