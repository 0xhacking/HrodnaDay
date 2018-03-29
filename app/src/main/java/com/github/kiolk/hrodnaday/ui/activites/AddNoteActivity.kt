package com.github.kiolk.hrodnaday.ui.activites

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.RequestPostToFCM
import com.github.kiolk.hrodnaday.SendRequestAsyncTask
import com.github.kiolk.hrodnaday.data.models.Museum
import com.github.kiolk.hrodnaday.data.models.WorkTime
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
                    mDatabaseReference.child("2").setValue(oneNewNote)
                }
                R.id.send_notification_button -> {
                    val workTime = WorkTime(thursdayStart = 8.00F, thursdayEnd = 17.00F, thursdayBreak = "13.00-14.00", sundayStart = 9.00F, sundayEnd = 16.00F, sundayBreak = "12.00-12.30", wednesdayStart = 66.00F)
                    mDatabaseReference.child("museums").child("DemoMuseum").setValue(Museum(nameOfMuseum = "DemoMuseum", timeWork = workTime))
                }
            }
        }
        send_button.setOnClickListener(listener)
        send_notification_button.setOnClickListener(listener)
    }
}
