package com.example.core.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.core.BaseApplication.Companion.currentApplication

object Utils {
    private val displayMetrics = Resources.getSystem().displayMetrics

    @JvmStatic
    fun Float.dp2px(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)
    }

    @JvmStatic
    @JvmOverloads
    fun toast(string: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(currentApplication, string, duration).show()
    }

    fun Context.getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    fun View.getColor(@ColorRes id: Int): Int {
        return this.context.getColor(id)
    }


    fun Any.log(msg: String) {
        Log.d(this.javaClass.simpleName, msg)
    }

    fun Any.logE(msg: String) {
        Log.e(this.javaClass.simpleName, msg)
    }
}


fun main() {

}