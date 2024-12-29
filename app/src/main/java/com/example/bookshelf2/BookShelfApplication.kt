package com.example.bookshelf2

import android.app.Application
import com.example.bookshelf2.data.AppContainer
import com.example.bookshelf2.data.DefaultAppContainer

class BookshelfApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}