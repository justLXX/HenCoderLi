package com.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.RelativeLayout
import kotlin.math.abs
import kotlin.math.log

open class DragViewLayout : RelativeLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.GRAY)
    }


    var touchX: Float = 0f
    var touchY: Float = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchX = x
                touchY = y
            }

            MotionEvent.ACTION_MOVE -> {
                val offsetX = x - touchX
                val offsetY = y - touchY
                log("offsetX = $offsetX  offsetY = $offsetY")
                if (abs(offsetX) > ViewConfiguration.getTouchSlop() || abs(offsetY) > ViewConfiguration.getTouchSlop()) {
                    scrollBy(-offsetX.toInt(), -offsetY.toInt())
                    touchX = x
                    touchY = y
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                touchY = 0f
                touchX = 0f
            }
        }
        return true
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = Color.WHITE
        this.strokeCap = Paint.Cap.SQUARE
        this.strokeWidth = 10.dp
    }


    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)

        canvas.drawLine(-10000f, height / 2f, 10000f, height / 2f, paint)

        canvas.drawLine(width / 2f, -10000f, width / 2f, 10000f, paint)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }

}