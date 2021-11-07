package com.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import com.example.app.R
import kotlin.math.min

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

fun getAvatar(resources: Resources, wid: Float): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.avtar, options)
    val min = min(options.outWidth, options.outHeight)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = wid.toInt()
    println("resWid = ${options.outWidth}  resHeight = ${options.outHeight}")
    println("inDensity = ${options.inDensity}inTargetDensity = ${options.inTargetDensity}")
    return BitmapFactory.decodeResource(resources, R.drawable.avtar, options)
}

fun Any.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}