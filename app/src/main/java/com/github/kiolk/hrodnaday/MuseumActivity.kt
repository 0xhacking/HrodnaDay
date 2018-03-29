package com.github.kiolk.hrodnaday

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.kiolk.hrodnaday.MuseumActivity.museumActivity.DEFAULT_MUSEUM
import com.github.kiolk.hrodnaday.MuseumActivity.museumActivity.MUSEUM
import com.github.kiolk.hrodnaday.MuseumActivity.museumActivity.MUSEUMS
import com.github.kiolk.hrodnaday.data.models.Museum
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_museum.*
import java.util.*

class MuseumActivity : AppCompatActivity() {
    object museumActivity {
        val MUSEUM = "museum"
        val DEFAULT_MUSEUM = "defaultMuseum"
        val MUSEUMS = "museums"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)
        setSupportActionBar(museum_tool_bar)

            val museum = intent.getStringExtra(MUSEUM)
            setupMuseumInformation(museum)


//        collapsing_tool_bar.title = "Collapsing title"
//        location_museum_image_view.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                val uri  = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(Mylocation)", 53.686308F, 23.833877F, 53.686308F, 23.833877F)
//                val geoIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//                startActivity(geoIntent)
//            }
//        })
    }

    private fun setupMuseumInformation(pMuseum: String?) {

        val firebaseReferences = FirebaseDatabase.getInstance().reference.child("days").child("museums").child(pMuseum)
        firebaseReferences.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val museum : Museum? = p0?.getValue<Museum>(Museum::class.java)
                Log.d("MyLogs", "demoGet : $museum")
                museum_tool_bar.title = museum?.nameOfMuseum
                collapsing_tool_bar.title = museum?.nameOfMuseum
                location_museum_image_view.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val uri  = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(${museum?.nameOfMuseum})",
                                museum?.museumLatitude,
                                museum?.museumShirota,
                                museum?.museumLatitude,
                                museum?.museumShirota)
                        val geoIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        startActivity(geoIntent)
                    }
                })
                call_in_museum_image_view.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val phone = museum?.phones?.get(0)?.getPhone()
                        val callIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
                        startActivity(callIntent)
                    }
                })
                full_museum_name_text_view.text = museum?.nameOfMuseum
                work_time_text_view.text = museum?.timeWork?.showWorkTime(resources.getStringArray(R.array.DAYS))
            }
        })
    }
}
