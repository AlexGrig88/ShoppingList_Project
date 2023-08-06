package com.alexgrig.shoppinglist.utils

import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem

interface AddEditListener {
    fun onClickAddOrEdit(item: ShoppingItem)
}