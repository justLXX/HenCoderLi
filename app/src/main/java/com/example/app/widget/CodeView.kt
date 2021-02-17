package com.example.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.example.app.R
import com.example.core.utils.Utils.dp2px
import java.util.*

class CodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatTextView(
        context!!, attrs
    ) {
    private val paint = Paint()
    private val codeList = arrayOf(
        "kotlin",
        "android",
        "java",
        "http",
        "https",
        "okhttp",
        "retrofit",
        "tcp/ip"
    )

    fun updateCode() {
        val random = Random().nextInt(codeList.size)
        val code = codeList[random]
        text = code
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        super.onDraw(canvas)
    }

    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        gravity = Gravity.CENTER
        setBackgroundColor(getColor(context,R.color.colorPrimary))
        setTextColor(Color.WHITE)
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.color = getColor(context,R.color.colorAccent)
        paint.strokeWidth = 6f.dp2px()
        updateCode()
    }
}