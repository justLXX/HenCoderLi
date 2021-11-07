package com.example.lesson

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson.t.fruit.Apple

class EventRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        val apple = Apple()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {


        val mode = MeasureSpec.getMode(heightSpec)
        val size = MeasureSpec.getSize(heightSpec)
        var realHeightMode = heightSpec

        Log.w("====>>>","mode = $mode  size = $size")
        if (mode == MeasureSpec.UNSPECIFIED) {
            realHeightMode = MeasureSpec.makeMeasureSpec(1920,MeasureSpec.AT_MOST)
        }

        super.onMeasure(widthSpec, realHeightMode);

        Log.d("====>>>","wid = $measuredWidth  height = $measuredHeight")

//        setMeasuredDimension(measuredWidth,realHeightMode)
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
        }
        return super.onInterceptTouchEvent(e)
    }


}