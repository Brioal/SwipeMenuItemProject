package com.brioal.swipemenuitem;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/14.
 */

public class SwipeMenuItemView extends FrameLayout {
    private final String TAG = "SwipeMenuItemView";
    private View mContentView; //主内容View
    private ViewGroup mMenuViewGroup;//菜单ViewGroup
    private ViewDragHelper mViewDragHelper;
    private OnMenuItemClickListener mClickListener;

    public SwipeMenuItemView(Context context) {
        this(context, null);
    }

    public SwipeMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewDragHelper();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        mClickListener = listener;
    }

    private void initViewDragHelper() {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mContentView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left < -mMenuViewGroup.getMeasuredWidth()) {
                    return -mMenuViewGroup.getMeasuredWidth();
                }
                if (left > 0) {
                    return 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return 0;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == mContentView) {
                    mMenuViewGroup.layout(mContentView.getRight(), 0, mContentView.getRight() + mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (mContentView.getLeft() < -mMenuViewGroup.getMeasuredWidth() / 4 + 50) {
                    open();
                } else {
                    close();
                }
            }
        });
    }

    //关闭
    private void close() {
        mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    //打开
    private void open() {
        mViewDragHelper.smoothSlideViewTo(mContentView, -mMenuViewGroup.getMeasuredWidth(), 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mMenuViewGroup = (ViewGroup) getChildAt(1);
        for (int i = 0; i < mMenuViewGroup.getChildCount(); i++) {
            final int finalI = i;
            mMenuViewGroup.getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onClicked(finalI);
                    }
                    close();
                }
            });
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mMenuViewGroup.layout(mContentView.getRight(), t, mContentView.getRight() + mMenuViewGroup.getMeasuredWidth(), b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
