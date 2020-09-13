package com.example.event.interfaces

import com.example.event.model.Event

interface EventListItemClickListener {
    fun onItemClick(event: Event)
}