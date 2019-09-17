package com.magicalrice.cases.appbarscroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.magicalrice.base_libs.utils.ScreenUtils;
import com.magicalrice.cases.R;

/**
 * Created by Adolph on 2018/6/6.
 */

/**上滑时
 * 当AppBarLayout由展开到收起时，会依次调用
 * onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onStopNestedScroll()
 *
 * 当AppBarLayout收起后继续向上滑动时，会依次调用
 * onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 下滑时
 * 当AppBarLayout全部展开时（即未到顶部时），会依次调用
 * onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 当AppBarLayout全部展开时（即到顶部时），继续向下滑动屏幕，会依次调用
 * onStartNestedScroll()->onNestedScrollAccepted()->onNestedPreScroll()->onNestedScroll()->onStopNestedScroll()
 *
 * 当有快速滑动时会在onStopNestedScroll()前依次调用
 * onNestedPreFling()->onNestedFling()
 *
 * 所以要修改AppBarLayout的越界行为可以重写onNestedPreScroll()或onNestedScroll()，
 * 因为AppBarLayout收起时不会调用onNestedScroll()，所以只能选择重写onNestedPreScroll()。
 *
 */

public class AppBarLayoutScrollBehavior extends AppBarLayout.Behavior {

    private OnAppBarListener mListener;
    private boolean mInited = false;

    private CoordinatorLayout mParent;
    private AppBarLayout mAppBarLayout;

    /**
     * title相关
     */
    private View mTitleSearchBtn;
    private View mTitlePindanBtn;
    private View mTitleSearchTextView;
    private ConstraintLayout mTitleLayout;
    private ConstraintLayout mTitleBarLayout;
    private ImageView mTitleBlurFrontLayout;
    private View mTitleBottomView;

    /**
     * 店铺顶部
     */
    private View mShopHeaderView;

    /**
     * 店铺图片
     */
    private View mImageViewShop;
    private View mImageViewShopLike;

    /**
     * 店铺相关信息 折叠展示
     */
//    private ShopInfoDetailLayout mShopInfoLayout;

    /**
     * 底部 viewpager导航栏
     */
//    private FixedIndicatorView mIndicatorView;

    /**
     * 底部收起按钮
     */
    private ImageView mHeadsUpArrow;

    private int mAppbarLayoutMinOffset;
    private int mAppbarLayoutMaxOffset;
    private int mIndicatorHeight;
    private int mImageViewScaleHeight;
    private int mNormalViewHeight;
    private int mTitleBarHeight;
    private int mShopInfoHeight;

    /**
     * dp距离
     */
    private int mDpToPx10;
    private int mDpToPx20;
    private int mDpToPx40;
    private int mDpToPx50;
    private int mDpToPx60;
    private int mDpToPx155;

    /**
     * 下拉展示店铺信息
     */
    private int mOffsetSpring;
    private ValueAnimator mSpringRecoverAnimator;

    public AppBarLayoutScrollBehavior(Context context, OnAppBarListener listener) {
        this.mListener = listener;

        mDpToPx10 = (int) ScreenUtils.dp2px(context,10);
        mDpToPx20 = (int) ScreenUtils.dp2px(context,20);
        mDpToPx40 = (int) ScreenUtils.dp2px(context,40);
        mDpToPx50 = (int) ScreenUtils.dp2px(context,50);
        mDpToPx60 = (int) ScreenUtils.dp2px(context,60);
        mDpToPx155 = (int) ScreenUtils.dp2px(context,155);
    }

