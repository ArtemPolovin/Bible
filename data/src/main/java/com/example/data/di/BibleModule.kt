package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.data.db.Database
import com.example.data.db.dao.ICellHomeDao
import com.example.data.db.dao.INoteDao
import com.example.data.implementationRepo.BookRepositoryImpl
import com.example.data.implementationRepo.CellHomeRepositoryImpl
import com.example.data.implementationRepo.NoteRepositoryImpl
import com.example.data.mappers.CellHomeMapper
import com.example.data.mappers.MapFromBookToCellBook
import com.example.data.mappers.NoteMapper
import com.example.data.utils.BibleConverter
import com.example.data.utils.BibleDataCache
import com.example.data.utils.SHARED_PREF
import com.example.domain.repositories.IBookRepository
import com.example.domain.repositories.ICellHomeRepo
import com.example.domain.repositories.INoteRepository
import com.example.domain.usecases.GetBooksListUseCase
import com.example.domain.usecases.GetCellBookListUseCase
import com.example.domain.usecases.GetCellHomeListUseCase
import com.example.domain.usecases.noteusecases.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class BibleModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    fun provideBibleConverter(gson: Gson) = BibleConverter(gson, context)

    @Provides
    fun provideMapFromBookListToCellBookList() = MapFromBookToCellBook()

    @Provides
    fun provideCellHomeMapper() = CellHomeMapper()

    @Provides
    fun provideNoteMapper() = NoteMapper()

    @Provides
    @Singleton
    fun provideCellHomeRepoImpl(
        cellHomeMapper: CellHomeMapper,
        cellHomeDao: ICellHomeDao,
        @Named("io")
        schedulersIO: Scheduler
    ): ICellHomeRepo {
        return CellHomeRepositoryImpl(cellHomeDao, cellHomeMapper, schedulersIO)
    }

    @Provides
    @Singleton
    fun provideBooksListRepoImpl(
        booksConverter: BibleConverter,
        mapper: MapFromBookToCellBook,
        @Named("io")
        schedulersIO: Scheduler
    ): IBookRepository =
        BookRepositoryImpl(booksConverter, mapper, schedulersIO)

    @Provides
    @Singleton
    fun provideNoteRepositoryImpl(
        mapper: NoteMapper, noteDao: INoteDao, @Named("io")
        schedulersIO: Scheduler
    ): INoteRepository =
        NoteRepositoryImpl(noteDao, mapper, schedulersIO)

    @Provides
    fun provideGetBooksListUseCase(bookRepository: IBookRepository) =
        GetBooksListUseCase(bookRepository)

    @Provides
    fun provideGetCellBookListUseCase(bookRepository: IBookRepository) =
        GetCellBookListUseCase(bookRepository)

    @Provides
    fun provideGetCellHomeListUseCase(cellHomeRepo: ICellHomeRepo) =
        GetCellHomeListUseCase(cellHomeRepo)

    @Provides
    fun provideFetchTopicsUseCase(noteRepository: INoteRepository) =
        FetchTopicsUseCase(noteRepository)

    @Provides
    fun provideInsertTopicToDBUseCase(noteRepository: INoteRepository) =
        InsertTopicToDBUseCase(noteRepository)

    @Provides
    fun provideRemoveTopicUseCase(noteRepository: INoteRepository) =
        RemoveTopicUseCase(noteRepository)

    @Provides
    fun provideFetchNotesUseCase(noteRepository: INoteRepository) =
        FetchNotesUseCase(noteRepository)

    @Provides
    fun provideInsertNoteUseCase(noteRepository: INoteRepository) =
        InsertNoteUseCase(noteRepository)

    @Provides
    fun provideRemoveNoteUseCase(noteRepository: INoteRepository) =
        RemoveNoteUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context.applicationContext, Database::class.java, "cellHomeDB")
            // .fallbackToDestructiveMigration()
            // .addMigrations(MIGRATION_3_4)
            .build()
    }

    @Provides
    @Singleton
    fun provideCellHomeDao(database: Database) = database.getCellHomeDao()

    @Provides
    @Singleton
    fun provideNoteDao(database: Database) = database.getNoteDao()

    @Provides
    @Named("io")
    fun provideSchedulersIO(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideSharePreferences(): SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideBookDataCache(sharedPref: SharedPreferences) = BibleDataCache(sharedPref)


}