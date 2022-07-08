package com.devsisters.SolitaireDeckedOu.helpers

import android.content.ContentResolver
import android.provider.Settings
import java.io.File

fun needGame(resolver: ContentResolver): Boolean {
    fun condition1(): Boolean {
        val list = listOf(
            "/sbin/", "/system/bin/", "/system/xbin/",
            "/data/local/xbin/", "/data/local/bin/",
            "/system/sd/xbin/", "/system/bin/failsafe/",
            "/data/local/"
        )
        try {
            for (elem in list) {
                if (File("${elem}su").exists()) return false
            }
        } catch (e: Exception) {
        }
        return true
    }

    fun condition2(): Boolean {
        return Settings.Global.getString(resolver, Settings.Global.ADB_ENABLED) != "1"
    }

    return !(condition1() && condition2())
}