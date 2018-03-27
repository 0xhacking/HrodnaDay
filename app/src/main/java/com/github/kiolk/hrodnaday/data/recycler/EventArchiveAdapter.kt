package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.reversSortCollection
import kotlinx.android.synthetic.main.card_one_event_archive.view.*

class EventArchiveAdapter(private val pContext: Context, private var pNotes: Array<DayNoteModel>) : RecyclerView.Adapter<EventArchiveAdapter.EventArchiveViewHolder>(), Filterable {

    lateinit var notes : Array<DayNoteModel>
    var sizeOfNotes = 0
    init {
        pNotes = reversSortCollection(pNotes)
        pNotes = pNotes.filter { it.language == pContext.resources.configuration.locale.language }.toTypedArray()
        notes = pNotes
        val sizeOfNotes = pNotes.size
    }
        lateinit var notesFiltered : Array<DayNoteModel>

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

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint?.toString()?.toLowerCase()
                if(searchString == null || searchString.toCharArray().isEmpty()){
                    notesFiltered = notes
                } else {
                    val titleFiltered = notes.filter { it.title.toLowerCase().contains(searchString) }.toTypedArray()
                    val authorFiltered = notes.filter { it.author.toLowerCase().contains(searchString) }.toTypedArray()
                    val collection : MutableList<DayNoteModel> = mutableListOf()
                    collection.addAll(titleFiltered)
                    collection.addAll(authorFiltered)
                    notesFiltered = collection.toSet().toTypedArray()
                }
                val filterResults = FilterResults()
                filterResults.values = notesFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                pNotes = results?.values as Array<DayNoteModel>
                sizeOfNotes = pNotes.size
                notifyDataSetChanged()
            }
        }
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