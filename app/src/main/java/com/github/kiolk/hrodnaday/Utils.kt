package com.github.kiolk.hrodnaday

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

fun checkConnection(pContext: Context): Boolean {
    val check = pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return check != null && check.activeNetworkInfo != null && check.activeNetworkInfo.isConnectedOrConnecting
}

fun convertEpochTime(date : Long, context : Context?) : String {

    val day =  Date(date)
    val locale : Locale? = context?.resources?.configuration?.locale
    val formatter = SimpleDateFormat("dd MMM yyyy", locale)

    return formatter.format(day)
}