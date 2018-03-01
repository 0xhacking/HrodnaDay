package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import kotlinx.android.synthetic.main.card_one_event.view.*

class OneEventAdapter(val context : Context, val arrayEvents : Array<DayNoteModel>) : RecyclerView.Adapter<OneEventAdapter.OneEventViewHolder>(){


    override fun getItemCount(): Int {
        return arrayEvents.size
    }

    override fun onBindViewHolder(holder: OneEventViewHolder?, position: Int) {
        holder?.title?.text = arrayEvents[position].title
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OneEventViewHolder {
        val view : View =LayoutInflater.from(context).inflate(R.layout.card_one_event, parent, false)
        return OneEventViewHolder(view)
    }

    class OneEventViewHolder internal constructor(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var title : TextView = itemView.title_one_event_card_text_view
    }

}
