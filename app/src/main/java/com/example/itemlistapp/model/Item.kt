package com.example.itemlistapp.model

import android.net.Uri


data class Item(
    var itemImageUri: String? = null,
    var itemName: String = "",
    var skuNumber: Int = 0,
    var stockQuantity: Int = 0,
    var description: String = ""
)

//data class Item(
//    var itemImageUri: Uri? = null,
//    var itemName: String = "",
//    var skuNumber: Int = 0,
//    var stockQuantity: Int = 0,
//    var description: String = ""
//)
