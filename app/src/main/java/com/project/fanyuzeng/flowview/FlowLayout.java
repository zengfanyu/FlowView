package com.project.fanyuzeng.flowview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: fanyuzeng on 2017/11/22 10:19
 * @desc:
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        Log.d(TAG,">> onMeasure >> " + "widthMode:"+widthMode+",heightMode:"+heightMode);

        //适配 wrap_content 情况

        //wrap_content 情况下FlowLayout宽度
        int width = 0;
        //wrap_content 情况FlowLayout高度
        int height = 0;

        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        Log.d(TAG, ">> onMeasure >> " + "childCount:" + childCount);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //测量子View 的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //子View的 LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //子View占据的宽高 等于 其本身的宽高加上左右 Margin 值
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //当前行的宽度在加上一个子View的宽度，比父View的宽度要大，那么就需要换行了
            if (lineWidth + childWidth > widthSize) {
                //宽度为当前最宽的那一行的宽度
                width = Math.max(width, lineWidth);
                //重置lineWidth为childWidth,因为此时换行了childWidth为第一个子View,那么此时行宽就是第一个子View的宽
                lineWidth = childWidth;
                //加上上一行的宽度,那么在最后一行时,就要注意边界值了,因为只加了 n-1 行的高度,没有加 n 行的高度
                height += lineHeight;
                //重置lineHeight为childHeight,因为此时换行了childHeight为第一个子View,那么此时行高就是第一个子View的高
                lineHeight = childHeight;
            } else {//不换行
                //叠加行宽
                lineWidth += childWidth;
                //更新当前行的最高的高度.最高的高度为当前行的最高的子View的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //到达最后一个控件
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
            Log.d(TAG, ">> onMeasure >> " + "height:" + height);

        }


//        Log.d(TAG, ">> onMeasure >> " + "widthSize:" + widthSize + ",heightSize:" + heightSize);
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);

    }

    /**
     * 一行一行的存储所有的控件
     */
    private List<List<View>> mChildViewList = new ArrayList<>();

    /**
     * 每一行高度
     */
    private List<Integer> mLineHeighValueList = new ArrayList<>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildViewList.clear();
        mLineHeighValueList.clear();
        //拿到当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childViewWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childViewHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //当前行宽加上 下一个子View的宽度 大于父容器的宽度时 换行
            if (lineWidth + childViewWidth > width) {
                //记录上一行的高度值
                mLineHeighValueList.add(lineHeight);
                //记录上一行的所有View
                mChildViewList.add(lineViews);

                lineWidth = 0;
                lineHeight = childViewHeight;
                //重置集合
                lineViews = new ArrayList<>();

            }
            //不换行
            lineWidth += childViewWidth;
            lineHeight = Math.max(lineHeight, childViewHeight);
            lineViews.add(childView);
        }
        mLineHeighValueList.add(lineHeight);
        mChildViewList.add(lineViews);


        //设置子View的位置
        int left = 0;
        int top = 0;

        int lineNum = mChildViewList.size();

        for (int i = 0; i < lineNum; i++) {
            //当前行的所有View
            lineViews = mChildViewList.get(i);
            lineHeight = mLineHeighValueList.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);

                //判断当前子View的状态
                if (view.getVisibility() == GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + view.getMeasuredWidth();
                int bc = tc + view.getMeasuredHeight();

                //对子View进行布局
                view.layout(lc, tc, rc, bc);

                left += view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            }//这个 for 循环是用于布局某一行的所有view

            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    /**
     * 与当前 ViewGroup 对应的 LayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
