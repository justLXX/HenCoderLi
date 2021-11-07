package com.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import androidx.core.view.updateLayoutParams

/**
 * @Author: liyuan
 * @Date: 2021/5/22 11:51 下午
 * @Description: 验证 View 的几种滑动方式
 */
class DragView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attributes, defStyleAttr) {
    private val TAG = "DragView"
    var lastX = 0f
    var lastY = 0f


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val x = event.x
        val y = event.y

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                layoutOnMove(event)
//                offsetOnMove(event)
//                layoutParamOnMove(event)
//                scrollByParamOnMove(event)
//                scrollToParamOnMove(event)
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
//                scrollerToStart(event)
            }
        }

        return true
    }


    //使用 layout 的方式滑动 View
    private fun layoutOnMove(event: MotionEvent) {
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        Log.d(TAG, "offsetX = $offsetX   offsetY = $offsetY")
        layout(
            (left + offsetX).toInt(), (top + offsetY).toInt(),
            (right + offsetX).toInt(), (bottom + offsetY).toInt()
        )
    }

    //使用 offsetLeftAndRight()的方式滑动 view
    private fun offsetOnMove(event: MotionEvent) {
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        offsetLeftAndRight(offsetX.toInt())
        offsetTopAndBottom(offsetY.toInt())
    }


    //使用 LayoutParam的方式滑动 view
    private fun layoutParamOnMove(event: MotionEvent) {
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = (left + offsetX).toInt()
            topMargin = (top + offsetY).toInt()
        }
    }


    //使用 Scroll的方式滑动 view
    private fun scrollByParamOnMove(event: MotionEvent) {
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        (parent as View).scrollBy((-offsetX).toInt(), (-offsetY).toInt())
    }

    //使用 ScrollTo的方式滑动 view
    private fun scrollToParamOnMove(event: MotionEvent) {
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        (parent as View).scrollTo(-event.x.toInt(), -event.y.toInt())
    }

    private val scroller: Scroller = Scroller(context)

    //使用 scroller 滚动回左边
    private fun scrollerToStart(event: MotionEvent) {
        val container = parent as View
        scroller.forceFinished(true)
        scroller.startScroll(
            container.scrollX,
            container.scrollY,
            -container.scrollX,
            -container.scrollY
        )
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            (parent as View).scrollTo(scroller.currX, scroller.currY)
            invalidate()
        }else{
            val container = parent as View
            Log.e(TAG + "view", "scrollX = $scrollX  scrollY = $scrollY  ")
            Log.e(TAG + "parent", "scrollX = ${container.scrollX}  scrollY = ${container.scrollY}  ")
            Log.e(TAG + "scroller", "scrollX = ${scroller.currX}  scrollY = ${scroller.currY}  ")
        }
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = Color.BLACK
        this.strokeCap = Paint.Cap.ROUND
        this.strokeWidth = 5.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paint)

        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paint)

    }


}