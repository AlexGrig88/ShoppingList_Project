package com.alexgrig.shoppinglist.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String,
    var amount: Double,
    var price: Double,
    var isBought: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}