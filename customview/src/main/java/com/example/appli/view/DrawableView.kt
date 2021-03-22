package com.example.appli.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View

class DrawableView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val drawable = ColorDrawable(Color.RED)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(width/2, height/2, width, bottom)
        drawable.draw(canvas)
    }
}