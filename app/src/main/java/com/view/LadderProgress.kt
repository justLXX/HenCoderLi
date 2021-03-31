package com.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.app.R

/**
 * @Author: liyuan
 * @Date: 2018/12/27 10:17
 * @Description:
 */

class LadderProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private lateinit var pgPaint: Paint

    private lateinit var processPaint: Paint

    private var mTextPaint = Paint()

    private lateinit var pgRect: RectF

    private lateinit var textRect: Rect

    /** 圆角  */
    private var radius: Float = 0f

    var mProgress: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var mMaxProgress: Int = 10
        set(value) {
            field = value
            invalidate()
        }

    var mTextSize = sp2Px(11f)

    var mFontPath: String? = null

    //初始时文字颜色
    var mTextColor = Color.parseColor("#FF0082FF")

    var mProgressBgColor = Color.WHITE

    var minWidth = 1

    private val processText: String
        get() = mProgress.toString() + "/" + mMaxProgress


    init {
        attrs?.let {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.LadderProgress)
            mTextColor = ta.getColor(R.styleable.LadderProgress_mTextColor, Color.parseColor("#FF0082FF"))
            mProgressBgColor = ta.getColor(R.styleable.LadderProgress_mProgressBgColor, Color.WHITE)
            mTextSize = ta.getDimensionPixelSize(R.styleable.LadderProgress_mTextSize, 11).toFloat()
            mProgress = ta.getInteger(R.styleable.LadderProgress_currentProgress, 0)
            mMaxProgress = ta.getInteger(R.styleable.LadderProgress_maxProgress, 10)
            mFontPath = ta.getString(R.styleable.LadderProgress_fontPath)
            ta.recycle() //
        }
        initPaint()
    }

    private fun initPaint() {
        pgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        processPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        processPaint.color = mTextColor
        processPaint.style = Paint.Style.FILL
        mTextPaint.isAntiAlias = true
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize
        if (mFontPath?.isNotEmpty() == true) {
            try {
                mTextPaint.typeface = Typeface.createFromAsset(context.assets, mFontPath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun initRect() {
        radius = (measuredHeight / 2).toFloat()
        minWidth = Math.max(measuredWidth / mMaxProgress, radius.toInt() * 2)
        textRect = Rect()
        pgRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initRect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制背景
        drawBackground(canvas)

        //绘制进度
        drawProcess(canvas)
        //绘制文本
        drawText(canvas)
        //绘制进度文本
        drawProcessText(canvas)
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        canvas?.let {

        }
    }

    private fun drawBackground(canvas: Canvas) {
        pgPaint.color = mProgressBgColor
        canvas.drawRoundRect(pgRect, radius, radius, pgPaint)
    }

    private fun drawProcess(canvas: Canvas) {
        processPaint.color = mTextColor
        canvas.save()
        var right = 0f
        if (mProgress > 0) {
            right = Math.max(measuredWidth * mProgress / mMaxProgress, minWidth).toFloat()
        }
//        canvas.clipRect(pgRect.left, pgRect.top, right.toFloat(), pgRect.bottom)

        canvas.drawRoundRect(RectF(pgRect.left, pgRect.top, right, pgRect.bottom), radius, radius, processPaint)
        canvas.restore()

    }

    private fun drawText(canvas: Canvas) {
        mTextPaint.color = mTextColor
        val text = processText
        mTextPaint.getTextBounds(text, 0, text.length, textRect)
        val tx = ((measuredWidth - textRect.width()) / 2).toFloat()
        val ty = ((measuredHeight + textRect.height()) / 2).toFloat()
        canvas.drawText(text, tx, ty, mTextPaint)
    }

    private fun drawProcessText(canvas: Canvas) {
        mTextPaint.color = mProgressBgColor
        val text = processText
        canvas.save()
        val tx = ((measuredWidth - textRect.width()) / 2).toFloat()
        val ty = ((measuredHeight + textRect.height()) / 2).toFloat()
        val right = (measuredWidth * mProgress / mMaxProgress)
        if (tx <= right) {
            canvas.clipRect(textRect.left, textRect.top, right, textRect.right)
            canvas.drawText(text, tx, ty, mTextPaint)
        }
        canvas.restore()
    }


    private fun sp2Px(size: Float): Float {
        val c = context
        val r: Resources

        if (c == null) {
            r = Resources.getSystem()
        } else {
            r = c.resources
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, r.displayMetrics)
    }

    /**
     * @param size sp
     */
    fun setTextSize(size: Float) {
        setRawTextSize((sp2Px(size)))
    }


    private fun setRawTextSize(size: Float) {
        if (size != mTextPaint.textSize) {
            mTextPaint.textSize = size
            invalidate()
        }
    }
}
