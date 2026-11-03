package com.pastel.personalvault.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast

object ClipboardHelper {

    /**
     * Copies [text] to clipboard, triggers a subtle haptic feedback,
     * and shows a toast notification in Persian.
     */
    fun copyToClipboard(context: Context, label: String, text: String, showToast: Boolean = true) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        if (clipboard != null) {
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)

            // Perform subtle haptic feedback
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                    vibratorManager?.defaultVibrator?.vibrate(
                        VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(35)
                }
            } catch (_: Exception) {
                // Ignore vibration failure if permission or hardware is unavailable
            }

            // In Android 13+ (API 33), the system shows its own clipboard notification overlay.
            // For older versions or clarity, we show a clean Persian toast.
            if (showToast && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                Toast.makeText(context, "$label کپی شد ✨", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
