package com.korostylev.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.korostylev.shoppinglist.domain.ShopItem

//коллбек для сравнения списков
class ShopListDiffCallback(
    private var oldList: List<ShopItem>,
    private var newList: List<ShopItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}