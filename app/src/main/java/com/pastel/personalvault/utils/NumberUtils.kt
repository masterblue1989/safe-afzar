package com.pastel.personalvault.utils

/**
 * NumberUtils provides functions to strictly convert Persian and Arabic numerals
 * to standard English ASCII digits (0-9) and format various Iranian documents.
 */
object NumberUtils {

    private val PERSIAN_DIGITS = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
    private val ARABIC_DIGITS  = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')

    /**
     * Converts any Persian (۰-۹) or Arabic (٠-٩) digits inside [input] to pure English ASCII (0-9).
     * All non-digit characters are preserved as is.
     */
    fun toEnglishDigits(input: String?): String {
        if (input.isNullOrEmpty()) return ""
        val sb = java.lang.StringBuilder(input.length)
        for (ch in input) {
            when {
                ch in '۰'..'۹' -> sb.append((ch - '۰' + '0'.code).toChar())
                ch in '٠'..'٩' -> sb.append((ch - '٠' + '0'.code).toChar())
                else -> sb.append(ch)
            }
        }
        return sb.toString()
    }

    /**
     * Extracts ONLY digits from the input and ensures they are normalized to English.
     */
    fun extractOnlyEnglishDigits(input: String?): String {
        return toEnglishDigits(input).filter { it.isDigit() }
    }

    /**
     * Formats a 16-digit bank card into 4-digit chunks: 6037 - 9918 - xxxx - xxxx
     */
    fun formatBankCard(cardNumber: String?): String {
        val clean = extractOnlyEnglishDigits(cardNumber)
        if (clean.length <= 4) return clean
        return clean.chunked(4).joinToString("  -  ")
    }

    /**
     * Formats a 10-digit postal code into 5-5 chunks: 12345 - 67890
     */
    fun formatPostalCode(postalCode: String?): String {
        val clean = extractOnlyEnglishDigits(postalCode)
        if (clean.length == 10) {
            return "${clean.substring(0, 5)} - ${clean.substring(5)}"
        }
        return clean
    }

    /**
     * Formats Iranian National Code: 001 - 234567 - 8
     */
    fun formatNationalCode(code: String?): String {
        val clean = extractOnlyEnglishDigits(code)
        if (clean.length == 10) {
            return "${clean.substring(0, 3)}-${clean.substring(3, 9)}-${clean.substring(9)}"
        }
        return clean
    }

    /**
     * Recognizes Iranian bank from the 6-digit BIN prefix.
     */
    fun detectBankName(cardNumber: String?): String {
        val digits = extractOnlyEnglishDigits(cardNumber)
        if (digits.length < 6) return "بانک نامشخص"
        val bin = digits.substring(0, 6)
        return when (bin) {
            "603799" -> "بانک ملی ایران"
            "589210" -> "بانک سپه"
            "621986" -> "بلو بانک / بانک سامان"
            "610433" -> "بانک ملت"
            "585983" -> "بانک تجارت"
            "627353" -> "بانک تجارت (سابق سینا)"
            "603770" -> "بانک کشاورزی"
            "628023" -> "بانک مسکن"
            "627760" -> "پست بانک ایران"
            "502229" -> "بانک پاسارگاد"
            "627412" -> "بانک اقتصاد نوین"
            "622106" -> "بانک پارسیان"
            "502908" -> "بانک توسعه تعاون"
            "504172" -> "بانک رسالت"
            "639346" -> "بانک سینا"
            "639607" -> "بانک سرمایه"
            "636214" -> "بانک آینده"
            "502938" -> "بانک دی"
            "606373" -> "بانک مهر ایران"
            "639599" -> "بانک قوامین"
            "505416" -> "بانک گردشگری"
            "636949" -> "بانک حکمت ایرانیان"
            "627381" -> "بانک انصار"
            "505785" -> "بانک ایران زمین"
            else -> "کارت شتابی"
        }
    }
}
