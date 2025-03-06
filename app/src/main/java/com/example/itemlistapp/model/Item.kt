package com.example.itemlistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemId: Int? = null,
    var itemImageUri: String? = null,
    var itemName: String = "",
    var skuNumber: Int  = 0,
    var stockQuantity: Int = 0,
    var description: String = ""
) : Serializable
