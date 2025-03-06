package com.example.itemlistapp

import android.app.Application
import androidx.room.Room
import com.example.itemlistapp.roomDataBaseUtils.AppDataBase

open class MyApplication : Application() {

    companion object {
        var db: AppDataBase? = null

        fun getDatabase(): AppDataBase? {
            return db
        }

    }
    override fun onCreate() {
        super.onCreate()
        db= Room.databaseBuilder(applicationContext, AppDataBase::class.java,"itemDB").allowMainThreadQueries().build()
    }

}