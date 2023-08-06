package com.alexgrig.shoppinglist.data.room.repositories

import com.alexgrig.shoppinglist.data.room.ShoppingDatabase
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem

class ShoppingRepository(private val db: ShoppingDatabase) {

    private val dao = db.getShoppingDao()

    suspend fun insert(item: ShoppingItem) = dao.insert(item)

    suspend fun update(item: ShoppingItem) = dao.update(item)

    suspend fun delete(item: ShoppingItem) = dao.delete(item)

    fun getAllItems() = dao.getAll()
}