    /**
     * AppBarLayout布局时调用
     *
     * @param parent 父布局CoordinatorLayout
     * @param abl 使用此Behavior的AppBarLayout
     * @param layoutDirection 布局方向
     * @return 返回true表示子View重新布局，返回false表示请求默认布局
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        if (mInited) {
            return super.onLayoutChild(parent,abl,layoutDirection);
        }

        initViews(parent,abl);
        initViewsHeight(parent);
        return super.onLayoutChild(parent, abl, layoutDirection);
    }

    /**
     * 当CoordinatorLayout的子View尝试发起嵌套滚动时调用
     *
     * @param parent 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param directTargetChild CoordinatorLayout的子View，或者是包含嵌套滚动操作的目标View
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param nestedScrollAxes 嵌套滚动的方向
     * @return 返回true表示接受滚动
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
//        L.e("onStartNestedScroll",);
        //屏蔽fling事件
        final boolean started =  super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type) && (type == ViewCompat.TYPE_TOUCH);
        if (started && mSpringRecoverAnimator != null && mSpringRecoverAnimator.isRunning()) {
            mSpringRecoverAnimator.cancel();
        }
        return started;
    }

    /**
     * 当嵌套滚动已由CoordinatorLayout接受时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param directTargetChild CoordinatorLayout的子View，或者是包含嵌套滚动操作的目标View
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param axes 嵌套滚动的方向
     * @param type
     */
    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    /**
     * 当准备开始嵌套滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param dx 用户在水平方向上滑动的像素数
     * @param dy 用户在垂直方向上滑动的像素数
     * @param consumed 输出参数，consumed[0]为水平方向应该消耗的距离，consumed[1]为垂直方向应该消耗的距离
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (dy > 0) {   //上滑
            if (mOffsetSpring - dy >= mAppbarLayoutMinOffset) {
                consumed[1] = dy;
                mOffsetSpring = mOffsetSpring - dy;
            } else {
                consumed[1] = dy;
                mOffsetSpring = mAppbarLayoutMinOffset;
            }
        } else {
            if (mOffsetSpring - dy <= mAppbarLayoutMaxOffset) {
                consumed[1] = dy;
                mOffsetSpring = mOffsetSpring - dy;
            } else {
                consumed[1] = mOffsetSpring - mAppbarLayoutMaxOffset;
                mOffsetSpring = mAppbarLayoutMaxOffset;
            }
        }

        onHandleScroll(child,mNormalViewHeight + mOffsetSpring);
        setTopAndBottomOffset((mOffsetSpring <= 0) ? mOffsetSpring : 0);
        checkShouldSpring(coordinatorLayout,child,mOffsetSpring);
    }

    /**
     * 嵌套滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param dxConsumed 由目标View滚动操作消耗的水平像素数
     * @param dyConsumed 由目标View滚动操作消耗的垂直像素数
     * @param dxUnconsumed 由用户请求但是目标View滚动操作未消耗的水平像素数
     * @param dyUnconsumed 由用户请求但是目标View滚动操作未消耗的垂直像素数
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    /**
     * 当嵌套滚动的子View准备快速滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @return 如果Behavior消耗了快速滚动返回true
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    /**
     * 当嵌套滚动的子View快速滚动时调用
     *
     * @param coordinatorLayout 父布局CoordinatorLayout
     * @param child 使用此Behavior的AppBarLayout
     * @param target 发起嵌套滚动的目标View(即AppBarLayout下面的ScrollView或RecyclerView)
     * @param velocityX 水平方向的速度
     * @param velocityY 垂直方向的速度
     * @param consumed 如果嵌套的子View消耗了快速滚动则为true
     * @return 如果Behavior消耗了快速滚动返回true
     */
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    /**
     * 初始化View
     * @param cool  父布局CoordinatorLayout
     * @param abl 使用此Behavior的AppBarLayout
     */
    private void initViews(CoordinatorLayout cool,AppBarLayout abl) {
        if (mParent == null) {
            mParent = cool;
        }
        if (mAppBarLayout == null) {
            mAppBarLayout = abl;
        }

        if (mTitleLayout == null) {
            mTitleLayout = mParent.findViewById(R.id.tool_bar_layout);
            mTitleBarLayout = mParent.findViewById(R.id.title_bar_layout);
            mTitleSearchBtn = mParent.findViewById(R.id.img_title_bar_search);
            mTitlePindanBtn = mParent.findViewById(R.id.img_title_bar_pindan);
            mTitleSearchTextView = mParent.findViewById(R.id.tv_title_bar_search);
        }

        if (mShopHeaderView == null) {
            mShopHeaderView = mParent.findViewById(R.id.v_appbar_header_shop);
        }

        if (mImageViewShop == null) {
            mImageViewShop = mParent.findViewById(R.id.img_shop);
        }

        if (mImageViewShopLike == null) {
            mImageViewShopLike = mParent.findViewById(R.id.img_like);
        }
    }

