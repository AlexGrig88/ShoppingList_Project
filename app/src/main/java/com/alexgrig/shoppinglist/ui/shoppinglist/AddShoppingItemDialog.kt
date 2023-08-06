package com.alexgrig.shoppinglist.ui.shoppinglist

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatDialog
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem
import com.alexgrig.shoppinglist.databinding.AddShoppingItemDialogBinding
import com.alexgrig.shoppinglist.utils.AddEditListener

class AddShoppingItemDialog(context: Context,
                            private val shoppingItem: ShoppingItem,
                            private val listener: AddEditListener,
                            ): AppCompatDialog(context) {

    lateinit var binding: AddShoppingItemDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddShoppingItemDialogBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.apply {
            if (shoppingItem.id != null) {
                etProductName.text = SpannableStringBuilder(shoppingItem.name)
                etAmount.text = SpannableStringBuilder(shoppingItem.amount.toString())
                etPrice.text = SpannableStringBuilder(shoppingItem.price.toString())
            }
        }
        binding.buttonOk.setOnClickListener { upsertProduct() }
        binding.buttonCancel.setOnClickListener { cancel() }
    }

    private fun upsertProduct() {
        binding.apply {
            val item = if (shoppingItem.id != 0) {
                shoppingItem.name = etProductName.text.toString()
                shoppingItem.amount = parseDouble(etAmount.text)
                shoppingItem.price = parseDouble(etPrice.text)
                shoppingItem
            } else {
                val name = etProductName.text.toString()
                val price = parseDouble(etPrice.text)
                val amount = parseDouble(etAmount.text)
                ShoppingItem(name, amount, price, 0)
            }
            listener.onClickAddOrEdit(item)
        }
        dismiss()
    }

    private fun parseDouble(value: Editable) = if (value.isNotEmpty()) value.toString().toDouble() else 0.0
}