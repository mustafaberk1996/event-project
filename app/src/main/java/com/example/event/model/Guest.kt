package com.example.event.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class  Guest(
    @SerializedName("id")
    @PrimaryKey
    var id:Long=0,
    var event_id:Long=0,
    @SerializedName("first_name")
    var firstName:String="",
    @SerializedName("last_name")
    var lastName:String="",
    @SerializedName("email")
    var email:String="",
    @SerializedName("prefix")
        var prefix:String="",
    @SerializedName("company")
var company:String=""
    ):RealmObject(){}