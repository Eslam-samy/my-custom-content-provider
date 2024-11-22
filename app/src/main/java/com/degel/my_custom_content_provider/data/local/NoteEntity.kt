package com.degel.my_custom_content_provider.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTES_TABLE = "notes"

@Entity(NOTES_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("desc") val desc: String

) {
    constructor() : this(0, "", "")
}
