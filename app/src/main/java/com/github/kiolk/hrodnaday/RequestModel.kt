package com.github.kiolk.hrodnaday

import com.github.kiolk.hrodnaday.data.http.HttpClient
import com.google.gson.Gson

interface SendRequest {
    fun perform(): ResponseModel
}

class RequestModel(private val url: String, private val callback: ResultCallback<ResponseModel>) : SendRequest {
    override fun perform(): ResponseModel {
        var note: DayNoteModel? = null
        try {
            val json = HttpClient().get(url)
            note = Gson().fromJson<DayNoteModel>(json, DayNoteModel::class.java)
        } catch (exception: Exception) {
            return ResponseModel(note, exception, callback)
        }
        return ResponseModel(note, null, callback)
    }
}