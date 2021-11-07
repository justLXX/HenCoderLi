package com.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils

/**
 * 基本图形绘制
 */
class SimpleGraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = SizeUtils.dp2px(16f).toFloat()
    }

    private val path = Path()

    var left = 0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        left = width / 2f
        path.reset()
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addRect(
            width / 2f - RADIUS,
            height / 2f,
            width / 2f + RADIUS,
            height / 2f + 2 * RADIUS,
            Path.Direction.CCW
        )
        path.fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        repeat(10) {
            val top = 100f * it
            val bottom = top + 100
            draw(canvas, top, bottom, it)
        }
    }

    private fun draw(canvas: Canvas, top: Float, bottom: Float, time: Int) {
        when (time) {
            0 -> drawPoint(canvas, top, bottom)
            1 -> drawOval(canvas, top, bottom)
            2 -> drawLine(canvas, top, bottom)
            3 -> drawRoundRect(canvas, top, bottom)
            4 -> drawArc(canvas, top, bottom)
        }
    }

    private fun drawArc(canvas: Canvas, top: Float, bottom: Float) {
        canvas.drawArc(left, top + 10, left + 100f, bottom - 10, 0f, 120f, true, paint)
        paint.color = Color.RED
        canvas.drawArc(left, top + 10, left + 100f, bottom - 10, 0f, -120f, true, paint)
        drawSign("drawArc", canvas, top, bottom)
    }


    private fun drawPathCircle(canvas: Canvas) {
        paint.color = Color.MAGENTA
        canvas.drawPath(path, paint)
    }

    private fun drawRectangle(canvas: Canvas) {

    }


    private fun drawCircle(canvas: Canvas) {
        paint.setColor(Color.RED)
        paint.strokeWidth = 10f
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)
    }

    private fun drawPoint(canvas: Canvas, top: Float, bottom: Float) {
        val points = floatArrayOf(
            0f, 0f,
            50f, 0f,
            100f, 25f,
            150f, 25f,
            200f, 50f
        )
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(30f, 30f, paint)
        paint.setColor(Color.RED)
        canvas.drawPoints(points, paint)
        drawDivider(canvas, bottom)
    }

    private fun drawOval(canvas: Canvas, top: Float, bottom: Float) {
        paint.color = Color.BLACK
        canvas.drawText("我是椭圆 oval", 0f, (top + bottom) / 2, paint)
        canvas.drawOval(left, top, left + 200, bottom, paint)
        drawDivider(canvas, bottom)
    }

    private fun drawLine(canvas: Canvas, top: Float, bottom: Float) {
        paint.color = Color.RED
        drawText("我是横向 drawLine", canvas, top, bottom)
        canvas.drawLine(0f, bottom - 10, width.toFloat(), bottom - 5, paint)
        drawDivider(canvas, bottom)
    }

    private fun drawRoundRect(canvas: Canvas, top: Float, bottom: Float) {
        paint.color = Color.BLUE
        canvas.drawRoundRect(left, top, left + 200f, bottom, 50f, 50f, paint)
        drawDivider(canvas, bottom)
    }

    private fun drawSign(str: String, canvas: Canvas, top: Float, bottom: Float) {
        drawText(str, canvas, top, bottom)
        drawDivider(canvas, bottom)
    }

    private fun drawDivider(canvas: Canvas, bottom: Float) {
        paint.color = Color.DKGRAY
        paint.strokeWidth = SizeUtils.dp2px(2f).toFloat()
        canvas.drawLine(0f, bottom, width.toFloat(), bottom, paint)
    }

    private fun drawText(str: String, canvas: Canvas, top: Float, bottom: Float) {
        paint.textSize = SizeUtils.sp2px(16f).toFloat()
        canvas.drawText(str, 0f, (top + bottom) / 2, paint)
    }

}