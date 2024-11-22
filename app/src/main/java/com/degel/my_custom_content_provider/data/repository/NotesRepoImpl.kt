package com.degel.my_custom_content_provider.data.repository

import com.degel.my_custom_content_provider.data.local.NotesDao
import com.degel.my_custom_content_provider.data.mappers.toNote
import com.degel.my_custom_content_provider.data.mappers.toNoteEntity
import com.degel.my_custom_content_provider.domain.model.Note
import com.degel.my_custom_content_provider.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepoImpl(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun insert(note: Note) {
        notesDao.insert(note.toNoteEntity())
    }

    override suspend fun update(note: Note) {
        notesDao.update(note.toNoteEntity())
    }

    override suspend fun delete(note: Note) {
        notesDao.delete(note.toNoteEntity())
    }

    override fun getAllNotes(): Flow<List<Note>> =
        notesDao.getAllNotes().map { it.map { it.toNote() } }
}