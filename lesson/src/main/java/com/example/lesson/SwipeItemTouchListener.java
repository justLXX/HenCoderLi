package com.example.lesson;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Intercept touch event of RecyclerView binded
 */
public class SwipeItemTouchListener implements RecyclerView.OnItemTouchListener {

    private float mDistanceX;
    private float mDistanceY;
    float x = 0, y = 0;

    Rect xy = new Rect();
    public SwipeItemTouchListener() {
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if ((recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) ||
                !isAttachedToWindow(recyclerView) || !hasAdapter(recyclerView)) {
            return false;
        }
        boolean hasScrollable = false;

        View view = findScrollableChildViewUnder(recyclerView, motionEvent);
        if (view != null){
            view.getGlobalVisibleRect(xy);
            hasScrollable = (motionEvent.getRawX() > xy.left && motionEvent.getRawX() < xy.right) && (motionEvent.getRawY() > xy.top && motionEvent.getRawY() < xy.bottom);
            Log.i("SwipeItemTouchListener", "motionEvent.getRawX() : " + motionEvent.getRawX() + "xy.left : " + xy.left + " xy.right : " + xy.right
                     + " motionEvent.getRawY() : " + motionEvent.getRawY() + " xy.top : " + xy.top + " xy.bottom : " + xy.bottom);
        }
        recyclerView.requestDisallowInterceptTouchEvent(hasScrollable);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        Log.i("SwipeItemTouchListener", "onTouchEvent MotionEvent" + motionEvent.getAction());
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    private View findScrollableChildViewUnder(RecyclerView recyclerView, MotionEvent event) {
        if (recyclerView == null) {
            return null;
        }
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        View child = recyclerView.findChildViewUnder(x, y);

        return findCanScrollView(child);
    }

    private View findCanScrollView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup target = (ViewGroup) v;
            if (target instanceof RecyclerView
                    && target.getVisibility() == View.VISIBLE) {
                return target;
            } else {
                for (int i = 0; i < target.getChildCount(); ++i) {
                    View view = findCanScrollView(target.getChildAt(i));
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            }
        } else {
            return null;
        }
    }

    private static boolean isAttachedToWindow(RecyclerView hostView) {
        if (Build.VERSION.SDK_INT >= 19) {
            return hostView.isAttachedToWindow();
        } else {
            return (hostView.getHandler() != null);
        }
    }

    private static boolean hasAdapter(RecyclerView hostView) {
        return (hostView.getAdapter() != null);
    }

}