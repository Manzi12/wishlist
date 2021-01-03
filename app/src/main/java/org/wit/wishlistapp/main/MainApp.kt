package org.wit.wishlistapp.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.wishlistapp.models.WishMemStore

class MainApp: Application(), AnkoLogger {
    val wishies = WishMemStore()

    override fun onCreate() {
        super.onCreate()
        info ("Wish app has started")
    }
}