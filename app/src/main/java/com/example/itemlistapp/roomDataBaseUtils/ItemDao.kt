package com.example.itemlistapp.roomDataBaseUtils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.itemlistapp.model.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAllItems(): MutableList<Item>

    @Insert
    fun insertItem(item: Item): Long

    @Update
    fun updateItem(item : Item)

    @Delete
    fun delete(item : Item)
}