package com.magicalrice.adolph.custom_widget.keyboard.view;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.magicalrice.adolph.base_utils.utils.ScreenUtils;
import com.magicalrice.adolph.custom_widget.R;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Adolph on 2018/3/13.
 */

public class BaseKeyboardView implements KeyboardView.OnKeyboardActionListener{
    private Activity mContext;
    private View parentView;
    private View backView;
    private KeyboardView mKeyView;       //数字键盘view
    private Keyboard mNumberKeyboard;       //数字键盘
    private Keyboard mLetterKeyboard;       //字母键盘
    private Keyboard mSymbolKeyboard;       //符号键盘

    private boolean isNumber = true;        //是否数字键盘
    public static boolean isUpper = false;  //是否大写
    private boolean isSymbol = false;       //是否是符号
    private EditText mEditText;
    private View mHeaderView;

    public void setEditText(EditText text) {
        mEditText = text;
    }

    public BaseKeyboardView(Activity context, View view) {
        mContext = context;
        parentView = view;

        mNumberKeyboard = new Keyboard(mContext, R.xml.w_keyboard_numbers);
        mLetterKeyboard = new Keyboard(mContext, R.xml.w_keyboard_word);
        mSymbolKeyboard = new Keyboard(mContext, R.xml.w_keyboard_symbol);
        mKeyView = parentView.findViewById(R.id.keyboard_view);
        mHeaderView = parentView.findViewById(R.id.keyboard_header);
        backView = parentView.findViewById(R.id.view_back);

        mKeyView.setKeyboard(mNumberKeyboard);
        mKeyView.setEnabled(true);
        mKeyView.setPreviewEnabled(false);
        mKeyView.setOnKeyboardActionListener(this);
    }

    //字母-符号，切换显示字母
    private void showLetterView() {
        if (mKeyView != null) {
            isSymbol = false;
            isNumber = false;
            fixBackView(5);
            mKeyView.setKeyboard(mLetterKeyboard);
        }
    }

    //字母-符号，切换显示符号
    private void showSymbolView() {
        if (mKeyView != null) {
            isSymbol = true;
            isNumber = false;
            fixBackView(25);
            mKeyView.setKeyboard(mSymbolKeyboard);
        }
    }

    //数字-字母，显示数字键盘
    private void showNumberView() {
        if (mKeyView != null) {
            isNumber = true;
            isSymbol = false;
            fixBackView(25);
            mKeyView.setKeyboard(mNumberKeyboard);
        }
    }

    private void fixBackView(int value) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) backView.getLayoutParams();
        params.height = (int) ScreenUtils.dp2px(mContext,value);
        backView.setLayoutParams(params);
    }

    /**
     * 切换大小写
     */
    private void changeKeyboart() {
        List<Keyboard.Key> keyList = mLetterKeyboard.getKeys();
        if (isUpper) {
            //大写切换小写
            isUpper = false;
            for (Keyboard.Key key : keyList) {
                if (key.label != null && isLetter(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {
            //小写切换大写
            isUpper = true;
            for (Keyboard.Key key : keyList) {
                if (key.label != null && isLetter(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    /**
     * 判断是否是字母
     *
     * @param str
     * @return
     */
    private boolean isLetter(String str) {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        return wordStr.contains(str.toLowerCase());
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        if (mKeyView != null && mKeyView.getVisibility() == View.VISIBLE) {
            mHeaderView.setVisibility(View.GONE);
            mKeyView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示键盘
     *
     * @param editText
     */
    public void showKeyboard(EditText editText) {
        this.mEditText = editText;
        int inputType = mEditText.getInputType();
        mHeaderView.setVisibility(View.VISIBLE);
        mKeyView.setVisibility(View.VISIBLE);
        switch (inputType) {
            case InputType.TYPE_CLASS_NUMBER:
                showNumberView();
                break;
            case InputType.TYPE_CLASS_PHONE:
                showNumberView();
                break;
            case InputType.TYPE_NUMBER_FLAG_DECIMAL:
                showNumberView();
                break;
            default:
                showLetterView();
                break;
        }
    }

    @Override
    public void onPress(int primaryCode) {
        Logger.d("onPress" + primaryCode);
        if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            mKeyView.setPreviewEnabled(false);
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            mKeyView.setPreviewEnabled(false);
        } else if (primaryCode == 32 || primaryCode == -2 || primaryCode == -35 || primaryCode == 90001 || primaryCode == -5) {
            mKeyView.setPreviewEnabled(false);
        } else {
            mKeyView.setPreviewEnabled(true);
        }
    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Logger.d("onKey" + primaryCode);
        if (mEditText == null)
            return;
        Editable editable = mEditText.getText();
        int start = mEditText.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            hideKeyboard();
        } else if (primaryCode == Keyboard.KEYCODE_DELETE || primaryCode == -35) {
            //回退键，删除字符
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            //大小写切换
            changeKeyboart();
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
            //数字和字母切换
            if (isNumber) {
                showLetterView();
            } else {
                showNumberView();
            }
        } else if (primaryCode == 90001) {
            //字母和字符切换
            if (isSymbol) {
                showLetterView();
            } else {
                showSymbolView();
            }
        } else {
            //输入键盘值
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
