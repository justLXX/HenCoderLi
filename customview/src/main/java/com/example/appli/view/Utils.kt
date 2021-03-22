package com.example.appli.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.example.appli.R
import kotlin.math.min

val Float.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )
    }

val Int.dp: Float
    get() = this.toFloat().dp


fun getAvatar(resources: Resources,wid: Float): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.avtar, options)
    val min = min(options.outWidth, options.outHeight)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = wid.toInt()
    println("resWid = ${options.outWidth}  resHeight = ${options.outHeight}")
    println("inDensity = ${options.inDensity}inTargetDensity = ${options.inTargetDensity}")
    return BitmapFactory.decodeResource(resources, R.drawable.avtar,options)
}