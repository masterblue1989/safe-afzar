package com.pastel.personalvault.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Pin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pastel.personalvault.data.model.ItemType
import com.pastel.personalvault.data.model.PersonalItem
import com.pastel.personalvault.ui.theme.PastelBorder
import com.pastel.personalvault.ui.theme.PastelCardSurface
import com.pastel.personalvault.ui.theme.PastelForestText
import com.pastel.personalvault.ui.theme.PastelMint100
import com.pastel.personalvault.ui.theme.PastelMintPrimary
import com.pastel.personalvault.ui.theme.PastelTextSubtle
import com.pastel.personalvault.utils.NumberUtils

@Composable
fun PastelCard(
    item: PersonalItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val icon: ImageVector = when (item.type) {
        ItemType.BANK_CARD -> Icons.Rounded.CreditCard
        ItemType.NATIONAL_CODE -> Icons.Rounded.Badge
        ItemType.POSTAL_CODE -> Icons.Rounded.LocalShipping
        ItemType.BIRTH_CERTIFICATE -> Icons.Rounded.MenuBook
        ItemType.NATIONAL_CARD_BACK_SERIAL -> Icons.Rounded.Pin
        ItemType.CUSTOM -> Icons.Rounded.Badge
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.2.dp, PastelBorder, RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = PastelCardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // سطر سربرگ: عنوان، نشان نوع سند، علاقه‌مندی و منوی عملیات
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(PastelMint100),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = item.type.titleFa,
                            tint = PastelMintPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = item.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelForestText
                        )
                        Text(
                            text = item.type.titleFa,
                            fontSize = 11.sp,
                            color = PastelMintPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // دکمه نشان‌گذاری علاقه‌مندی
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "نشان‌گذاری",
                            tint = if (item.isFavorite) Color(0xFFE57373) else PastelTextSubtle,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    // منوی سه‌نقطه عملیات
                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "عملیات",
                                tint = PastelTextSubtle,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("ویرایش اطلاعات") },
                                onClick = {
                                    menuExpanded = false
                                    onEdit()
                                },
                                leadingIcon = {
                                    Icon(Icons.Rounded.Edit, contentDescription = null, tint = PastelMintPrimary)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("حذف از گاوصندوق", color = Color(0xFFD32F2F)) },
                                onClick = {
                                    menuExpanded = false
                                    onDelete()
                                },
                                leadingIcon = {
                                    Icon(Icons.Rounded.DeleteOutline, contentDescription = null, tint = Color(0xFFD32F2F))
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // نمایش فیلدها متناسب با نوع مدرک (با ارقام انگلیسی و دکمه کپی اختصاصی برای هر فیلد)
            when (item.type) {
                // ۳ کد پستی مختلف: منزل، محل کار، سایر/دلخواه
                ItemType.POSTAL_CODE -> {
                    // کد پستی اول (منزل)
                    if (item.primaryValue.isNotBlank()) {
                        CopyableField(
                            label = "کد پستی اول (منزل)",
                            rawTextToCopy = item.primaryValue,
                            displayedText = NumberUtils.formatPostalCode(item.primaryValue),
                            isLarge = true
                        )
                    }

                    // کد پستی دوم (محل کار)
                    if (item.secondaryValue1.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CopyableField(
                            label = "کد پستی دوم (محل کار)",
                            rawTextToCopy = item.secondaryValue1,
                            displayedText = NumberUtils.formatPostalCode(item.secondaryValue1),
                            isLarge = true
                        )
                    }

                    // کد پستی سوم (سایر یا دلخواه)
                    if (item.secondaryValue2.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CopyableField(
                            label = "کد پستی سوم (سایر / دلخواه)",
                            rawTextToCopy = item.secondaryValue2,
                            displayedText = NumberUtils.formatPostalCode(item.secondaryValue2),
                            isLarge = true
                        )
                    }
                }

                // کارت بانکی
                ItemType.BANK_CARD -> {
                    val bankName = NumberUtils.detectBankName(item.primaryValue)
                    CopyableField(
                        label = "شماره ۱۶ رقمی کارت ($bankName)",
                        rawTextToCopy = item.primaryValue,
                        displayedText = NumberUtils.formatBankCard(item.primaryValue),
                        isLarge = true
                    )

                    // کد اعتبارسنجی CVV2 و تاریخ انقضا در دو ستون مجزا با دکمه کپی اختصاصی
                    if (item.secondaryValue1.isNotEmpty() || item.secondaryValue2.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (item.secondaryValue1.isNotEmpty()) {
                                Box(modifier = Modifier.weight(1f)) {
                                    CopyableField(
                                        label = "کد امنیتی (CVV2)",
                                        rawTextToCopy = item.secondaryValue1,
                                        displayedText = item.secondaryValue1
                                    )
                                }
                            }
                            if (item.secondaryValue2.isNotEmpty()) {
                                Box(modifier = Modifier.weight(1f)) {
                                    CopyableField(
                                        label = "تاریخ انقضا",
                                        rawTextToCopy = item.secondaryValue2,
                                        displayedText = item.secondaryValue2
                                    )
                                }
                            }
                        }
                    }

                    // شماره شبا
                    if (item.secondaryValue3.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        val formattedShaba = if (item.secondaryValue3.startsWith("IR", ignoreCase = true)) {
                            item.secondaryValue3.uppercase()
                        } else {
                            "IR${item.secondaryValue3}"
                        }
                        CopyableField(
                            label = "شماره شبا (با IR)",
                            rawTextToCopy = formattedShaba,
                            displayedText = formattedShaba
                        )
                    }
                }

                // کارت ملی هوشمند
                ItemType.NATIONAL_CODE -> {
                    CopyableField(
                        label = "کد ملی ۱۰ رقمی",
                        rawTextToCopy = item.primaryValue,
                        displayedText = NumberUtils.formatNationalCode(item.primaryValue),
                        isLarge = true
                    )
                    if (item.secondaryValue1.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CopyableField(
                            label = "سریال پشت کارت هوشمند ملی",
                            rawTextToCopy = item.secondaryValue1,
                            displayedText = item.secondaryValue1
                        )
                    }
                }

                // شناسنامه
                ItemType.BIRTH_CERTIFICATE -> {
                    CopyableField(
                        label = "شماره عددی شناسنامه",
                        rawTextToCopy = item.primaryValue,
                        displayedText = item.primaryValue,
                        isLarge = true
                    )
                    if (item.secondaryValue1.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CopyableField(
                            label = "سری و حرف شناسنامه",
                            rawTextToCopy = item.secondaryValue1,
                            displayedText = item.secondaryValue1
                        )
                    }
                }

                // سریال پشت کارت ملی مجزا
                ItemType.NATIONAL_CARD_BACK_SERIAL -> {
                    CopyableField(
                        label = "سریال پشت کارت ملی",
                        rawTextToCopy = item.primaryValue,
                        displayedText = item.primaryValue,
                        isLarge = true
                    )
                }

                // اطلاعات دلخواه
                ItemType.CUSTOM -> {
                    CopyableField(
                        label = "شناسه / مقدار عددی یا متنی",
                        rawTextToCopy = item.primaryValue,
                        displayedText = item.primaryValue,
                        isLarge = true
                    )
                }
            }

            // یادداشت دلخواه در صورت وجود
            if (item.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "یادداشت: " + item.notes,
                    fontSize = 12.sp,
                    color = PastelTextSubtle,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFAFCFA))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}
