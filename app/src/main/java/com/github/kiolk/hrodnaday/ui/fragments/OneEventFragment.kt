package com.github.kiolk.hrodnaday.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.data.recycler.OneEventAdapter
import kotlinx.android.synthetic.main.fragment_one_event.view.*

class OneEventFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
                    view.one_event_recycler_view.adapter = arrayOfEvents?.let { OneEventAdapter(activity.baseContext, it) }
//                    view.one_event_title_text_view.text = day?.description
                }

                override fun onError(exception: Exception) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }))
    }
}