package com.magicalrice.view.circular_progress_bar;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.router.RouterTable;
import com.magicalrice.view.R;
import com.magicalrice.view.circular_progress_bar.prototype_meizu.PrototypeMeizuCircularProgressBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Adolph on 2018/3/29.
 */

@Route(path = RouterTable.ITEM_VIEW_PROGRESS_BAR)
public class CircularProgressBarActivity extends BaseActivity implements View.OnClickListener {
    private PrototypeMeizuCircularProgressBar bar;
    private Disposable disposable;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_circular_progress_bar;
    }

    @Override
    protected void initUI() {
        bar = findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        bar.setOnClickListener(this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (disposable != null) {
            disposable.dispose();
        }
        if (bar.getStatus() == PrototypeMeizuCircularProgressBar.DOWNLOADING) {
            bar.onPauseProgress();
        } else {
            onStartDownload();
        }
    }

    private void onStartDownload() {
        bar.onStartProgress();
        disposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (aLong > 9) {
                        bar.onFinishProgress();
                    } else {
                        bar.setProgress((int) (aLong * 10),100);
                    }
                });
        bar.setListener(disposable::dispose);
    }
}
