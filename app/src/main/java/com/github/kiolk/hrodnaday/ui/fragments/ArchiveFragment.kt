package com.github.kiolk.hrodnaday.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.data.recycler.ClickListener
import com.github.kiolk.hrodnaday.data.recycler.EventArchiveAdapter
import com.github.kiolk.hrodnaday.data.recycler.ItemClickListener
import com.github.kiolk.hrodnaday.data.recycler.RecyclerTouchListener
import kotlinx.android.synthetic.main.card_one_event_archive.view.*
import kotlinx.android.synthetic.main.fragment_archive_events.view.*

class ArchiveFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_archive_events, null)  ?: super.onCreateView(inflater, container, savedInstanceState)
    }
    fun presentData(itemListener : ItemClickListener){
        SendRequestAsyncTask().execute(RequestModelFromDB(
                object : ResultCallback<ResponseModel>{
                    override fun onSuccess(param: ResponseModel) {
                        view.archive_events_recycler_view.layoutManager = LinearLayoutManager(activity.baseContext)
                        view.archive_events_recycler_view.addOnItemTouchListener(RecyclerTouchListener(activity.baseContext, view.archive_events_recycler_view, object : ClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("MyLogs", "onClick Id ${view.title_card_text_view.text} and $position event")
                                val date = view.title_card_text_view.text
                                itemListener.onItemClick(date.toString())
                            }

                            override fun onLongClick(view: View, position: Int) {
                                Log.d("MyLogs", "onLongClick Id $position event")

                            }
                        } ))
                        view.archive_events_recycler_view.adapter = param.objects?.let { EventArchiveAdapter(activity.baseContext, it) }
                    }

                    override fun onError(exception: Exception) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
        ))
    }

    fun getAdapter() : RecyclerView.Adapter<*>? {
        return view.archive_events_recycler_view.adapter
    }
}