package com.degel.my_custom_content_provider.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.degel.my_custom_content_provider.data.local.NOTES_DATABASE
import com.degel.my_custom_content_provider.data.local.NOTES_TABLE
import com.degel.my_custom_content_provider.data.local.NoteEntity
import com.degel.my_custom_content_provider.data.local.NotesDao
import com.degel.my_custom_content_provider.data.local.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class NotesContentProvider : ContentProvider() {

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var notesDao: NotesDao

    companion object {
        const val AUTHORITY = "com.degel.my_custom_content_provider.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$NOTES_TABLE")
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, NOTES_TABLE, 1)
        addURI(AUTHORITY, NOTES_TABLE.plus("#"), 2)
    }

    override fun onCreate(): Boolean {
        val context = context ?: return false
        notesDatabase = Room.databaseBuilder(context, NotesDatabase::class.java, NOTES_DATABASE)
            .build()
        notesDao = notesDatabase.getNotesDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        order: String?
    ): Cursor {
        return when (uriMatcher.match(uri)) {
            1 -> {
                val queryBuilder = SupportSQLiteQueryBuilder.builder(NOTES_TABLE)
                    .columns(projection)
                    .selection(selection, selectionArgs)
                    .orderBy(order)
                    .create()
                runBlocking(Dispatchers.IO) { notesDatabase.query(queryBuilder) }
            }

            2 -> {
                val noteId = ContentUris.parseId(uri).toInt()
                val queryBuilder = SupportSQLiteQueryBuilder.builder(NOTES_TABLE)
                    .columns(projection)
                    .selection("${NoteEntity::id.name} = ?", arrayOf(noteId.toString()))
                    .orderBy(order)
                    .create()
                runBlocking(Dispatchers.IO) { notesDatabase.query(queryBuilder) }
            }

            else -> throw IllegalArgumentException("no uri found")
        }
    }

    override fun getType(p0: Uri): String? {
        return when (uriMatcher.match(p0)) {
            1 -> "vnd.android.cursor.dir/$AUTHORITY.$NOTES_TABLE"
            2 -> "vnd.android.cursor.item/$AUTHORITY.$NOTES_TABLE"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val noteEntity = NoteEntity(
            title = values?.getAsString("title").orEmpty(),
            desc = values?.getAsString("desc").orEmpty()
        )
        val id = runBlocking(Dispatchers.IO) { notesDao.insert(noteEntity) }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        val noteId = ContentUris.parseId(uri).toInt()
        val noteEntity = runBlocking(Dispatchers.IO) { notesDao.getAllNoteId(noteId) }
        val id = runBlocking(Dispatchers.IO) { notesDao.delete(noteEntity) }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return id
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        p2: String?,
        p3: Array<out String>?
    ): Int {
        val noteId = ContentUris.parseId(uri).toInt()
        val noteEntity = runBlocking(Dispatchers.IO) { notesDao.getAllNoteId(noteId) }
        val updateNoteEntity = noteEntity.copy(
            title = values?.getAsString("title").orEmpty(),
            desc = values?.getAsString("desc").orEmpty(),
            id = noteEntity.id
        )
        val id = runBlocking(Dispatchers.IO) { notesDao.update(updateNoteEntity) }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return id
    }
}