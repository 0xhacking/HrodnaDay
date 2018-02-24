package com.github.kiolk.hrodnaday.ui

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.data.database.DBConnector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBConnector.initInstance(this)

        val listener = View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                about_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                archive_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                day_event_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))

                when (it) {
                    it -> it.background = resources.getDrawable(R.drawable.button_under_background)
                }
            }
        }

        about_button_text_view.setOnClickListener(listener)
        archive_button_text_view.setOnClickListener(listener)
        day_event_button_text_view.setOnClickListener(listener)


//        SendRequestAsyncTask().execute(RequestModel("https://raw.githubusercontent.com/Kiolk/HelloWorld/master/JsonModel",
        SendRequestAsyncTask().execute(RequestModel("http://www.json-generator.com/api/json/get/bUEgRdnXJu?indent=2",
                object : ResultCallback<ResponseModel> {
                    override fun onSuccess(param: ResponseModel) {
                        val arrayEvents = param.objects
                        arrayEvents?.sortBy { it.day }
                        val note = arrayEvents?.maxBy { it.day }
                        title_text_view.text = note?.title
                    }

                    override fun onError(exception: Exception) {
                        title_text_view.text = exception.message
                    }
                }
        ))
    }
}
