package com.pastel.personalvault.data.model

/**
 * انواع مدارک و اطلاعات شخصی پشتیبانی‌شده در نرم‌افزار
 * تمامی عنوان‌ها به زبان فارسی روان و استاندارد تعریف شده‌اند
 */
enum class ItemType(
    val titleFa: String,
    val categoryGroup: CategoryGroup
) {
    POSTAL_CODE(
        titleFa = "کدهای پستی سه‌گانه (منزل، محل کار، سایر)",
        categoryGroup = CategoryGroup.IDENTITY_DOCS
    ),
    NATIONAL_CODE(
        titleFa = "کارت ملی هوشمند و سریال پشت کارت",
        categoryGroup = CategoryGroup.IDENTITY_DOCS
    ),
    BANK_CARD(
        titleFa = "کارت بانکی و شماره شبا",
        categoryGroup = CategoryGroup.BANKING
    ),
    BIRTH_CERTIFICATE(
        titleFa = "شناسنامه و سریال",
        categoryGroup = CategoryGroup.IDENTITY_DOCS
    ),
    NATIONAL_CARD_BACK_SERIAL(
        titleFa = "سریال پشت کارت ملی",
        categoryGroup = CategoryGroup.IDENTITY_DOCS
    ),
    CUSTOM(
        titleFa = "اطلاعات دلخواه و سایر اسناد",
        categoryGroup = CategoryGroup.CUSTOM
    )
}

enum class CategoryGroup(val faName: String) {
    ALL("همه موارد"),
    BANKING("کارت‌های بانکی"),
    IDENTITY_DOCS("مدارک هویتی و کدهای پستی"),
    CUSTOM("سایر اطلاعات")
}
