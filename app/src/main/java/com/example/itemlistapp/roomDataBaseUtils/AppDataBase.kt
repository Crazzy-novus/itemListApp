package com.example.itemlistapp.roomDataBaseUtils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itemlistapp.model.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}