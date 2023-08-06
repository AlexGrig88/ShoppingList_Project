package com.alexgrig.shoppinglist.viewmodels

import androidx.lifecycle.ViewModel
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem
import com.alexgrig.shoppinglist.data.room.repositories.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val repository: ShoppingRepository
): ViewModel() {

    fun insert(item: ShoppingItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.insert(item)
    }
    fun update(item: ShoppingItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(item)
    }

    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }

    fun getAllShoppingItems() = repository.getAllItems()

}