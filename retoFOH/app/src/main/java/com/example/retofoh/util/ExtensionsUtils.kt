package com.example.retofoh.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun ViewPager2.configurePagerProperties(itemCount: Int) {
    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = itemCount
    (getChildAt(0) as RecyclerView).apply {
        setPadding(5.dpToPx(context), 0, 5.dpToPx(context), 0)
        clipToPadding = false
    }
}

fun ViewPager2.handlePageScrollStateChange(state: Int) {
    if (state == ViewPager2.SCROLL_STATE_IDLE) {
        val itemCount = adapter?.itemCount ?: return
        when (currentItem) {
            0 -> setCurrentItem(itemCount - 2, false)
            itemCount - 1 -> setCurrentItem(1, false)
        }
    }
}

internal fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}