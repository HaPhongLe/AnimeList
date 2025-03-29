package com.example.animelist.ui.util

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {
    fun stringForRes(@StringRes resId: Int) = context.getString(resId)
}
