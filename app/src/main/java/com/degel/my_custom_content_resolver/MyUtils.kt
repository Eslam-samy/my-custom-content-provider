package com.degel.my_custom_content_resolver

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


const val AUTHORITY = "com.degel.my_custom_content_provider.provider"
    const val NOTES_TABLE = "notes"
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$NOTES_TABLE")

fun getAllNotesUsingContentResolver(
    context:Context
) :List<Note>{



    /**
     * this content resolver contains 4 operations
     * insert
     * update
     * delete
     * query
     */
    val myList = mutableListOf<Note>()

    /**
     * we will use query here to get the data from the content provider
     * the authority and content url must be the same as in the manifest file
     */
    val cursor = context.contentResolver.query(
        CONTENT_URI,
        null,
        null,
        null,
        null
    )

    // check if my cursor is not null first
    cursor?.let {
        // check weather we have next item in the list or not
        while (it.moveToNext()) {
            // get the column index from the cursor
            val id = it.getInt(it.getColumnIndexOrThrow("id"))
            val title = it.getString(it.getColumnIndexOrThrow("title"))
            val desc = it.getString(it.getColumnIndexOrThrow("desc"))
            myList.add(
                Note(
                    id = id,
                    title = title,
                    desc = desc
                )
            )
        }
        it.close()

    }
    return myList
}

    /**
     * this function is used to insert data into the content provider
     **/
    fun ContentResolver.insertNote(
        title:String,
        desc:String

    ){
        val values = ContentValues().apply {
            put("title", title)
            put("desc", desc)
        }
        insert(CONTENT_URI, values)

    }

    /**
     * this function is used to update data into the content provider
     **/
    fun ContentResolver.updateNote(
        id:Int,
        title:String,
        desc:String
    ){
        val values = ContentValues().apply {
            put("title", title)
            put("desc", desc)
        }
        val uri = Uri.parse("$CONTENT_URI/$id")
        update(uri, values, "id=?", arrayOf(id.toString()))
    }

    /**
     * this function is used to delete data into the content provider
     **/
    fun ContentResolver.deleteNote(
        id:Int
    ){
        val uri = Uri.parse("$CONTENT_URI/$id")
        delete(uri, "id=?", arrayOf(id.toString()))
    }

    fun ContentResolver.observe(context: Context, uri: Uri) = callbackFlow{
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                val notes = getAllNotesUsingContentResolver(context)
                trySend(notes)
            }
        }
        registerContentObserver(uri, true, observer)
        awaitClose {
            unregisterContentObserver(observer)
        }
    }