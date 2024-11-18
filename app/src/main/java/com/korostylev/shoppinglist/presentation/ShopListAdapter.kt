package com.korostylev.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.korostylev.shoppinglist.R
import com.korostylev.shoppinglist.databinding.ItemShopDisabledBinding
import com.korostylev.shoppinglist.databinding.ItemShopEnabledBinding
import com.korostylev.shoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("unknown view type $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()

            }
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()

            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }



    companion object {

        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOL_SIZE = 15
    }
}