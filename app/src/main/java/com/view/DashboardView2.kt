package com.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

val DASH_STROKE_WIDTH = 3f.dp

class DashboardView2 @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = DASH_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }

    val startAngle = OPEN_ANGLE / 2 + 90

    val sweepAngle = 360 - OPEN_ANGLE

    val path = Path()

    val dash = Path().apply {
        addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }
    lateinit var pathEffect: PathDashPathEffect

    lateinit var arcRectF: RectF

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        arcRectF = RectF(width / 2f - RADIUS, height / 2f - RADIUS, width / 2 + RADIUS, height / 2f + RADIUS)
        path.addArc(arcRectF, startAngle, sweepAngle)
        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(dash,
                (pathMeasure.length - DASH_WIDTH) / MARK_COUNT,
                0f,
                PathDashPathEffect.Style.MORPH)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画分割线
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        // 画弧
        paint.pathEffect = null
        canvas.drawPath(path, paint)

        canvas.drawLine(width / 2f,
                height / 2f,
                width / 2f+ LENGTH* cos(markToRadius()),
                height / 2f+ LENGTH* sin(markToRadius()),paint)
    }

    private fun toRadius(angle: Float) = Math.toRadians(angle.toDouble()).toFloat()

    private fun markToRadius() = toRadius((startAngle + sweepAngle / MARK_COUNT * MARK))
}