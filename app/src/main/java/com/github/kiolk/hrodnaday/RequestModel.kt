package com.github.kiolk.hrodnaday

import com.github.kiolk.hrodnaday.data.database.DBOperations
import com.github.kiolk.hrodnaday.data.http.HttpClient
import com.google.gson.Gson
import java.util.ArrayList

interface SendRequest {
    fun perform(): ResponseModel
}

class RequestModel(private val url: String, private val callback: ResultCallback<ResponseModel>) : SendRequest {
    override fun perform(): ResponseModel {
        var note: DayNoteModel? = null
        var noteArray: Array<DayNoteModel>? = null
        try {
            val json = HttpClient().get(url)
            noteArray = Gson().fromJson<Array<DayNoteModel>>(json, Array<DayNoteModel>::class.java)
//            note = Gson().fromJson<DayNoteModel>(json, DayNoteModel::class.java)
        } catch (exception: Exception) {

            return ResponseModel(noteArray, exception, callback)
        }
        DBOperations().insertArray(noteArray)

        return ResponseModel(DBOperations().getAll().toTypedArray(), null, callback)
    }
}

class RequestModelFromDB(private val callback: ResultCallback<ResponseModel>):  SendRequest {
    override fun perform(): ResponseModel {
        return ResponseModel(DBOperations().getAll().toTypedArray(), null, callback)
    }
}