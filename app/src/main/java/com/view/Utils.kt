package com.view

import android.util.TypedValue

/**
 * @Author: liyuan
 * @Date: 2/23/21 10:11 PM
 * @Description:
 */

val Float.dp
    get() = TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        this,
        android.content.res.Resources.getSystem().displayMetrics
    )
val Int.dp
    get() = this.toFloat().dp