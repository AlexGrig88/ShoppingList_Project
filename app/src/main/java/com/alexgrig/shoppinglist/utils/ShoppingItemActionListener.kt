package com.alexgrig.shoppinglist.utils

import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem

interface ShoppingItemActionListener {
    fun onDeleteItem(item: ShoppingItem)
    fun onPlusOneCounter(item: ShoppingItem)
    fun onMinusOneCounter(item: ShoppingItem)
    fun onEditItem(item: ShoppingItem)
    fun onClickBought(item: ShoppingItem)
}