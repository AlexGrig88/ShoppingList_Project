package com.alexgrig.shoppinglist.utils

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexgrig.shoppinglist.R
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem
import com.alexgrig.shoppinglist.databinding.ShoppingItemBinding
import kotlin.math.round


class ShoppingItemsAdapter(
    private val actionListener: ShoppingItemActionListener
): RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingItemViewHolder>(), View.OnClickListener {

    var items: List<ShoppingItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding =
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.deleteIv.setOnClickListener(this)
        binding.plusIv.setOnClickListener(this)
        binding.minusIv.setOnClickListener(this)
        binding.ivEdit.setOnClickListener(this)
        binding.checkBoxBought.setOnClickListener(this)
        Log.d("TagCreate", "OncreateViewHolder")
        return ShoppingItemViewHolder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ShoppingItemViewHolder, pos: Int) {
        val shoppingItem = items[pos]
        holder.binding.apply {
            deleteIv.tag = shoppingItem
            plusIv.tag = shoppingItem
            minusIv.tag = shoppingItem
            ivEdit.tag = shoppingItem
            checkBoxBought.tag = shoppingItem
            productNameTv.text = shoppingItem.name
            if (shoppingItem.isBought == 1) {
                checkBoxBought.isChecked = true
                productNameTv.strike = true
            } else {
                checkBoxBought.isChecked = false
                productNameTv.strike = false
            }
            tvCost.text = getCost(shoppingItem).toString() + " руб."
            tvAmount.text = shoppingItem.amount.toString()
        }
        Log.d("TagId", shoppingItem.id.toString())
    }

    override fun onClick(v: View) {
        val currentItem = v.tag as ShoppingItem
        when (v.id) {
            R.id.deleteIv -> actionListener.onDeleteItem(currentItem)
            R.id.plusIv -> actionListener.onPlusOneCounter(currentItem)
            R.id.minusIv -> actionListener.onMinusOneCounter(currentItem)
            R.id.checkBoxBought -> actionListener.onClickBought(currentItem)
            else -> actionListener.onEditItem(currentItem)
        }
    }

    fun getCost(shoppingItem: ShoppingItem) = round(shoppingItem.price * shoppingItem.amount * 100) / 100

    inner class ShoppingItemViewHolder(val binding: ShoppingItemBinding): RecyclerView.ViewHolder(binding.root)

    inline var TextView.strike: Boolean
        set(visible) {
            paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG

}