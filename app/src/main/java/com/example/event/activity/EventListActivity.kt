package com.example.event.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.event.ApiClient
import com.example.event.Connectivity
import com.example.event.Constants
import com.example.event.R
import com.example.event.adapter.EventAdapter
import com.example.event.interfaces.EventListItemClickListener
import com.example.event.adapter.PaginationAdapter
import com.example.event.interfaces.PaginationItemClickListener
import com.example.event.model.Base
import com.example.event.model.Event
import com.example.event.service.EventService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_event_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class EventListActivity : AppCompatActivity() {

    val TAG: String = "EventListActivity"
    lateinit var eventService: EventService
    lateinit var base: Base
    lateinit var eventAdapter: EventAdapter
    lateinit var realm: Realm
    inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    var page: Int = 1;
    var paginationAdapter: PaginationAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        realm = Realm.getDefaultInstance()
        eventService = ApiClient.getClient().create(EventService::class.java)
        rvEvents.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        rvPaginationEvent.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        getEventList()
    }

    private fun getEventList() {
        if (Connectivity.isConnected(applicationContext))
            getDataFromServer()
        else
            getDataFromLocal()
    }

    private fun saveLocalDatabase(eventList: MutableList<Event>) {
        realm.executeTransactionAsync {
            try {
                val realmlist: RealmList<Event> = RealmList()
                it.where(Event::class.java).findAll().deleteAllFromRealm()
                realmlist.addAll(eventList)
                it.insert(realmlist)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    fun getDataFromServer() {
        progressbar.visibility = View.VISIBLE
        eventService.eventList(page).enqueue(object : Callback<Base> {

            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d(TAG, t.toString())
                progressbar.visibility = View.GONE
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                progressbar.visibility = View.GONE
                if (response.isSuccessful) {
                    val type = object : TypeToken<MutableList<Event>>() {}.type
                    val eventList: MutableList<Event> = Gson().fromJson<MutableList<Event>>(
                        Gson().toJson(response.body()?.results),
                        type
                    )
                    saveLocalDatabase(eventList)
                    showDataOnList(eventList, response.body()?.count!!)
                }
            }
        })
    }

    fun getDataFromLocal() {
        progressbar.visibility = View.VISIBLE
        realm.beginTransaction()
        progressbar.visibility = View.GONE
        val eventList: MutableList<Event> = realm.where(Event::class.java).limit(Constants.PAGE_DATA_COUNT.toLong()).findAll()
        showDataOnList(eventList, eventList.size)
        realm.commitTransaction()
    }

    fun showDataOnList(eventList: MutableList<Event>, totalDataCount: Int) {
        if (totalDataCount==0)
        {
            Toast.makeText(applicationContext,Constants.NO_DATA,Toast.LENGTH_LONG).show()
            return
        }

        eventAdapter = EventAdapter(eventList!!, object :
            EventListItemClickListener {
            override fun onItemClick(event: Event) {
                val intent = Intent(applicationContext, EventDetailActivity::class.java)
                intent.putExtra(Constants.EVENT_NAME, event.name)
                intent.putExtra(Constants.EVENT_ID, event.id)
                startActivity(intent)
            }
        })

        rvEvents.adapter = eventAdapter

        val remaining = totalDataCount % Constants.PAGE_DATA_COUNT
        var pageSize: Int = totalDataCount / Constants.PAGE_DATA_COUNT
        if (remaining > 0)
            pageSize++

        if (paginationAdapter == null) {

            paginationAdapter =
                PaginationAdapter(page, pageSize, object : PaginationItemClickListener {
                    override fun itemClick(position: Int) {
                        page=position+1
                        getEventList()
                    }
                })
            rvPaginationEvent.adapter = paginationAdapter
        }
    }
}