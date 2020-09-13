package com.example.event.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.event.ApiClient
import com.example.event.Connectivity
import com.example.event.Constants
import com.example.event.R
import com.example.event.adapter.GuestAdapter
import com.example.event.adapter.PaginationAdapter
import com.example.event.interfaces.PaginationItemClickListener
import com.example.event.model.Base
import com.example.event.model.Event
import com.example.event.model.Guest
import com.example.event.service.EventService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_event_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class EventDetailActivity : AppCompatActivity() {
    lateinit var event: Event
    val TAG = EventDetailActivity::class.simpleName
    lateinit var eventService: EventService
    lateinit var guestList: MutableList<Guest>
    lateinit var guestAdapter: GuestAdapter
    var paginationAdapter:PaginationAdapter?=null
     var  page:Int=1
    lateinit var  realm:Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        realm = Realm.getDefaultInstance()
        event = Event()
        event.name = intent.getStringExtra(Constants.EVENT_NAME).toString()
        event.id  =intent.getLongExtra(Constants.EVENT_ID,-1)

        val actionBar: ActionBar? =  supportActionBar
        actionBar?.title = event.name
        actionBar?.setDisplayHomeAsUpEnabled(true)
        rvGuests.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rvPagination.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        eventService = ApiClient.getClient().create(EventService::class.java)
        getGuestList()

    }

    private fun getGuestList() {
        if (Connectivity.isConnected(applicationContext))
            getDataFromServer()
        else
            getDataFromLocal()
    }

    private fun getDataFromLocal() {
        realm.beginTransaction()
        progressbar_detail.visibility = View.GONE
        val totalCount = realm.where(Guest::class.java).equalTo("event_id",event.id).findAll().size
        val guestList: MutableList<Guest> = realm.where(Guest::class.java).equalTo("event_id",event.id).limit(Constants.PAGE_DATA_COUNT.toLong()).findAll()
        showDataOnList(guestList, totalCount )
        realm.commitTransaction()
    }

   fun showDataOnList(guestList: MutableList<Guest>, totalCount:Int){
       if (totalCount==0)
       {
           Toast.makeText(applicationContext,Constants.NO_DATA,Toast.LENGTH_LONG).show()
           return
       }

       guestAdapter = GuestAdapter(guestList)

       rvGuests.adapter = guestAdapter

       val remaining = totalCount % Constants.PAGE_DATA_COUNT
       var pageSize:Int = totalCount/Constants.PAGE_DATA_COUNT
       if (remaining>0)
           pageSize++

       if (paginationAdapter==null)
       {

           paginationAdapter =  PaginationAdapter(page,pageSize,object : PaginationItemClickListener{
               override fun itemClick(position: Int) {
                   page=position+1
                   getGuestList()
               }
           })
           rvPagination.adapter = paginationAdapter
       }
   }

    private fun getDataFromServer() {
        progressbar_detail.visibility = View.VISIBLE

        eventService.guestList(event.id,page).enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d(TAG, t.toString())
                progressbar_detail.visibility = View.GONE
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                progressbar_detail.visibility = View.GONE
                if (response.isSuccessful) {
                    val type = object : TypeToken<MutableList<Guest>>() {}.type
                    guestList = Gson().fromJson(Gson().toJson(response.body()?.results), type)
                    saveLocalDatabase(guestList)
                    showDataOnList(guestList, response.body()?.count!!)
                }
            }
        })
    }

    private fun saveLocalDatabase(guestList: MutableList<Guest>) {
        Realm.getDefaultInstance().executeTransactionAsync {
            try {
                val realmlist: RealmList<Guest> = RealmList()
                it.where(Guest::class.java).findAll().deleteAllFromRealm()
                for (item in guestList)
                    item.event_id = event.id
                realmlist.addAll(guestList)
                it.insert(realmlist)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }


}