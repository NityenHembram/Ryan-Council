package com.amvlabs.ryancouncil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.amvlabs.ryancouncil.R

class RecyclerViewAdapter(val userList:List<String>,val listener:OnItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var onclickListener:OnItemClickListener? = null

    init {
        this.onclickListener = listener
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val user_tv = itemView.findViewById<TextView>(R.id.user_tv_name)
        val item_cell = itemView.findViewById<CardView>(R.id.item_cell)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.user_tv.text = userList[position].toString()
        holder.item_cell.setOnClickListener {
            onclickListener?.onItemClick(userList[position])
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}
interface OnItemClickListener{
    fun onItemClick(item:String)
}