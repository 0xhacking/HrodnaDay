package com.github.kiolk.hrodnaday.ui.fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.github.kiolk.hrodnaday.data.recycler.OneEventAdapter
import com.github.kiolk.hrodnaday.data.recycler.PictureClickListener
import com.github.kiolk.hrodnaday.ui.activites.PictureActivity
import kotlinx.android.synthetic.main.fragment_one_event.view.*

val PICTURE_URL = "PictureUrl"

class OneEventFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            activity.setTheme(R.style.MyTheme_Dark)
        }else{
            activity.setTheme(R.style.MyTheme_Light)
        }
        return inflater?.inflate(R.layout.fragment_one_event, null) ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showChosenDay(date : Long){
        SendRequestAsyncTask().execute(RequestModelFromDB(
            object : ResultCallback<ResponseModel>{
                override fun onSuccess(param: ResponseModel) {
                    var arrayOfEvents: Array<DayNoteModel>? = param.objects
                    val day : DayNoteModel? = arrayOfEvents?.find { it.day == date}
                    if (day != null) arrayOfEvents = arrayOf(day)
                    view.one_event_recycler_view.layoutManager = LinearLayoutManager(activity.baseContext)
                    view.one_event_recycler_view.adapter = arrayOfEvents?.let { OneEventAdapter(activity.baseContext, it,
                            object : PictureClickListener {
                                override fun onPictureClick(pictureUrl: String) {
                                    Log.d("MyLogs", "Url of picture $pictureUrl")
                                    val intent : Intent = Intent(activity.baseContext, PictureActivity::class.java)
                                    intent.putExtra(PICTURE_URL, pictureUrl)
                                    startActivity(intent)
                                }
                            }) }
                }

                override fun onError(exception: Exception) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }))
    }
}