    /**
     * 初始化视图高度
     * @param parent
     */
    private void initViewsHeight(CoordinatorLayout parent) {
        //整个Appbar高度
        if (mNormalViewHeight == 0) {
            mNormalViewHeight = mAppBarLayout.getBottom();
        }

        //标题栏高度
        if (mTitleBarHeight == 0) {
            mTitleBarHeight = mTitleBarLayout.getHeight();
        }

        //固定指示器高度
        if (mIndicatorHeight == 0) {
//            mIndicatorHeight =
        }

        if (mNormalViewHeight > 0 && mAppBarLayout.getBottom() < mNormalViewHeight) {
            mInited = true;
            if (mListener != null) {
                mListener.initEnd(mTitleBarHeight + mIndicatorHeight);
            }
            mNormalViewHeight = mAppBarLayout.getBottom() - mIndicatorHeight;
            mImageViewScaleHeight = mAppBarLayout.getBottom() - mIndicatorHeight - mDpToPx20;
            mAppbarLayoutMinOffset = mDpToPx155 - mNormalViewHeight - mIndicatorHeight;
            mAppbarLayoutMaxOffset = parent.getHeight() - mAppBarLayout.getBottom() + mIndicatorHeight;

        }
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        //滑动到最大状态后 屏蔽滑动事件
        return false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return false;
    }

    private boolean onHandleScroll(AppBarLayout child,int fixedBottom) {
        setTitleBarProgress(fixedBottom);
        float scale = setShopImageScaleProcess(fixedBottom);
        setTitleBarProgress(fixedBottom);
        setSearchBtnAlphaProcess(fixedBottom);
        setTitleBarSearchProcess(fixedBottom);
        onTitleLayoutTranslationY(fixedBottom,scale);
        return true;
    }

    private void setTitleBarProgress(float fixedBottom) {
        float alpha;
        if (fixedBottom > mNormalViewHeight) {
            alpha = 0;
        } else if (fixedBottom <= mNormalViewHeight && fixedBottom > mImageViewScaleHeight) {
            alpha = (mNormalViewHeight - fixedBottom) / mDpToPx20;
        } else {
            alpha = 1;
        }

        if (mTitleBlurFrontLayout != null) {
            mTitleBlurFrontLayout.setAlpha(getValidValue(alpha) * 255);
        }
    }

    /**
     * 处理 店铺照片缩放 alpha 倍数为1
     * 缩放 mImageViewScaleHeight 到 mImageViewScaleHeight - mDpToPx40  间距40dp
     * alpha mImageViewScaleHeight - mDpToPx20 到 mImageViewScaleHeight - mDpToPx40  间距20dp
     *
     * @param fixedBottom
     * @return
     */
    private float setShopImageScaleProcess(float fixedBottom) {
        float alpha = 0;
        float scale = 0;
        if (fixedBottom > mImageViewScaleHeight) {
            scale = 1;
        } else if (fixedBottom <= mImageViewScaleHeight && fixedBottom > mImageViewScaleHeight - mDpToPx40) {
            scale = (mDpToPx40 + fixedBottom - mImageViewScaleHeight) / mDpToPx40;
        }

        if (fixedBottom > mImageViewScaleHeight - mDpToPx20) {
            alpha = 1;
        } else if (fixedBottom <= mImageViewScaleHeight - mDpToPx20 && fixedBottom > mImageViewScaleHeight - mDpToPx40) {
            alpha = (mDpToPx40 + fixedBottom - mImageViewScaleHeight) / mDpToPx20;
        }

        alpha = getValidValue(alpha);
        scale = getValidValue(scale);

        if (mImageViewShop != null) {
            mImageViewShop.setScaleX(scale);
            mImageViewShop.setScaleY(scale);
            mImageViewShop.setAlpha(alpha);
        }

        if (mImageViewShopLike != null) {
            mImageViewShopLike.setScaleX(scale);
            mImageViewShopLike.setScaleY(scale);
            mImageViewShopLike.setAlpha(alpha);
        }

        return scale;
    }

    /**
     * 处理 TitleBar搜索按钮隐藏 Alpha效果 mImageViewScaleHeight - mDpToPx20 到 mImageViewScaleHeight -
     * mDpToPx40 间距20dp
     *
     * @param fixedBottom
     */
    private void setSearchBtnAlphaProcess(float fixedBottom) {
        float alpha = 0;
        if (fixedBottom > mImageViewScaleHeight - mDpToPx20) {
            alpha = 1;
        } else if (fixedBottom <= mImageViewScaleHeight - mDpToPx20 && fixedBottom > mImageViewScaleHeight - mDpToPx40) {
            alpha = (mDpToPx40 + fixedBottom - mImageViewScaleHeight) / mDpToPx20;
        }

        if (mTitleSearchBtn != null) {
            mTitleSearchBtn.setAlpha(alpha);
        }

        if (mTitlePindanBtn != null) {
            mTitlePindanBtn.setAlpha(alpha);
        }
    }

