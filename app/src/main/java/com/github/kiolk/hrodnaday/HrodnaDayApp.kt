package com.github.kiolk.hrodnaday

import android.app.Application
import android.util.Log
import com.github.kiolk.hrodnaday.data.database.DBConnector
import com.google.firebase.messaging.FirebaseMessaging
import kiolk.com.github.pen.Pen
import kiolk.com.github.pen.utils.PenConstantsUtil

class HrodnaDayApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Log.d("MyLogs", "Start HrodnaDayApp")
        DBConnector.initInstance(this)
        FirebaseMessaging.getInstance().subscribeToTopic("All")
        Pen.getInstance()
                .setLoaderSettings()
                .setSavingStrategy(PenConstantsUtil.SAVE_FULL_IMAGE_STRATEGY)
                .setContext(this)
                .setTypeOfCache(PenConstantsUtil.INNER_FILE_CACHE)
                .setSizeInnerFileCache(10)
                .setUp()
    }
}