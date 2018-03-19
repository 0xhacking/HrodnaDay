package com.github.kiolk.hrodnaday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        send_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val day : Long = date_editor_text_view.text.toString().toLong()
                val oneNewNote : DayNoteModel = DayNoteModel(day)
                mDatabaseReference.push().setValue(oneNewNote)
            }
        })
    }
}
