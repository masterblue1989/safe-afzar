package com.pastel.personalvault.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pastel.personalvault.utils.NumberUtils

@Entity(tableName = "personal_items")
data class PersonalItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val type: ItemType,
    // مقادیر عددی همیشه به صورت ارقام استاندارد انگلیسی ذخیره می‌شوند
    val primaryValue: String,        // برای کارت: شماره کارت | برای کد ملی: ۱۰ رقم | برای کد پستی: کد پستی اول (منزل)
    val secondaryValue1: String = "", // برای کارت: کد CVV2 | برای شناسنامه: سری/حرف | برای کد پستی: کد پستی دوم (محل کار)
    val secondaryValue2: String = "", // برای کارت: تاریخ انقضا | برای کد پستی: کد پستی سوم (سایر/دلخواه)
    val secondaryValue3: String = "", // برای کارت: شماره شبا | برای کد پستی: برچسب یا عنوان کدها
    val notes: String = "",
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * تضمین تبدیل تمامی ارقام وارد شده فارسی و عربی به ارقام استاندارد انگلیسی قبل از ذخیره
         */
        fun createSanitized(
            id: Long = 0,
            title: String,
            type: ItemType,
            primaryValue: String,
            secondaryValue1: String = "",
            secondaryValue2: String = "",
            secondaryValue3: String = "",
            notes: String = "",
            isFavorite: Boolean = false
        ): PersonalItem {
            return PersonalItem(
                id = id,
                title = title.trim(),
                type = type,
                primaryValue = NumberUtils.toEnglishDigits(primaryValue.trim()),
                secondaryValue1 = NumberUtils.toEnglishDigits(secondaryValue1.trim()),
                secondaryValue2 = NumberUtils.toEnglishDigits(secondaryValue2.trim()),
                secondaryValue3 = NumberUtils.toEnglishDigits(secondaryValue3.trim()),
                notes = notes.trim(),
                isFavorite = isFavorite,
                updatedAt = System.currentTimeMillis()
            )
        }
    }
}
