package com.example.tuananhe.phaibay.util

import android.content.res.Resources
import android.util.TypedValue

object BubbleUtil {

    fun convertDipToPixel(resource: Resources, dip: Float): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip ,
            resource.displayMetrics
        ).toInt()
}
