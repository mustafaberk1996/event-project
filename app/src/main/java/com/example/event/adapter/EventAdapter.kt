package com.example.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.event.R
import com.example.event.interfaces.EventListItemClickListener
import com.example.event.model.Event

class EventAdapter(val eventList: MutableList<Event>, private  val itemClick: EventListItemClickListener) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.rl_event_list_item,parent,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(eventList[position],position)
        holder.itemView.setOnClickListener {
            itemClick.onItemClick(eventList[position])
        }
    }
    class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNo:TextView = itemView.findViewById(R.id.tvNo)
        private val tvEventName:TextView = itemView.findViewById(R.id.tvEventName)
        private val tvEventStartDate:TextView = itemView.findViewById(R.id.tvEventStartDate)
        private val tvEventEndDate:TextView = itemView.findViewById(R.id.tvEventEndDate)

            fun  bindData(event:Event,position: Int)
            {
                tvNo.text = "${position+1}"
                tvEventName.text = event.name
                tvEventName.text = event.name
                tvEventStartDate.text = event.starts.replace('T',' ')
                tvEventEndDate.text =event.ends.replace('T',' ')
            }

    }




}