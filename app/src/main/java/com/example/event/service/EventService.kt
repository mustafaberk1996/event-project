package com.example.event.service

import com.example.event.Constants
import com.example.event.model.Base
import retrofit2.Call
import retrofit2.http.*


interface EventService {

    @Headers("Content-Type: application/json","Authorization:Token 82f59ce8462541e718daf96a118a6598")
    @GET("events?page_size=${Constants.PAGE_DATA_COUNT}")
    fun eventList(@Query("page") page:Int): Call<Base>

    @Headers("Content-Type: application/json","Authorization:Token 82f59ce8462541e718daf96a118a6598")
    @GET("events/{event_id}/guests?page_size=${Constants.PAGE_DATA_COUNT}")
    fun guestList(@Path("event_id")  eventId:Long,@Query("page") page:Int):Call<Base>
}