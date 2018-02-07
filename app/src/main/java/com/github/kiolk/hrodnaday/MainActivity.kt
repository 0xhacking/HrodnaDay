package com.github.kiolk.hrodnaday

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kiolk.hrodnaday.data.database.DBConnector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBConnector.initInstance(this)

        SendRequestAsyncTask().execute(RequestModel("https://raw.githubusercontent.com/Kiolk/HelloWorld/master/JsonModel",
               object : ResultCallback<ResponseModel>{
                   override fun onSuccess(param: ResponseModel) {
                       title_text_view.text = param.objects?.title
                   }

                   override fun onError(exception: Exception) {
                       title_text_view.text = exception.message
                   }
               }
        ))
    }
}
