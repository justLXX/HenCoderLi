package com.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText

/**
 * @Author: liyuan
 * @Date: 2/25/21 9:46 PM
 * @Description:
 */
private val TEXT_SIZE = 12.dp
private val TEXT_MARGIN = 8.dp
private val HORIZONTAL_OFFSET = 5.dp
private val VERTICAL_OFFSET = 23.dp

class MaterialEditText(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatEditText(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.textSize = TEXT_SIZE
        setPadding(
            paddingLeft,
            (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
            paddingRight,
            paddingBottom
        )
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (!text.isNullOrBlank()) {

        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!text.isNullOrBlank()) {
            //绘制提示文字
            canvas.drawText(hint.toString(), HORIZONTAL_OFFSET, VERTICAL_OFFSET, paint)
        }

    }

}