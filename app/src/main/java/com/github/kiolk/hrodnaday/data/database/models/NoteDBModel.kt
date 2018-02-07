package com.github.kiolk.hrodnaday.data.database.models

import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DAY
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DESCRIPTION
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.PICTURE_URL
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TABLE_NAME
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TITLE
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB._ID

object NoteDBModel {

    object NoteDB{
        val _ID = "_id"
        val DAY = "day"
        val PICTURE_URL = "picture_url"
        val TITLE = "title"
        val DESCRIPTION = "description"
        val MUSEUM = "museum"
        val TABLE_NAME = "day_notes"
    }

    val SQL_CREATE_ENTRIES = """CREATE TABLE $TABLE_NAME (
            $_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            $DAY BIGINT,
            $PICTURE_URL TEXT,
            $DESCRIPTION TEXT,
            $TITLE TEXT,
            $MUSEUM TEXT
    )""".trimMargin()

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    val SQL_QUERY_ALL = "SELECT * FROM $TABLE_NAME ORDER BY $DAY DESC"
}