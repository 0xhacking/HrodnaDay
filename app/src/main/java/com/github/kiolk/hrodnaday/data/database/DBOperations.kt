package com.github.kiolk.hrodnaday.data.database

import android.content.ContentValues
import android.database.Cursor
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.ARTICLE_AUTHOR
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.AUTHOR
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.CREATING
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DAY
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.DESCRIPTION
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MATERIALS
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM_COORDINATES
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.MUSEUM_URL
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.PICTURE_URL
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.SIZE
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TABLE_NAME
import com.github.kiolk.hrodnaday.data.database.models.NoteDBModel.NoteDB.TITLE

class DBOperations {

    private val helper: DBConnector = DBConnector.instance!!

    fun getAll(): List<DayNoteModel> {

        return helper.readableDatabase.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DAY
        ).use(this::allFromCursor).sortedBy { it.day }
    }

    private fun allFromCursor(cursor: Cursor): List<DayNoteModel> {
        val list = ArrayList<DayNoteModel>()
        while (cursor.moveToNext()) {
            list.add(fromCursor(cursor))
        }
        cursor.close()
        return list
    }

    private fun fromCursor(cursor: Cursor): DayNoteModel {
        return DayNoteModel().apply {
            day = cursor.getLong(cursor.getColumnIndex(DAY))
            pictureUrl = cursor.getString(cursor.getColumnIndex(PICTURE_URL))
            title = cursor.getString(cursor.getColumnIndex(TITLE))
            author = cursor.getString(cursor.getColumnIndex(AUTHOR))
            creating = cursor.getString(cursor.getColumnIndex(CREATING))
            description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
            size = cursor.getString(cursor.getColumnIndex(SIZE))
            materials = cursor.getString(cursor.getColumnIndex(MATERIALS))
            museum = cursor.getString(cursor.getColumnIndex(MUSEUM))
            museumUrl = cursor.getString(cursor.getColumnIndex(MUSEUM_URL))
            museumCoordinates = cursor.getString(cursor.getColumnIndex(MUSEUM_COORDINATES))
            articleAuthor = cursor.getString(cursor.getColumnIndex(ARTICLE_AUTHOR))
        }
    }

    fun insertArray(notes: Array<DayNoteModel>) {
        fromNotes(notes).forEach {
            val readableDatabase = helper.readableDatabase
            try {
                readableDatabase.beginTransaction()
                readableDatabase.insert(TABLE_NAME, null, it )
                readableDatabase.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                readableDatabase.endTransaction()
            }
        }
    }

    fun insert(note : DayNoteModel){
        val contentValue = fromDayNote(note)
        val readableDatabase = helper.readableDatabase
        try {
            readableDatabase.beginTransaction()
            readableDatabase.insert(TABLE_NAME, null, contentValue )
            readableDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            readableDatabase.endTransaction()
        }
    }

private fun fromNotes(notes: Array<out DayNoteModel>): List<ContentValues> {
    return notes.map (this::fromDayNote)
}


private fun fromDayNote(note: DayNoteModel): ContentValues {
    return ContentValues().apply {
        put(DAY, note.day)
        put(PICTURE_URL, note.pictureUrl)
        put(TITLE, note.title)
        put(AUTHOR, note.author)
        put(CREATING, note.creating)
        put(DESCRIPTION, note.description)
        put(SIZE, note.size)
        put(MATERIALS, note.materials)
        put(MUSEUM, note.museum)
        put(MUSEUM_URL, note.museumUrl)
        put(MUSEUM_COORDINATES, note.museumCoordinates)
        put(ARTICLE_AUTHOR, note.articleAuthor)
    }
}
}