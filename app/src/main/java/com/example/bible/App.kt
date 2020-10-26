package com.example.bible

import android.app.Application
import com.example.bible.di.BibleComponent
import com.example.bible.di.DaggerBibleComponent
import com.example.data.di.BibleModule


class App : Application() {

    lateinit var bibleComponent: BibleComponent

    override fun onCreate() {
        super.onCreate()
        bibleComponent = DaggerBibleComponent.builder()
            .bibleModule(BibleModule(applicationContext))
            .build()
    }
}