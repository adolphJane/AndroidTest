package com.magicalrice.widget.keyboard.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.magicalrice.adolph.custom_widget.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/3/13.
 */

public class KeyboardUtils implements View.OnClickListener {
    protected View view;
    protected Dialog popWindow;
    protected Activity mContext;
    private EditText editText;
    private List<String> contentList;
    private BaseKeyboardView keyboardView;

    public KeyboardUtils(Activity mContext) {
        try {
            this.mContext = mContext;
            if (contentList == null) {
                contentList = new ArrayList<>();
            }

            if (popWindow == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.keyboard_popu,null);
                popWindow = new Dialog(mContext,R.style.keyboard_popupAnimation);
                view.findViewById(R.id.keyboard_finish).setOnClickListener(this);
                view.findViewById(R.id.keyboard_back_hide).setOnClickListener(this);
            }

            popWindow.setContentView(view);
            popWindow.setCanceledOnTouchOutside(true);
            Window window = popWindow.getWindow();
            window.setWindowAnimations(R.style.keyboard_popupAnimation);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            popWindow.setOnDismissListener(dialog -> {
                if (editText != null && editText.isFocused()) {
                    editText.clearFocus();
                }
            });
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
            if (keyboardView == null) {
                keyboardView = new BaseKeyboardView(mContext,view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏系统键盘
     * @param editText
     */
    public void hideSystemSoftKeyboard(EditText editText) {
        int sdkInd = Build.VERSION.SDK_INT;
        if (sdkInd >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText,false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }

        //如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

    public void attachTo(final EditText editText) {
        this.editText = editText;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        hideSystemSoftKeyboard(editText);
        popWindow.show();
        keyboardView.showKeyboard(editText);
    }

    public void dismiss() {
        if (popWindow != null && popWindow.isShowing()) {
            popWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            int i = v.getId();
            if (i == R.id.keyboard_finish) {
                keyboardView.hideKeyboard();
                dismiss();
            } else if (i == R.id.keyboard_back_hide) {
                keyboardView.hideKeyboard();
                dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}