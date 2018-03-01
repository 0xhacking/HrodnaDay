package com.github.kiolk.hrodnaday

import android.content.Context
import android.net.ConnectivityManager

fun checkConnection(pContext: Context): Boolean {
    val check = pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return check != null && check.activeNetworkInfo != null && check.activeNetworkInfo.isConnectedOrConnecting
}