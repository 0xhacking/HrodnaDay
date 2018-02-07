package com.github.kiolk.hrodnaday

import android.os.AsyncTask
import android.util.Log
import com.github.kiolk.hrodnaday.data.database.DBOperations

class SendRequestAsyncTask : AsyncTask<RequestModel, Void, ResponseModel>(){
    override fun doInBackground(vararg params: RequestModel?): ResponseModel? {
        val response = params[0]?.perform()
        val dayNote : DayNoteModel = response?.objects ?: DayNoteModel()
        DBOperations().insert(dayNote)
        val allNotes = DBOperations().getAll()
        Log.d("MyLogs", "All notes $allNotes")
        return response
    }

    override fun onPostExecute(result: ResponseModel) {
       if(result.exception == null) result.callback.onSuccess(result) else result.callback.onError(result.exception)
    }
}
