package com.example.event.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Event(
    @SerializedName("id")
    @PrimaryKey
    var id: Long = 0,
    @SerializedName("groupId")
    var groupId: Long = 0,
    @SerializedName("timezome")
    var timezone: String = "",
    @SerializedName("created")
    var created: String = "",
    @SerializedName("ends")
    var ends: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("modified")
    var modified: String = "",
    @SerializedName("starts")
    var starts: String = ""
) : RealmObject() {


}
