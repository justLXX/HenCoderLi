package com.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(Color.parseColor("#C2185B"), Color.parseColor("#00ACC1"), Color.parseColor("#558B2F"), Color.parseColor("#5D4037"))
private val OFFSET_LENGTH = 20f.dp
const val OFFSET_INDEX = 2

class PieView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    lateinit var rect: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect = RectF(width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS, height / 2f + RADIUS)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        ANGLES.forEachIndexed { index, sweepAngle ->
            if (index == OFFSET_INDEX) {
                canvas.save()
                canvas.translate((OFFSET_LENGTH * cos(angleToRadius((startAngle + sweepAngle / 2)))), (OFFSET_LENGTH * sin(angleToRadius((startAngle + sweepAngle / 2)))))
            }
            paint.setColor(COLORS[index])
            canvas.drawArc(rect, startAngle, sweepAngle, true, paint)
            startAngle += sweepAngle
            if (index == OFFSET_INDEX) {
                canvas.restore()
            }
        }
    }

    private fun angleToRadius(angle: Float): Float {
        return Math.toRadians(angle.toDouble()).toFloat()
    }
}