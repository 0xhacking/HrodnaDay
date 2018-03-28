package com.github.kiolk.hrodnaday

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_museum.*
import java.util.*

class MuseumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)
        setSupportActionBar(museum_tool_bar)
        collapsing_tool_bar.title = "Collapsin title"
        location_museum_image_view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val uri  = String.format(Locale.ENGLISH, "geo: %f, %f", 25.33F, 44.33F)
                val geoIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(geoIntent)
            }
        })
    }
}
