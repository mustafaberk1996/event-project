package com.example.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.event.R
import com.example.event.model.Guest
import kotlinx.android.synthetic.main.rl_guest_list_item.view.*

class GuestAdapter(private val guestList: MutableList<Guest>) :
    RecyclerView.Adapter<GuestAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rl_guest_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return guestList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(guestList[position], position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvGuestNo: TextView = itemView.findViewById(R.id.tvNo)
        var tvGuestFullName: TextView = itemView.findViewById(R.id.tvGuestName)
        var tvCompany: TextView = itemView.findViewById(R.id.tvCompany)

        fun bind(guest: Guest, position: Int) {
            tvGuestNo.text = "${position + 1}"
            tvGuestFullName.text = "${guest.firstName} ${guest.lastName}"
            tvCompany.text = "${guest.company}"
        }

    }
}