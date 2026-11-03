# ProGuard rules for PastelVault
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
