package com.degel.my_custom_content_provider.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.degel.my_custom_content_provider.data.local.NotesDao
import com.degel.my_custom_content_provider.data.local.NotesDatabase
import com.degel.my_custom_content_provider.data.repository.NotesRepoImpl
import com.degel.my_custom_content_provider.domain.repository.NotesRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotesDataModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.getNotesDao()
    }

    @Provides
    fun provideRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepoImpl(notesDao)
    }

}