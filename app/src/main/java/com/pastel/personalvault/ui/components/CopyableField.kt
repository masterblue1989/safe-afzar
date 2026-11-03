package com.pastel.personalvault.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pastel.personalvault.ui.theme.CopyPillBg
import com.pastel.personalvault.ui.theme.CopyPillBorder
import com.pastel.personalvault.ui.theme.CopyPillText
import com.pastel.personalvault.ui.theme.PastelForestText
import com.pastel.personalvault.ui.theme.PastelTextSubtle
import com.pastel.personalvault.utils.ClipboardHelper
import kotlinx.coroutines.delay

/**
 * کامپوننت فیلد اطلاعاتی با دکمه کپی سریع و نمایش اعداد به صورت ارقام انگلیسی و LTR
 */
@Composable
fun CopyableField(
    label: String,
    rawTextToCopy: String,
    displayedText: String = rawTextToCopy,
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    extraNote: String? = null
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(1800)
            isCopied = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF7FBF9))
            .border(1.dp, Color(0xFFE4F2EA), RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 9.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // برچسب فارسی فیلد
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = PastelTextSubtle
            )

            // دکمه کپی سریع با تم پاستلی
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isCopied) Color(0xFFD1F2DD) else CopyPillBg)
                    .border(
                        1.dp,
                        if (isCopied) Color(0xFF8FD5A6) else CopyPillBorder,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        ClipboardHelper.copyToClipboard(context, label, rawTextToCopy)
                        isCopied = true
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AnimatedContent(
                        targetState = isCopied,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "انیمیشن_کپی"
                    ) { copied ->
                        if (copied) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "کپی شد",
                                tint = Color(0xFF156E42),
                                modifier = Modifier.size(14.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.ContentCopy,
                                contentDescription = "کپی",
                                tint = CopyPillText,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = if (isCopied) "کپی شد!" else "کپی سریع",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isCopied) Color(0xFF156E42) else CopyPillText
                    )
                }
            }
        }

        // مقدار عددی ذخیره شده با ارقام استاندارد انگلیسی به صورت چپ به راست
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Text(
                text = displayedText,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = if (isLarge) 17.sp else 14.sp,
                color = PastelForestText,
                letterSpacing = if (isLarge) 1.5.sp else 0.8.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        if (!extraNote.isNullOrBlank()) {
            Text(
                text = extraNote,
                fontSize = 11.sp,
                color = PastelTextSubtle,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
