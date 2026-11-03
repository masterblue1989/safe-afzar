package com.pastel.personalvault.data.repository

import com.pastel.personalvault.data.local.ItemDao
import com.pastel.personalvault.data.model.PersonalItem
import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {

    fun getAllItems(): Flow<List<PersonalItem>> = itemDao.getAllItems()

    fun searchItems(query: String): Flow<List<PersonalItem>> = itemDao.searchItems(query)

    suspend fun getItemById(id: Long): PersonalItem? = itemDao.getItemById(id)

    suspend fun insertItem(item: PersonalItem): Long = itemDao.insertItem(item)

    suspend fun updateItem(item: PersonalItem) = itemDao.updateItem(item)

    suspend fun deleteItem(item: PersonalItem) = itemDao.deleteItem(item)

    suspend fun deleteById(id: Long) = itemDao.deleteById(id)

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        itemDao.updateFavorite(id, isFavorite)
    }
}
