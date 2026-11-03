package com.pastel.personalvault.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pastel.personalvault.data.model.ItemType
import com.pastel.personalvault.data.model.PersonalItem
import com.pastel.personalvault.ui.theme.PastelBorder
import com.pastel.personalvault.ui.theme.PastelForestText
import com.pastel.personalvault.ui.theme.PastelMint100
import com.pastel.personalvault.ui.theme.PastelMintPrimary
import com.pastel.personalvault.ui.theme.PastelMintSubtle
import com.pastel.personalvault.ui.theme.PastelTextSubtle
import com.pastel.personalvault.utils.NumberUtils

@Composable
fun ItemDialog(
    initialItem: PersonalItem? = null,
    onDismiss: () -> Unit,
    onSave: (PersonalItem) -> Unit
) {
    var title by remember { mutableStateOf(initialItem?.title ?: "") }
    var selectedType by remember { mutableStateOf(initialItem?.type ?: ItemType.POSTAL_CODE) }
    var primaryValue by remember { mutableStateOf(initialItem?.primaryValue ?: "") }
    var secondaryValue1 by remember { mutableStateOf(initialItem?.secondaryValue1 ?: "") }
    var secondaryValue2 by remember { mutableStateOf(initialItem?.secondaryValue2 ?: "") }
    var secondaryValue3 by remember { mutableStateOf(initialItem?.secondaryValue3 ?: "") }
    var notes by remember { mutableStateOf(initialItem?.notes ?: "") }
    var validationError by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // سربرگ دیالوگ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (initialItem == null) "افزودن اطلاعات جدید" else "ویرایش اطلاعات ذخیره‌شده",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PastelForestText
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Rounded.Close, contentDescription = "بستن", tint = PastelTextSubtle)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // انتخاب نوع مدرک
                Text(
                    text = "نوع مدرک یا اطلاعات مورد نظر:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = PastelTextSubtle
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    val types = listOf(
                        ItemType.POSTAL_CODE,
                        ItemType.NATIONAL_CODE,
                        ItemType.BANK_CARD,
                        ItemType.BIRTH_CERTIFICATE,
                        ItemType.NATIONAL_CARD_BACK_SERIAL,
                        ItemType.CUSTOM
                    )

                    types.chunked(2).forEach { rowTypes ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowTypes.forEach { type ->
                                val isSelected = selectedType == type
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) PastelMint100 else Color(0xFFF6FAF7))
                                        .border(
                                            1.dp,
                                            if (isSelected) PastelMintPrimary else PastelBorder,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .clickable {
                                            selectedType = type
                                            if (title.isEmpty() || types.any { it.titleFa == title }) {
                                                title = when (type) {
                                                    ItemType.POSTAL_CODE -> "کدهای پستی من (۳ آدرس)"
                                                    ItemType.NATIONAL_CODE -> "کارت ملی هوشمند"
                                                    ItemType.BANK_CARD -> "کارت بانکی"
                                                    ItemType.BIRTH_CERTIFICATE -> "شناسنامه"
                                                    ItemType.NATIONAL_CARD_BACK_SERIAL -> "سریال پشت کارت ملی"
                                                    ItemType.CUSTOM -> "اطلاعات دلخواه"
                                                }
                                            }
                                        }
                                        .padding(horizontal = 8.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = type.titleFa,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) PastelMintPrimary else PastelForestText
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // عنوان ورودی
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("عنوان مورد (مثلاً: کدهای پستی من یا کارت ملی)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PastelMintPrimary,
                        unfocusedBorderColor = PastelBorder
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // فیلدهای اختصاصی متناسب با نوع سند
                when (selectedType) {
                    // گرفتن ۳ کد پستی مختلف (منزل، محل کار، سایر)
                    ItemType.POSTAL_CODE -> {
                        // کد پستی اول (منزل)
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("کد پستی اول - منزل (۱۰ رقم)") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // کد پستی دوم (محل کار)
                        OutlinedTextField(
                            value = secondaryValue1,
                            onValueChange = { secondaryValue1 = NumberUtils.toEnglishDigits(it) },
                            label = { Text("کد پستی دوم - محل کار (۱۰ رقم)") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // کد پستی سوم (سایر یا دلخواه)
                        OutlinedTextField(
                            value = secondaryValue2,
                            onValueChange = { secondaryValue2 = NumberUtils.toEnglishDigits(it) },
                            label = { Text("کد پستی سوم - سایر / دلخواه (۱۰ رقم)") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }

                    // کارت بانکی
                    ItemType.BANK_CARD -> {
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("شماره ۱۶ رقمی کارت بانکی") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )

                        if (primaryValue.length >= 6) {
                            val detectedBank = NumberUtils.detectBankName(primaryValue)
                            Text(
                                text = "نام بانک صادرکننده: $detectedBank",
                                fontSize = 12.sp,
                                color = PastelMintPrimary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = secondaryValue1,
                                onValueChange = { secondaryValue1 = NumberUtils.toEnglishDigits(it) },
                                label = { Text("کد CVV2") },
                                singleLine = true,
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PastelMintPrimary,
                                    unfocusedBorderColor = PastelBorder
                                )
                            )

                            OutlinedTextField(
                                value = secondaryValue2,
                                onValueChange = { secondaryValue2 = NumberUtils.toEnglishDigits(it) },
                                label = { Text("انقضا (ماه/سال)") },
                                singleLine = true,
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = PastelMintPrimary,
                                    unfocusedBorderColor = PastelBorder
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryValue3,
                            onValueChange = { secondaryValue3 = NumberUtils.toEnglishDigits(it) },
                            label = { Text("شماره شبا (با پیشوند IR)") },
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = FontFamily.Monospace),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }

                    // کارت ملی
                    ItemType.NATIONAL_CODE -> {
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("کد ملی (۱۰ رقم)") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryValue1,
                            onValueChange = { secondaryValue1 = NumberUtils.toEnglishDigits(it) },
                            label = { Text("سریال پشت کارت هوشمند ملی") },
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = FontFamily.Monospace),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }

                    // شناسنامه
                    ItemType.BIRTH_CERTIFICATE -> {
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("شماره عددی شناسنامه") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryValue1,
                            onValueChange = { secondaryValue1 = it },
                            label = { Text("سری و حرف شناسنامه (مثلاً: الف / ۳۸)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }

                    // سریال پشت کارت ملی
                    ItemType.NATIONAL_CARD_BACK_SERIAL -> {
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("سریال ۱۰ رقمی یا حروفی پشت کارت ملی") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }

                    // دلخواه
                    ItemType.CUSTOM -> {
                        OutlinedTextField(
                            value = primaryValue,
                            onValueChange = { primaryValue = NumberUtils.toEnglishDigits(it) },
                            label = { Text("شناسه یا شماره مورد نظر") },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PastelForestText
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PastelMintPrimary,
                                unfocusedBorderColor = PastelBorder
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // فیلد یادداشت دلخواه
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("یادداشت یا توضیحات تکمیلی (اختیاری)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PastelMintPrimary,
                        unfocusedBorderColor = PastelBorder
                    )
                )

                // نوار آگاهی: تضمین ذخیره با ارقام انگلیسی
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PastelMintSubtle)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = null,
                        tint = PastelMintPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "تمامی شماره‌ها به صورت خودکار با ارقام استاندارد انگلیسی (0-9) ذخیره می‌شوند تا کپی و پیست بدون خطا باشد.",
                        fontSize = 11.sp,
                        color = PastelForestText,
                        lineHeight = 16.sp
                    )
                }

                if (validationError != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = validationError ?: "",
                        color = Color(0xFFD32F2F),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // دکمه‌های انصراف و ذخیره
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("انصراف", color = PastelTextSubtle)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (title.isBlank()) {
                                validationError = "لطفاً عنوان را وارد فرمایید."
                                return@Button
                            }
                            if (primaryValue.isBlank()) {
                                validationError = "لطفاً مقدار اصلی شماره را وارد فرمایید."
                                return@Button
                            }

                            val sanitizedItem = PersonalItem.createSanitized(
                                id = initialItem?.id ?: 0,
                                title = title,
                                type = selectedType,
                                primaryValue = primaryValue,
                                secondaryValue1 = secondaryValue1,
                                secondaryValue2 = secondaryValue2,
                                secondaryValue3 = secondaryValue3,
                                notes = notes,
                                isFavorite = initialItem?.isFavorite ?: false
                            )

                            onSave(sanitizedItem)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PastelMintPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("ذخیره در گاوصندوق", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
