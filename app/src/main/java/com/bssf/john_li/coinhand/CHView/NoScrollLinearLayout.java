package com.bssf.john_li.coinhand.CHView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by John_Li on 4/10/2018.
 */

public class NoScrollLinearLayout extends LinearLayout {
    private ScrollView scrollView;
    public NoScrollLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public NoScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent. ACTION_UP) {
            scrollView .requestDisallowInterceptTouchEvent( false); //手离开地图的时候不拦截scrollview的touch事件，这样手指滑动的时候scrollview就会滑动
        } else {
            scrollView .requestDisallowInterceptTouchEvent( true); //底层view调用此方法后，true表示父层View的touch事件将不会被触发,父View不拦截事件，由子view自己处理
        }
        return false; //返回值表示是否拦截touch事件至子view，true表示拦截touch事件,不将touch传递至子view,而是执行自己的onTouchEvent方法；false表示不拦截,传递至子view，子view将会执行onTouchEvent方法
    }
}
