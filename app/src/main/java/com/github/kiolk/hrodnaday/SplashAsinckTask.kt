package com.github.kiolk.hrodnaday

import android.os.AsyncTask

interface SplashListener{
    fun endShowSplash()
}

class SplashAsinckTask : AsyncTask<SplashListener, Void, SplashListener>(){
    override fun doInBackground(vararg params: SplashListener): SplashListener {
        Thread.sleep(1000L)
        return params[0]
    }

    override fun onPostExecute(result: SplashListener?) {
        super.onPostExecute(result)
        result?.endShowSplash()
    }
}