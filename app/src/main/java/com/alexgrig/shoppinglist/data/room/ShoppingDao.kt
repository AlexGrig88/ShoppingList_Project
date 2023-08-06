package com.alexgrig.shoppinglist.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ShoppingItem)

    @Update
    suspend fun update(item: ShoppingItem)
    
    @Delete
    suspend fun delete(item: ShoppingItem)
    
    @Query("SELECT * FROM shopping_items")
    fun getAll(): LiveData<List<ShoppingItem>>
}