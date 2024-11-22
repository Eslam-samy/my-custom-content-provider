package com.degel.my_custom_content_provider.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val NOTES_DATABASE = "notes_db"

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, NotesDatabase::class.java, NOTES_DATABASE)
                .build()
    }

    abstract fun getNotesDao(): NotesDao

}