package com.degel.my_custom_content_provider.data.mappers

import com.degel.my_custom_content_provider.data.local.NoteEntity
import com.degel.my_custom_content_provider.domain.model.Note

fun Note.toNoteEntity():NoteEntity{
    return NoteEntity(id,title,desc)
}
fun NoteEntity.toNote():Note{
    return Note (id,title,desc)
}