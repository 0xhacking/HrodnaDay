package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import kiolk.com.github.pen.GetBitmapCallback
import kiolk.com.github.pen.Pen
import kotlinx.android.synthetic.main.card_one_event.view.*

interface PictureClickListener{
    fun onPictureClick(pictureUrl : String)
}

class OneEventAdapter(val context : Context, val arrayEvents : Array<DayNoteModel>, val listener : PictureClickListener) : RecyclerView.Adapter<OneEventAdapter.OneEventViewHolder>(){


    override fun getItemCount(): Int {
        return arrayEvents.size
    }

    override fun onBindViewHolder(holder: OneEventViewHolder?, position: Int) {
        holder?.title?.text = arrayEvents[position].title
        holder?.day?.text = arrayEvents[position].day.toString()
        setupPicture(position, holder?.picture, arrayEvents, context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder?.itemView?.background = context.resources.getDrawable(R.drawable.colorlees_background)
        }

        holder?.author?.text = arrayEvents[position].author
        holder?.creating?.text = arrayEvents[position].creating
        holder?.description?.text = arrayEvents[position].description
        holder?.pictureUrl = arrayEvents[position].pictureUrl
        holder?.pictureClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OneEventViewHolder {
        val view : View =LayoutInflater.from(context).inflate(R.layout.card_one_event, parent, false)
        return OneEventViewHolder(view)
    }

    class OneEventViewHolder internal constructor(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var pictureUrl : String? = null
        var pictureClickListener : PictureClickListener? = null
        var title : TextView = itemView.title_one_event_card_text_view
        var day : TextView = itemView.day_one_event_card_text_view
        var picture : ImageView = itemView.picture_one_event_card_image_view
        var author : TextView = itemView.author_one_event_card_text_view
        var creating : TextView = itemView.creating_one_event_card_text_view
        var description : TextView = itemView.description_one_event_card_text_view


        init {
            picture.setOnClickListener(this)

        }


        override fun onClick(v: View?) {

            if (v?.id ==  R.id.picture_one_event_card_image_view) pictureUrl?.let { pictureClickListener?.onPictureClick(it) }
        }
    }
}

 fun setupPicture(position: Int, view : ImageView?, arrayEvents: Array<DayNoteModel>, context: Context) {
    Pen.getInstance().getImageFromUrl(arrayEvents[position].pictureUrl).getBitmapDirect(object : GetBitmapCallback {
        override fun getBitmap(pBitmapFromLoader: Bitmap?): Bitmap {
            val width = context.applicationContext.resources.displayMetrics.widthPixels
//            val widthDp = width.div(context.resources.displayMetrics.density)
            //TODO need refactor this. Transfer in Pen library
            view?.layoutParams?.width = width
            val widthPicture = pBitmapFromLoader?.width
            val heightPicture = pBitmapFromLoader?.height
            val ratioPicture = widthPicture?.let { heightPicture?.div(it.toFloat()) }
            val height = ratioPicture?.times(width)
            //                val fileName : String = pBitmapFromLoader
            view?.layoutParams?.height = height?.toInt()
            //                var height = holder?.picture?.layoutParams?.height
            view?.setImageBitmap(pBitmapFromLoader)
            //                holder?.picture?.layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            //                height = holder?.picture?.layoutParams?.height
            Pen.getInstance().getImageFromUrl(arrayEvents[position].pictureUrl).inputTo(view)
            return pBitmapFromLoader!!
        }
    })
}
