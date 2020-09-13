package com.example.event

import com.example.event.model.Base
import com.example.event.service.EventService
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.junit.Assert.*


class EventServiceTest {

    @Test
    fun getEventList()
    {
        val eventServiceTest:EventService = ApiClient.getClient().create(EventService::class.java)
        eventServiceTest.eventList(1).enqueue(object : Callback<Base>{
            override fun onFailure(call: Call<Base>, t: Throwable) {
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                assertEquals(200,response.code())
            }
        })
    }

    @Test
    fun getGuestList()
    {
        val eventServiceTest:EventService = ApiClient.getClient().create(EventService::class.java)
        eventServiceTest.guestList(71787,1).enqueue(object : Callback<Base>{
            override fun onFailure(call: Call<Base>, t: Throwable) {

            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                assertEquals(200,response.code())
            }
        })
    }


}