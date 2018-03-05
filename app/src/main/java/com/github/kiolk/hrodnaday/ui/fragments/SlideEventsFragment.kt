package layout

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
//import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.PictureActivity
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.data.recycler.ClickListener
import com.github.kiolk.hrodnaday.data.recycler.setupPicture
import com.github.kiolk.hrodnaday.ui.fragments.PICTURE_URL
import kiolk.com.github.pen.Pen
import org.w3c.dom.Text

class SlideEventsFragment: Fragment() {

    var day : Long? = 0
    lateinit var dayNote : DayNoteModel

    fun formInstance( dayNote : DayNoteModel?) : SlideEventsFragment{
        val day : SlideEventsFragment = SlideEventsFragment()
        val bundle : Bundle = Bundle()
        dayNote?.day?.let { bundle.putLong("day", it) }
        bundle.putSerializable("note", dayNote)
        day.arguments =bundle
        return day
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        day = arguments?.getLong("day")
        dayNote = arguments?.getSerializable("note") as DayNoteModel

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_screen_events, null) ?: super.onCreateView(inflater, container, savedInstanceState)
        view?.findViewById<TextView>(R.id.day_one_event_card_text_view)?.text = day.toString()
        setUpNoteInView( view, dayNote )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view?.background = view?.context?.resources?.getDrawable(R.drawable.colorlees_background)
        }
        view?.isScrollContainer = true
        return view
    }
}

fun setUpNoteInView(view: View?, dayNote: DayNoteModel) {
    view?.findViewById<TextView>(R.id.day_one_event_card_text_view)?.text = dayNote.day.toString()
//    Pen.getInstance().getImageFromUrl(dayNote.pictureUrl).inputTo(view?.findViewById(R.id.picture_one_event_card_image_view))
    view?.findViewById<TextView>(R.id.title_one_event_card_text_view)?.text = dayNote.title
    view?.findViewById<TextView>(R.id.author_one_event_card_text_view)?.text = dayNote.author
    view?.findViewById<TextView>(R.id.creating_one_event_card_text_view)?.text = dayNote.creating
    view?.findViewById<TextView>(R.id.description_one_event_card_text_view)?.text = dayNote.description
    val array : Array<DayNoteModel> = arrayOf(dayNote)
    view?.findViewById<ImageView>(R.id.picture_one_event_card_image_view)?.setOnClickListener(object : View.OnClickListener{
        override fun onClick(v: View?) {
            val intent : Intent = Intent(view.context, PictureActivity::class.java)
            intent.putExtra(PICTURE_URL, dayNote.pictureUrl)
            startActivity(view.context, intent, null)
        }
    })
    view?.context?.let { setupPicture(0, view.findViewById(R.id.picture_one_event_card_image_view), array, it) }
}