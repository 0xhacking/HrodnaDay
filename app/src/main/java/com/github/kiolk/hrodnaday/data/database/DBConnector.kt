package com.github.kiolk.hrodnaday.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TABLE_NAME

class DBConnector private constructor(context : Context): SQLiteOpenHelper(context, appDbName, null, dbVersion) {

    companion object {
        private val appDbName = "hrodnaday.db"

        private val dbVersion = 1

        var instance: DBConnector? = null
            private set

        fun initInstance(pContext: Context) {
            if (instance == null) {
                instance = DBConnector(pContext)
                Log.d("MyLogs", "Create instance of DBConnector")
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NoteDBModel.SQL_CREATE_ENTRIES)
        Log.d("MyLogs", "Create table $TABLE_NAME")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NoteDBModel.SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}
