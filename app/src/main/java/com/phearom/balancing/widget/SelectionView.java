package com.phearom.balancing.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phearom on 6/25/16.
 */
public class SelectionView<T> extends LinearLayout {
    private final static int ITEM_DATA = -111;
    private Map<View, T> item;
    private OnItemSelectedListener listener;

    private Drawable selectedRes;
    private Drawable unSelectedRes;

    private LayoutParams itemLayoutParam;

    public SelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        item = new HashMap<>();
        itemLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setMargin(2, 2, 2, 2);
    }

    public SelectionView(Context context) {
        super(context);
        item = new HashMap<>();
        itemLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setMargin(2, 2, 2, 2);
    }

    public void setMargin(int l, int t, int r, int b) {
        itemLayoutParam.setMargins(l, t, r, b);
    }

    public void setSelectedResId(Integer resId) {
        this.selectedRes = ContextCompat.getDrawable(getContext(), resId);
    }

    public void setUnSelectedResId(Integer resId) {
        this.unSelectedRes = ContextCompat.getDrawable(getContext(), resId);
    }

    public void setSelectedColor(Integer color) {
        this.selectedRes = new ColorDrawable(color);
    }

    public void setUnSelectedColor(Integer color) {
        this.unSelectedRes = new ColorDrawable(color);
    }

    public void setSelectedColor(String color) {
        try {
            this.selectedRes = new ColorDrawable(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnSelectedColor(String color) {
        try {
            this.unSelectedRes = new ColorDrawable(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind(View v, T object) {
        item.put(v, object);
        v.setOnClickListener(new ItemSelected());
        v.setTag(ITEM_DATA, item.get(v));
        v.setLayoutParams(itemLayoutParam);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(unSelectedRes);
        }
        addView(v);
        invalidate();
    }

    private void updateUI(View current) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            for (View v : item.keySet()) {
                v.setBackground(unSelectedRes);
            }
            current.setBackground(selectedRes);
        }
        invalidate();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnItemSelectedListener<T> {
        void onSelected(T object);
    }

    private class ItemSelected implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (null != listener) {
                updateUI(v);
                T item = (T) v.getTag(ITEM_DATA);
                listener.onSelected(item);
            }
        }
    }
}
