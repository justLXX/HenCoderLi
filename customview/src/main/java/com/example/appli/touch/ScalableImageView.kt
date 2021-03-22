package com.example.appli.touch

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.graphics.withSave
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.appli.view.dp
import com.example.appli.view.getAvatar
import com.example.core.utils.Utils.log
import com.example.core.utils.Utils.logE
import kotlin.math.max
import kotlin.math.min

/**
 * @Author: liYuan
 * @Date: 3/20/21 9:15 PM
 * @Description: 缩放自定义 View （练习 重写onTouch）
 */
val IMAGE_Width = 250.dp

private const val EXTRA_SCALE_FRACTION = 1.5f

class ScalableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener, Runnable {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, IMAGE_Width)

    //初始偏移
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    // 手势滑动时的便宜
    private var offsetX = 0f
    private var offsetY = 0f
    private var big = false
    private var smallScale = 0f
    private var bigScale = 0f

    private var scroller = OverScroller(context)

    private val gestureDetector = GestureDetectorCompat(context, this).apply {
        setIsLongpressEnabled(false)
    }
    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        println("bitMap size --- width = ${bitmap.width}  height = ${bitmap.height}")
        println("View size --- width = ${width}  height = ${height}")

        val bitmapRatio = bitmap.width / bitmap.height.toFloat()
        val viewRatio = width / height.toFloat()
        println("bitmapRatio = $bitmapRatio  viewRatio = $viewRatio")
        if (bitmapRatio > viewRatio) {
            smallScale = (width.toFloat() / bitmap.width.toFloat())
            bigScale = (height.toFloat() / bitmap.height.toFloat()) * EXTRA_SCALE_FRACTION
        } else {
            bigScale = (width.toFloat() / bitmap.width.toFloat()) * EXTRA_SCALE_FRACTION
            smallScale = (height.toFloat() / bitmap.height.toFloat())
        }
        println("smallScale = $smallScale  bigScale = $bigScale")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (big) {
            canvas.translate(offsetX, offsetY)
        }
        canvas.translate(width / 2f, 0f)
        println("smallScale = $smallScale  bigScale = $bigScale")
        val scale = (bigScale - smallScale) * scaleFraction + smallScale
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    //触摸手势
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    // 按压效果
    override fun onShowPress(e: MotionEvent?) {

    }

    /**
     * @param e1 downEvent
     * @param e2 currentEvent
     * @param distanceX 旧位置 - 新位置 X方向差值
     * @param distanceY 旧位置 - 新位置 Y方向差值
     */
    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (big) {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffsets()
            invalidate()
        }
        return false
    }

    private fun fixOffsets() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }


    // 长按
    override fun onLongPress(e: MotionEvent?) {

    }

    /**
     * @param e1 downEvent
     * @param e2 currentEvent
     * @param velocityX 横向速度
     * @param velocityY 纵向速度
     */
//    override fun onFling(
//        e1: MotionEvent,
//        e2: MotionEvent,
//        velocityX: Float,
//        velocityY: Float
//    ): Boolean {
//        if (big) {
//            val minX = (-(bigScale * bitmap.width - width)/ 2)
//            val maxX = (bigScale * bitmap.width - width) / 2
//            val maxY = (bigScale * bitmap.height - height) / 2
//            val minY = (-(bigScale * bitmap.height - height)) / 2
//            scroller.fling(
//                offsetX.toInt(),
//                offsetY.toInt(),
//                velocityX.toInt(),
//                velocityY.toInt(),
//                minX.toInt(),
//                maxX.toInt(),
//                minY.toInt(),
//                maxY.toInt()
//            )
//            postOnAnimation(this)
//        }
//        return false
//    }

    override fun onFling(
        downEvent: MotionEvent?,
        currentEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (big) {
            scroller.fling(
                offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                (-(bitmap.width * bigScale - width) / 2).toInt(),
                ((bitmap.width * bigScale - width) / 2).toInt(),
                (-(bitmap.height * bigScale - height) / 2).toInt(),
                ((bitmap.height * bigScale - height) / 2).toInt()
            )
            ViewCompat.postOnAnimation(this@ScalableImageView, this)
        }
        return false
    }

    private fun refresh() {
        val result = scroller.computeScrollOffset()
        if (result) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            ViewCompat.postOnAnimation(this, this)
        }
    }


    /**
     * 单击效果
     * @return 是否消费事件，不影响具体业务，仅供系统做记录报错用
     * 对流程无影响
     *  [GestureDetectorCompat.setIsLongpressEnabled] 默认为true
     * 不支持长按情况下，就是点击
     * 支持长按情况下，在触发长按之前手离开屏幕才会触发，否则不触发
     *
     * 不支持双击情况下使用
     */
    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    /**
     * 单击
     *
     * 按下等 300ms，确保不是双击才回调
     * 优点：确保区分单，双击
     * 缺点：响应不及时
     * 支持双击功能情况下使用
     */
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

//双击
    /**
     * 双击
     * 两次触发 down 间隔在 40..300ms [ViewConfiguration.getDoubleTapTimeout]
     * 40ms 为防抖作用
     *
     * 仅会相应
     */
    override fun onDoubleTap(e: MotionEvent?): Boolean {
        big = !big
        if (big) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
            offsetX = 0f
            offsetY = 0f
        }
        log("onDoubleTap action = ${e?.action}")
        return false
    }

    /**
     * 双击事件，并且会响应双击之后的其他手势
     */
    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        logE("onDoubleTapEvent action = ${e?.action}")
        return false
    }

    override fun run() {
        refresh()
    }


}