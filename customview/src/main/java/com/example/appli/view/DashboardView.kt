package com.example.appli.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

const val OPEN_ANGLE = 120f
val DASH_WIDTH = 2f.dp
val DASH_HEIGHT = 10f.dp
val RADIUS = 150f.dp
const val MARK = 0
val LENGTH = 120f.dp
const val MARK_COUNT = 20
class DashboardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
    }

    private val dash = Path()

    private val path = Path()

    private lateinit var pathEffect: PathDashPathEffect

    private val startAngle = 90 + OPEN_ANGLE / 2

    private val sweepAngle: Float = 360 - OPEN_ANGLE


    init {
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CCW)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
                width / 2f - RADIUS,
                height / 2 - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                sweepAngle
        )
        val pathMeasure = PathMeasure(path, false)
        pathEffect =
                PathDashPathEffect(
                        dash,
                        (pathMeasure.length - DASH_WIDTH) / 20f,
                        0f,
                        PathDashPathEffect.Style.MORPH
                )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画刻度
        paint.pathEffect = pathEffect
        canvas.drawPath(
                path, paint
        )

        //画弧
        paint.pathEffect = null
        canvas.drawPath(
                path, paint
        )

        //画指针
        canvas.drawLine(
                width / 2f,
                height / 2f,
                (width / 2f + LENGTH * cos(markToRadians(MARK))).toFloat(),
                (height / 2f + LENGTH * sin(markToRadians(MARK))).toFloat(),
                paint
        )

    }

    //角度转弧度
    private fun markToRadians(mark: Int) =
            Math.toRadians((startAngle + sweepAngle / 20 * mark).toDouble())

}