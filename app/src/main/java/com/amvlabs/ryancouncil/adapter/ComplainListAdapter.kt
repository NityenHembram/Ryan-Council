package com.amvlabs.ryancouncil.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.R
import com.amvlabs.ryancouncil.model.ComplainModel

class ComplainListAdapter(val complainList:List<ComplainModel>,var onComplainClickListener: OnComplainClickListener):RecyclerView.Adapter<ComplainListAdapter.ViewHolder>() {

    var listener:OnComplainClickListener? = null

    init {
        this.listener = onComplainClickListener
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val complainText = itemView.findViewById<TextView>(R.id.com_tv)
        val dropdown = itemView.findViewById<ImageView>(R.id.more)
        val comItem = itemView.findViewById<CardView>(R.id.item_cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.complain_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = complainList[position]
        holder.complainText.text = item.sub
        when(item.action){
            "0" -> {holder.comItem.setCardBackgroundColor(Color.parseColor("#5702F4"))}
            "1" -> {holder.comItem.setCardBackgroundColor(Color.parseColor("#7BF402"))}
            "2" -> {holder.comItem.setCardBackgroundColor(Color.parseColor("#F40202"))}
        }
        holder.comItem.setOnClickListener{
            listener?.onClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return complainList.size
    }
}

interface OnComplainClickListener{
    fun onClicked(item:ComplainModel)
}