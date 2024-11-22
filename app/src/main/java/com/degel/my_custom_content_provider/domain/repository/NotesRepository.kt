package com.degel.my_custom_content_provider.domain.repository

import com.degel.my_custom_content_provider.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insert(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    fun getAllNotes(): Flow<List<Note>>

}