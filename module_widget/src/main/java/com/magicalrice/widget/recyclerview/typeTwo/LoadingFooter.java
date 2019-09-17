package com.magicalrice.widget.recyclerview.typeTwo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.magicalrice.adolph.custom_widget.R;

public class LoadingFooter extends RelativeLayout {
    public static final int NORMAL = 1;
    public static final int THE_END = 2;
    public static final int LOADING = 3;
    public static final int NETWORK_ERROR = 4;

    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;
    private TextView mLoadingText;
    private ProgressBar mLoadingProgress;

    @State
    private int mState = NORMAL;

    @IntDef({NORMAL, THE_END, LOADING, NETWORK_ERROR})
    public  @interface State {
    }

    public LoadingFooter(Context context) {
        this(context, null);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_list_footer, this);
        setOnClickListener(null);
        setState(NORMAL, true);
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        setState(state, true);
    }

    public void setState(@State int state, boolean showView) {
        if (mState == state)
            return;
        mState = state;
        switch (state) {
            case NORMAL:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }
                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                break;
            case LOADING:
                setOnClickListener(null);
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }
                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                if (mLoadingView == null) {
                    ViewStub viewStub = findViewById(R.id.loading_viewstub);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = mLoadingView.findViewById(R.id.loading_progress);
                    mLoadingText = mLoadingView.findViewById(R.id.tv_loading);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);
                mLoadingProgress.setVisibility(VISIBLE);
                mLoadingText.setText("正在加载中...");
                break;
            case THE_END:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }
                if (mNetworkErrorView != null) {
                    mNetworkErrorView.setVisibility(GONE);
                }
                if (mTheEndView == null) {
                    ViewStub viewStub = findViewById(R.id.end_viewstub);
                    mTheEndView = viewStub.inflate();
                } else {
                    mTheEndView.setVisibility(VISIBLE);
                }
                mTheEndView.setVisibility(showView ? VISIBLE : GONE);
                break;
            case NETWORK_ERROR:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }

                if (mNetworkErrorView == null) {
                    ViewStub viewStub = findViewById(R.id.network_error_viewstub);
                    mNetworkErrorView = viewStub.inflate();
                } else {
                    mNetworkErrorView.setVisibility(VISIBLE);
                }
                mNetworkErrorView.setVisibility(showView ? VISIBLE : GONE);
                break;
            default:
                break;
        }
    }
}
