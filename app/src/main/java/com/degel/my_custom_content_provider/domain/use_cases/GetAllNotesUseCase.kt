package com.degel.my_custom_content_provider.domain.use_cases

import com.degel.my_custom_content_provider.domain.repository.NotesRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val notesRepository: NotesRepository) {

    operator fun invoke() = notesRepository.getAllNotes()

}