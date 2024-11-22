package com.degel.my_custom_content_provider.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long


    @Update
    suspend fun update(noteEntity: NoteEntity): Int

    @Delete
    suspend fun delete(noteEntity: NoteEntity): Int


    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun getAllNoteId(id:Int): NoteEntity

}