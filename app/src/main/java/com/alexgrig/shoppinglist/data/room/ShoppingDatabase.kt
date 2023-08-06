package com.alexgrig.shoppinglist.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDatabase: RoomDatabase() {
    
    abstract fun getShoppingDao(): ShoppingDao
    
    companion object {
        @Volatile
        var instance: ShoppingDatabase? = null
        private val mutex = Any()
        // вызывается при создании объекта
        operator fun invoke(context: Context) = instance ?: synchronized(mutex) {
            instance ?: createDatabase(context).also { instance = it }
        }
        
        private fun createDatabase(context: Context) = 
            Room.databaseBuilder(context.applicationContext,
                ShoppingDatabase::class.java, "ShoppingDb.db").build()
    }
}