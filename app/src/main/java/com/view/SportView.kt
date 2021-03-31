package com.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import com.example.app.R
import java.util.jar.Attributes

/**
 * @Author: liyuan
 * @Date: 2/23/21 10:17 PM
 * @Description:
 */
private val RADIUS = 150.dp
private val RING_WIDTH = 20.dp
private val CIRCLE_COLOR = "#90a4a3".toColorInt()
private val HEIGHT_LIGHT_COLOR = "#ff4081".toColorInt()

class SportView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attributes, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        textAlign = Paint.Align.CENTER
//        typeface = ResourcesCompat.getFont(context, R.font.onion_math_bold)
    }

    private val bounds = Rect()

    private var fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制环
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = RING_WIDTH
        paint.color = CIRCLE_COLOR
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)

        // 绘制进度条

        paint.style = Paint.Style.STROKE
        paint.color = HEIGHT_LIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = RING_WIDTH
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            width / 2f + RADIUS,
            height / 2f + RADIUS,
            90f, 180f,
            false,
            paint
        )
        val content = "abap"
        paint.getTextBounds(content, 0, content.length, bounds)
        paint.getFontMetrics(fontMetrics)
        //绘制文字
        paint.style = Paint.Style.FILL
        canvas.drawText(
            "abap", width / 2f, height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2, paint
        )

    }


}