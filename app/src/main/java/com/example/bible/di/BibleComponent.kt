package com.example.bible.di

import com.example.bible.ui.books.BookFragment
import com.example.data.di.BibleModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BibleModule::class])
interface BibleComponent {

fun inject(bookFragment: BookFragment)
}
