package com.example.event

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration =
            RealmConfiguration.Builder().name("db.realm").deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
    }
}