package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import kotlinx.android.synthetic.main.card_one_event_archive.view.*

class EventArchiveAdapter(private val pContext: Context, private var pNotes : Array<DayNoteModel>) : RecyclerView.Adapter<EventArchiveAdapter.EventArchiveViewHolder>() {
//
//    init {
//        setHasStableIds(true)
//    }

//    override fun onClick(v: View?) {
//        Log.d("MyLogs", "Id ${v?.id} event: ${pNotes[v?.id ?: 0]}")
//    }

    override fun onBindViewHolder(holder: EventArchiveViewHolder?, position: Int) {
        val event = pNotes[position]
        Log.d("MyLogs", "$position")
        holder?.title?.text = event.day.toString()
        holder?.museum?.text = event.museum
        holder?.itemView?.isClickable = true
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return pNotes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): EventArchiveViewHolder {
        val view = LayoutInflater.from(pContext).inflate(R.layout.card_one_event_archive, parent, false)
        return EventArchiveViewHolder(view)
    }

    class EventArchiveViewHolder internal constructor(itemView : View) : View.OnClickListener, RecyclerView.ViewHolder(itemView){
        override fun onClick(v: View?) {
            Log.d("MyLogs", "Id $adapterPosition} event")
        }

        val title: TextView = itemView.title_card_text_view
        val museum: TextView = itemView.museum_card_text_view

    }
}