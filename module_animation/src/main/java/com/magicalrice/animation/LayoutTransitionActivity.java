package com.magicalrice.animation;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.animation.databinding.ActivityLayoutTransitionBinding;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.router.RouterTable;

/**
 * Created by Adolph on 2018/6/15.
 */

/**
 * APPEARING：容器中出现一个视图。
 * DISAPPEARING：容器中消失一个视图。
 * CHANGING：布局改变导致某个视图随之改变，例如调整大小，但不包括添加或者移除视图。
 * CHANGE_APPEARING：其他视图的出现导致某个视图改变。
 * CHANGE_DISAPPEARING：其他视图的消失导致某个视图改变。
 */

@Route(path = RouterTable.ITEM_ANIMARION_LAYOUT_TRANSITION)
public class LayoutTransitionActivity extends BaseActivity<ActivityLayoutTransitionBinding> implements View.OnClickListener {
    private LayoutTransition transition;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_layout_transition;
    }

    @Override
    protected void initUI() {
        initToolbar();
        transition = new LayoutTransition();
        binding.llContainer.setLayoutTransition(transition);

        Animator appearAnim = ObjectAnimator.ofFloat(null,"rotationY",90f,0).setDuration(transition.getDuration(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.APPEARING,appearAnim);

        Animator disappearAnim = ObjectAnimator.ofFloat(null,"rotationX",0,90f).setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING,disappearAnim);

//        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
//        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
//        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0,1);
//        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom",0, 1);
//        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",1f, 0f, 1f);
//        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",1f, 0f, 1f);
//
//        Animator changeDisappearAnim = ObjectAnimator.ofPropertyValuesHolder(this,pvhLeft,pvhTop,pvhRight,pvhBottom,pvhScaleY,pvhScaleX);
//        changeDisappearAnim.setDuration(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        binding.btnAdd.setOnClickListener(this);
        binding.header.findViewById(R.id.img_web).setOnClickListener(this);
    }

    @Override
    protected String getDemoName() {
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Button button = new Button(this);
            button.setText("移除自己");
            button.setOnClickListener(v -> binding.llContainer.removeView(v));
            binding.llContainer.addView(button,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else if (view.getId() == R.id.img_web) {
            ARouter.getInstance().build(RouterTable.ITEM_WEB_DOC).withString("path",RouterTable.WEB_PATH_LAYOUTTRANSITION).navigation(LayoutTransitionActivity.this, new NavigationCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    showShortToast("发现目标Activity," + postcard.getPath());
                }

                @Override
                public void onLost(Postcard postcard) {
                    showShortToast("没有目标Activity,"  + postcard.getPath());
                }

                @Override
                public void onArrival(Postcard postcard) {
                    showShortToast("跳转完成,"  + postcard.getPath());
                }

                @Override
                public void onInterrupt(Postcard postcard) {
                    showShortToast("已被拦截,"  + postcard.getPath());
                }
            });
        }
    }
}
