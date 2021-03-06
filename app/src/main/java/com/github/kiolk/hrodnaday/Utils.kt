package com.github.kiolk.hrodnaday

import android.content.Context
import android.net.ConnectivityManager
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import java.text.SimpleDateFormat
import java.util.*

val MY_LOGS = "MyLogs"

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

fun getCurrentDay() : Long{
    val currentTimeMillis = System.currentTimeMillis()
    return currentTimeMillis - currentTimeMillis.rem(86400000) + 86400000
}

fun reversSortCollection( array: Array<DayNoteModel>) : Array<DayNoteModel>{
    val tmp = array
    var newArray : MutableList<DayNoteModel> = tmp.toMutableList()
    newArray = newArray.asReversed()
    return newArray.toList().toTypedArray()
}

