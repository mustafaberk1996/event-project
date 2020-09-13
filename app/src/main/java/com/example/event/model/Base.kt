package com.example.event.model

data class Base(val next:Any,
                val previous:Any,
                val count: Int = 0,
                val results: MutableList<Object>)