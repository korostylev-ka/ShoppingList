package com.korostylev.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korostylev.shoppinglist.domain.ShopItem
import com.korostylev.shoppinglist.domain.ShopListRepository
import java.util.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 100) {
            val item = ShopItem("Name $i", i, kotlin.random.Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        Log.d("MainActivity", "add")
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        Log.d("MainActivity", "${shopItem.toString()}")
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}