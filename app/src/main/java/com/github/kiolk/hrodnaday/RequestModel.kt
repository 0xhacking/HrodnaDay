package com.github.kiolk.hrodnaday

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
        return ResponseModel(noteArray, null, callback)
    }
}
//
//class AppInfoJsonParser {
//
//    fun getAppInfo(pJson: String): VersionOfApp {
//        val result: VersionOfApp
//        result = Gson().fromJson<VersionOfApp>(pJson, VersionOfApp::class.java!!)
//        val array = Gson().fromJson<Array<VersionOfApp>>(pJson, Array<VersionOfApp>::class.java)
//        return result
//    }
//}
