package com.example.appli.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 使用path 自定义View
 */
class PathView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {

    val paint = Paint()
    val path = Path()

    val oval1 = RectF(200f,200f,400f,400f)
    val oval2 = RectF(400f,200f,600f,400f)
    init {
        path.addArc(oval1,145f,225f)
        path.arcTo(oval2,-180f,225f)
        path.lineTo(400f,524f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path,paint)
    }

}