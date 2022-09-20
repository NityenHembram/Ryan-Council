package com.amvlabs.ryancouncil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.R

class ComplainListAdapter(val complainList:List<String>):RecyclerView.Adapter<ComplainListAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val complainText = itemView.findViewById<TextView>(R.id.com_tv)
        val dropdown = itemView.findViewById<ImageView>(R.id.more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.complain_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = complainList[position]
        holder.complainText.text = item
    }

    override fun getItemCount(): Int {
        return complainList.size
    }
}