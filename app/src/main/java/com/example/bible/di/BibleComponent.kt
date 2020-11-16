package com.example.bible.di

import com.example.bible.ui.books.BookFragment
import com.example.bible.ui.home.HomeFragment
import com.example.bible.ui.readbook.ReadingPageFragment
import com.example.bible.ui.search.SearchFragment
import com.example.data.di.BibleModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BibleModule::class])
interface BibleComponent {

    fun inject(bookFragment: BookFragment)
    fun inject(readingPageFragment: ReadingPageFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(homeFragment: HomeFragment)
}
