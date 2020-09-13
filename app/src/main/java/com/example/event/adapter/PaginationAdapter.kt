package com.example.event.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.event.R
import com.example.event.interfaces.PaginationItemClickListener
import kotlinx.android.synthetic.main.activity_event_detail.view.*
import java.text.FieldPosition

class PaginationAdapter(  var page:Int,private var totalPage:Int,private  var itemClickListener: PaginationItemClickListener):
    RecyclerView.Adapter<PaginationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       var item=  LayoutInflater.from(parent.context).inflate(R.layout.rl_pagination_list_item,parent,false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return  totalPage
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bind(position)
        Log.d("adapter","$position")
        if (position+1==page)
        {
            holder.tvPagNo.setBackgroundResource(R.drawable.dr_tv_page_item_active)
            holder.tvPagNo.setTextColor(Color.WHITE)
        }
        else
        {
            holder.tvPagNo.setBackgroundResource(R.drawable.dr_tv_page_item)
            holder.tvPagNo.setTextColor(Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(position)
            page = position+1
            notifyDataSetChanged()
        }
    }

    class  ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var tvPagNo: TextView =  itemView.findViewById(R.id.tvPageNo)

        fun bind(position: Int)
        {
            tvPagNo.text = "${position+1}"
        }
    }
}