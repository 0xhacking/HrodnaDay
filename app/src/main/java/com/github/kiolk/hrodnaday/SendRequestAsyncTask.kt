package com.github.kiolk.hrodnaday

import android.os.AsyncTask
import android.util.Log
import com.github.kiolk.hrodnaday.data.database.DBOperations

class SendRequestAsyncTask : AsyncTask<RequestModel, Void, ResponseModel>(){
    override fun doInBackground(vararg params: RequestModel?): ResponseModel? {
        val response = params[0]?.perform()
        val dayNoteArray = response?.objects
        if(dayNoteArray != null) DBOperations().insertArray(dayNoteArray)
        val allNotes = DBOperations().getAll()
        Log.d("MyLogs", "All notes $allNotes")
        return ResponseModel(allNotes.toTypedArray(), response?.exception, response!!.callback)
    }

    override fun onPostExecute(result: ResponseModel) {
       if(result.exception == null) result.callback.onSuccess(result) else result.callback.onError(result.exception)
    }
}
