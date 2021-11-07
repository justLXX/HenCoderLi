package com.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SqWindowManagerFloatView extends DragViewLayout {


    public SqWindowManagerFloatView(final Context context, final int floatImgId) {
        super(context);
        setClickable(true);
        final ImageView floatView = new ImageView(context);
        floatView.setImageResource(floatImgId);
        floatView.setOnClickListener(v -> Toast.makeText(context, "点击了悬浮球", Toast.LENGTH_SHORT).show());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(floatView, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
