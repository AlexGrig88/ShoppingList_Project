package com.alexgrig.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexgrig.shoppinglist.data.room.ShoppingDatabase
import com.alexgrig.shoppinglist.data.room.entities.ShoppingItem
import com.alexgrig.shoppinglist.data.room.repositories.ShoppingRepository
import com.alexgrig.shoppinglist.databinding.ActivityShoppingBinding
import com.alexgrig.shoppinglist.utils.AddEditListener
import com.alexgrig.shoppinglist.utils.ShoppingItemActionListener
import com.alexgrig.shoppinglist.utils.ShoppingItemsAdapter
import com.alexgrig.shoppinglist.viewmodels.ShoppingViewModel
import com.alexgrig.shoppinglist.viewmodels.ShoppingViewModelFactory
import kotlin.math.round

class ShoppingActivity : AppCompatActivity(), ShoppingItemActionListener{

    lateinit var shoppingViewModel: ShoppingViewModel
    lateinit var adapter: ShoppingItemsAdapter
    lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shoppingRepository = ShoppingRepository(ShoppingDatabase(this))
        val factory = ShoppingViewModelFactory(shoppingRepository)
        shoppingViewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        binding.rvShoppingList.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingItemsAdapter(this)
        binding.rvShoppingList.adapter = adapter

        shoppingViewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it.reversed()
            updateUI(it)
        })

        binding.floatButtonAdd.setOnClickListener {
            AddShoppingItemDialog(
                this,
                ShoppingItem("", 0.0, 0.0, 0),
                object : AddEditListener {
                override fun onClickAddOrEdit(item: ShoppingItem) {
                    shoppingViewModel.insert(item)
                }
            }).show()
        }
    }

    private fun updateUI(items: List<ShoppingItem>) {
        val totalCost = items.filter { it.isBought == 1 }.sumOf { adapter.getCost(it) }
        binding.tvTotalValue.text = "$totalCost руб."
    }

    override fun onDeleteItem(item: ShoppingItem) {
        shoppingViewModel.delete(item)
    }

    override fun onPlusOneCounter(item: ShoppingItem) {
        item.amount = round((item.amount + 1.0) * 100) / 100
        shoppingViewModel.update(item)
    }

    override fun onMinusOneCounter(item: ShoppingItem) {
        if (item.amount > 1.0) {
            item.amount = round((item.amount - 1.0) * 100) / 100
            shoppingViewModel.update(item)
        }
    }

    override fun onEditItem(item: ShoppingItem) {
        AddShoppingItemDialog(this, item, object : AddEditListener {
            override fun onClickAddOrEdit(item: ShoppingItem) {
                shoppingViewModel.update(item)
            }
        }).show()
    }

    override fun onClickBought(item: ShoppingItem) {
        item.isBought = if (item.isBought == 1) 0 else 1
        shoppingViewModel.update(item)
    }

}