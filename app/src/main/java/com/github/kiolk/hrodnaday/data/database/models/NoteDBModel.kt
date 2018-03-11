package com.github.kiolk.hrodnaday.data.database.models

import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.ARTICLE_AUTHOR
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.AUTHOR
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.CREATING
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DAY
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DESCRIPTION
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.LANGUAGE
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MATERIALS
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM_COORDINATES
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM_URL
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.PICTURE_URL
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.SIZE
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TABLE_NAME
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TITLE

object NoteDBModel {

    object NoteDB{
//        val _ID = "_id"
        val DAY = "day"
        val PICTURE_URL = "picture_url"
        val TITLE = "title"
        val AUTHOR = "author"
        val CREATING = "creating"
        val SIZE = "size"
        val MATERIALS = "materials"
        val DESCRIPTION = "description"
        val MUSEUM = "museum"
        val MUSEUM_URL = "museum_url"
        val MUSEUM_COORDINATES = "museum_coordinates"
        val TABLE_NAME = "day_notes"
        val ARTICLE_AUTHOR = "article_author"
        val LANGUAGE = "language"
    }

    val SQL_CREATE_ENTRIES = """CREATE TABLE $TABLE_NAME (
            $DAY BIGINT PRIMARY KEY NOT NULL,
            $PICTURE_URL TEXT,
            $DESCRIPTION TEXT,
            $TITLE TEXT,
            $AUTHOR TEXT,
            $CREATING TEXT,
            $SIZE TEXT,
            $LANGUAGE TEXT,
            $MATERIALS TEXT,
            $MUSEUM_URL TEXT,
            $MUSEUM_COORDINATES TEXT,
            $MUSEUM TEXT,
            $ARTICLE_AUTHOR TEXT

    )""".trimMargin()
//      $_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    val SQL_QUERY_ALL = "SELECT * FROM $TABLE_NAME ORDER BY $DAY DESC"
}