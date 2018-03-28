package com.github.kiolk.hrodnaday.ui.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.RequestPostToFCM
import com.github.kiolk.hrodnaday.SendRequestAsyncTask
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    lateinit var mFirebaseDatabase : FirebaseDatabase
    lateinit var mDatabaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference.child("days")
        val listener : View.OnClickListener = View.OnClickListener { v ->
            when (v?.id) {
                R.id.send_button -> {
                    val day : Long = date_editor_text_view.text.toString().toLong()
                    val oneNewNote: DayNoteModel = DayNoteModel(day)
                    mDatabaseReference.child("1").child("day").setValue(1111122222333)
                }
                R.id.send_notification_button -> {
                    SendRequestAsyncTask().execute(RequestPostToFCM("https://gcm-http.googleapis.com/gcm/send"))
                }
            }
        }
        send_button.setOnClickListener(listener)
        send_notification_button.setOnClickListener(listener)
    }
}
