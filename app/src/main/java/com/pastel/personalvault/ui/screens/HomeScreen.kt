package com.pastel.personalvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pastel.personalvault.data.model.CategoryGroup
import com.pastel.personalvault.data.model.PersonalItem
import com.pastel.personalvault.ui.components.ItemDialog
import com.pastel.personalvault.ui.components.PastelCard
import com.pastel.personalvault.ui.theme.PastelBorder
import com.pastel.personalvault.ui.theme.PastelForestText
import com.pastel.personalvault.ui.theme.PastelMint100
import com.pastel.personalvault.ui.theme.PastelMintLightest
import com.pastel.personalvault.ui.theme.PastelMintPrimary
import com.pastel.personalvault.ui.theme.PastelMintSubtle
import com.pastel.personalvault.ui.theme.PastelTextSubtle
import com.pastel.personalvault.ui.viewmodel.VaultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VaultViewModel,
    modifier: Modifier = Modifier
) {
    val items by viewModel.items.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<PersonalItem?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PastelMintLightest,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    itemToEdit = null
                    showDialog = true
                },
                containerColor = PastelMintPrimary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "افزودن اطلاعات جدید",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // نوار بالایی اپلیکیشن با تم سبز پاستلی
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFDFF3E8),
                                Color(0xFFEEF8F2)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        PastelMintSubtle,
                        RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Shield,
                                        contentDescription = null,
                                        tint = PastelMintPrimary,
                                        modifier = Modifier.size(19.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "سیف افزار (safe afzar)",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PastelForestText
                                )
                            }
                            Text(
                                text = "دسترسی فوری و کپی با یک لمس (ارقام استاندارد انگلیسی)",
                                fontSize = 12.sp,
                                color = PastelTextSubtle,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // نشان امنیت و آفلاین بودن
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .border(1.dp, PastelBorder, RoundedCornerShape(16.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = null,
                                    tint = PastelMintPrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "امن و آفلاین",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PastelForestText
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // کادر جستجوی یکپارچه
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        placeholder = {
                            Text(
                                text = "جستجو در کد ملی، ۳ کد پستی، کارت‌ها و عناوین…",
                                fontSize = 13.sp,
                                color = PastelTextSubtle
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null,
                                tint = PastelMintPrimary
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onSearchQueryChanged("") }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = "پاک کردن متن",
                                        tint = PastelTextSubtle
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PastelMintPrimary,
                            unfocusedBorderColor = Color(0xFFD2E6DB)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // چیپ‌های دسته‌بندی فیلترها (کاملاً فارسی)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = listOf(
                    CategoryGroup.ALL,
                    CategoryGroup.BANKING,
                    CategoryGroup.IDENTITY_DOCS,
                    CategoryGroup.CUSTOM
                )

                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) PastelMintPrimary else Color.White)
                            .border(
                                1.dp,
                                if (isSelected) PastelMintPrimary else PastelBorder,
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { viewModel.onCategorySelected(cat) }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cat.faName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else PastelForestText
                        )
                    }
                }
            }

            // فهرست موارد ذخیره‌شده
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(PastelMint100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null,
                                tint = PastelMintPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (searchQuery.isNotEmpty()) "موردی با این عبارت جستجو یافت نشد!" else "هنوز موردی در گاوصندوق ذخیره نشده است.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PastelForestText
                        )

                        Text(
                            text = "جهت ثبت ۳ کد پستی، کارت بانکی یا کد ملی، دکمه + پایین را لمس کنید.",
                            fontSize = 12.sp,
                            color = PastelTextSubtle,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        PastelCard(
                            item = item,
                            onEdit = {
                                itemToEdit = item
                                showDialog = true
                            },
                            onDelete = {
                                viewModel.deleteItem(item)
                            },
                            onToggleFavorite = {
                                viewModel.toggleFavorite(item)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        ItemDialog(
            initialItem = itemToEdit,
            onDismiss = {
                showDialog = false
                itemToEdit = null
            },
            onSave = { savedItem ->
                viewModel.saveItem(savedItem)
                showDialog = false
                itemToEdit = null
            }
        )
    }
}
