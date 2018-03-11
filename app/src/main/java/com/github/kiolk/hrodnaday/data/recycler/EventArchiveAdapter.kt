package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import kotlinx.android.synthetic.main.card_one_event_archive.view.*

class EventArchiveAdapter(private val pContext: Context, private var pNotes: Array<DayNoteModel>) : RecyclerView.Adapter<EventArchiveAdapter.EventArchiveViewHolder>() {

    init {
        pNotes = pNotes.filter { it.language == pContext.resources.configuration.locale.language }.toTypedArray()
    }

    override fun onBindViewHolder(holder: EventArchiveViewHolder?, position: Int) {
        val event = pNotes[position]
        Log.d("MyLogs", "$position")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder?.itemView?.background = pContext.resources.getDrawable(R.drawable.colorlees_background)
        }
        holder?.title?.text = event.title
        holder?.author?.text = event.author
        holder?.note = event
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

    class EventArchiveViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var note: DayNoteModel? = null
        val title: TextView = itemView.title_card_text_view
        val author: TextView = itemView.museum_card_text_view

        fun openEvent() {
            Log.d("MyLogs", "Id $adapterPosition} event")
        }
    }
}