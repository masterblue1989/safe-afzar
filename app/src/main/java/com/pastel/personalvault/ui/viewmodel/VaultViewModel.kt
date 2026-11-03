package com.pastel.personalvault.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pastel.personalvault.data.local.AppDatabase
import com.pastel.personalvault.data.model.CategoryGroup
import com.pastel.personalvault.data.model.ItemType
import com.pastel.personalvault.data.model.PersonalItem
import com.pastel.personalvault.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VaultViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository

    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow(CategoryGroup.ALL)

    val items: StateFlow<List<PersonalItem>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ItemRepository(database.itemDao())

        items = combine(
            repository.getAllItems(),
            searchQuery,
            selectedCategory
        ) { allItems, query, category ->
            allItems.filter { item ->
                val matchesQuery = query.isBlank() ||
                        item.title.contains(query, ignoreCase = true) ||
                        item.primaryValue.contains(query, ignoreCase = true) ||
                        item.secondaryValue1.contains(query, ignoreCase = true) ||
                        item.secondaryValue2.contains(query, ignoreCase = true) ||
                        item.notes.contains(query, ignoreCase = true)

                val matchesCategory = when (category) {
                    CategoryGroup.ALL -> true
                    else -> item.type.categoryGroup == category
                }

                matchesQuery && matchesCategory
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // درج اطلاعات اولیه نمونه به زبان فارسی در اولین اجرای برنامه
        viewModelScope.launch {
            val existing = repository.getAllItems().first()
            if (existing.isEmpty()) {
                seedInitialData()
            }
        }
    }

    private suspend fun seedInitialData() {
        val samples = listOf(
            // ذخیره ۳ کد پستی مختلف (منزل، محل کار، سایر)
            PersonalItem.createSanitized(
                title = "کدهای پستی سه‌گانه من",
                type = ItemType.POSTAL_CODE,
                primaryValue = "1458932145",   // کد پستی اول: منزل
                secondaryValue1 = "1939547891", // کد پستی دوم: محل کار
                secondaryValue2 = "4671895231", // کد پستی سوم: ویلا / سایر
                notes = "کد پستی منزل، شرکت و ویلای شمال",
                isFavorite = true
            ),
            PersonalItem.createSanitized(
                title = "کارت ملی هوشمند",
                type = ItemType.NATIONAL_CODE,
                primaryValue = "0012345678",
                secondaryValue1 = "2D9847125",
                notes = "کد ملی ۱۰ رقمی و سریال پشت کارت هوشمند ملی",
                isFavorite = true
            ),
            PersonalItem.createSanitized(
                title = "کارت بانک ملت (حقوق)",
                type = ItemType.BANK_CARD,
                primaryValue = "6104337890123456",
                secondaryValue1 = "782",
                secondaryValue2 = "07/28",
                secondaryValue3 = "120120000000001234567890",
                notes = "واریز حقوق ماهانه و امور بانکی روزمره",
                isFavorite = true
            ),
            PersonalItem.createSanitized(
                title = "شناسنامه فردی",
                type = ItemType.BIRTH_CERTIFICATE,
                primaryValue = "123456",
                secondaryValue1 = "الف/۳۸",
                notes = "صادره از حوزه پنج ثبت احوال تهران"
            ),
            PersonalItem.createSanitized(
                title = "کارت بلو بانک (خرید اینترنتی)",
                type = ItemType.BANK_CARD,
                primaryValue = "6219861099887766",
                secondaryValue1 = "490",
                secondaryValue2 = "09/29",
                notes = "شارژ هفتگی جهت خریدهای اینترنتی و اسنپ"
            )
        )

        samples.forEach { repository.insertItem(it) }
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun onCategorySelected(category: CategoryGroup) {
        selectedCategory.value = category
    }

    fun saveItem(item: PersonalItem) {
        viewModelScope.launch {
            if (item.id == 0L) {
                repository.insertItem(item)
            } else {
                repository.updateItem(item)
            }
        }
    }

    fun deleteItem(item: PersonalItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun toggleFavorite(item: PersonalItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item.id, !item.isFavorite)
        }
    }
}