    /**
     * 处理 TitleBar搜索框显示 Alpha mImageViewScaleHeight - mDpToPx40 到 mImageViewScaleHeight -
     * mDpToPx50 间距10dp
     *
     * @param fixedBottom
     */
    private void setTitleBarSearchProcess(float fixedBottom) {
        float alpha = 1;
        if (fixedBottom > mImageViewScaleHeight - mDpToPx40) {
            alpha = 0;
        } else if (fixedBottom <= mImageViewScaleHeight - mDpToPx40 && fixedBottom > mImageViewScaleHeight - mDpToPx50) {
            alpha = (mImageViewScaleHeight - mDpToPx40 - fixedBottom) / mDpToPx10;
        }

        if (mTitleSearchTextView != null) {
            mTitleSearchTextView.setAlpha(getValidValue(alpha));
        }
    }

    /**
     * 改变标题布局高度 平移店铺头像
     * 标题栏 修改高度 mNormalViewHeight 到 mImageViewScaleHeight - mDpToPx40 间距 60dp
     * 店铺头像 修改高度 mNormalViewHeight  到 mImageViewScaleHeight - mDpToPx20 间距 40dp
     *
     * @param fixedBottom
     * @param shopScaleY     缩放对平移动画补偿
     */
    private void onTitleLayoutTranslationY(int fixedBottom,float shopScaleY) {
        int imgTranslationY;
        int titleBottomTranslationY;
        int titleBottom;

        if (fixedBottom > mNormalViewHeight) {
            imgTranslationY = 0;
        } else if (fixedBottom <= mNormalViewHeight && fixedBottom > mImageViewScaleHeight - mDpToPx20) {
            imgTranslationY = fixedBottom - mNormalViewHeight;
        } else {
            imgTranslationY = -mDpToPx40;
        }

        if (fixedBottom > mImageViewScaleHeight) {
            shopScaleY = 0;
        } else if (fixedBottom <= mImageViewScaleHeight && fixedBottom > mImageViewScaleHeight - mDpToPx20) {
            shopScaleY = mImageViewShop.getHeight() * (1 - shopScaleY) / 2;
        } else if (fixedBottom <= mImageViewScaleHeight - mDpToPx20 && fixedBottom > mImageViewScaleHeight -mDpToPx40) {
            shopScaleY = mImageViewShop.getHeight() / 4;
        } else {
            shopScaleY = mImageViewShop.getHeight() / 4;
        }

        if (fixedBottom > mNormalViewHeight) {
            titleBottomTranslationY  = 0;
            titleBottom = mNormalViewHeight - mShopInfoHeight + mDpToPx20;
        } else if (fixedBottom <= mNormalViewHeight && fixedBottom > mNormalViewHeight - mDpToPx40) {
            titleBottomTranslationY = fixedBottom - mNormalViewHeight;
            titleBottom = fixedBottom - mShopInfoHeight + mDpToPx20;
        } else if (fixedBottom <= mNormalViewHeight - mDpToPx40 && fixedBottom > mNormalViewHeight - mDpToPx60) {
            titleBottomTranslationY = -mDpToPx40;
            titleBottom = fixedBottom - mShopInfoHeight + mDpToPx20;
        } else {
            titleBottomTranslationY = -mDpToPx40;
            titleBottom = mTitleBarHeight;
        }

        if (mTitleBottomView != null) {
            mTitleBottomView.setTranslationY(titleBottomTranslationY);
        }

        if (mTitleBlurFrontLayout != null) {
            mTitleBlurFrontLayout.setBottom(titleBottom);
        }

        if (mTitleLayout != null) {
            mTitleLayout.setBottom(titleBottom);
        }

        if (mImageViewShop != null) {
            mImageViewShop.setTranslationY(imgTranslationY + shopScaleY);
        }

        if (mImageViewShopLike != null) {
            mImageViewShopLike.setTranslationY(imgTranslationY);
        }
    }

    private void checkShouldSpring(CoordinatorLayout coordinatorLayout,AppBarLayout child,int newOffset) {
        if (newOffset < 0) {
            newOffset = 0;
        }
    }

    private float getValidValue(float value) {
        if (value < 0) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        return value;
    }
}
