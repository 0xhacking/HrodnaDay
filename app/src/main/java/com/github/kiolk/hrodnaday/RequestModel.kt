package com.github.kiolk.hrodnaday

import com.github.kiolk.hrodnaday.data.database.DBOperations
import com.github.kiolk.hrodnaday.data.http.HttpClient
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.google.gson.Gson

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

class RequestPostToFCM(private  val  url : String) : SendRequest{
    override fun perform(): ResponseModel {
        val httpClient = HttpClient()
        httpClient.post(url)
        return ResponseModel(null, null, object : ResultCallback<ResponseModel>{
            override fun onSuccess(param: ResponseModel) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(exception: Exception) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}