package com.example.appli.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils

class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val path = Path()

    /**
     * //forceClosed 是否计算闭合
     *  pathMeasure = PathMeasure(path, false)
     *  pathMeasure.length //path 长度
     *  pathMeasure.getPosTan()// 获取某位置正切值
     */
    lateinit var pathMeasure: PathMeasure

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addCircle(width / 2f, height / 2f, RADIUS * 1.5f, Path.Direction.CCW)
        path.addRect(
            width / 2f - RADIUS,
            height / 2f,
            width / 2f + RADIUS,
            height / 2 + 2 * RADIUS,
            Path.Direction.CCW
        )

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.fillType = Path.FillType.EVEN_ODD
        canvas.drawPath(path, paint)
    }

}