package com.magicalrice.adolph.custom_widget.keyboard.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.magicalrice.adolph.custom_widget.R;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Adolph on 2018/3/13.
 */

public class BaseKeyboardView {
    private Activity mContext;
    private View parentView;
    private KeyboardView mLetterView;       //字母键盘view
    private KeyboardView mNumberView;       //数字键盘view
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

        mNumberKeyboard = new Keyboard(mContext, R.xml.cw_keyboard_numbers);
        mLetterKeyboard = new Keyboard(mContext, R.xml.cw_keyboard_word);
        mSymbolKeyboard = new Keyboard(mContext, R.xml.cw_keyboard_symbol);
        mNumberView = parentView.findViewById(R.id.keyboard_view);
        mLetterView = parentView.findViewById(R.id.keyboard_view2);
        mHeaderView = parentView.findViewById(R.id.keyboard_header);

        mNumberView.setKeyboard(mNumberKeyboard);
        mNumberView.setEnabled(true);
        mNumberView.setPreviewEnabled(false);
        mNumberView.setOnKeyboardActionListener(listener);
        mLetterView.setKeyboard(mLetterKeyboard);
        mLetterView.setEnabled(true);
        mLetterView.setPreviewEnabled(false);
        mLetterView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
            Logger.d("onPress" + primaryCode);
            if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                List<Keyboard.Key> keyList = mLetterKeyboard.getKeys();
                mLetterView.setPreviewEnabled(false);
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                mLetterView.setPreviewEnabled(false);
            } else if (primaryCode == 32) {
                mLetterView.setPreviewEnabled(false);
            } else {
                mLetterView.setPreviewEnabled(true);
            }
        }

        @Override
        public void onRelease(int primaryCode) {
            Logger.d("onRelease" + primaryCode);
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Logger.d("onKey" + primaryCode);
            try {
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
                    mLetterView.setKeyboard(mLetterKeyboard);
                } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
                    //数字和字母切换
                    if (isNumber) {
                        showLetterView();
                        showLetterView2();
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
                    editable.insert(start,Character.toString((char) primaryCode));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
    };

    //字母-符号，切换显示字母
    private void showLetterView() {
        if (mLetterView != null) {
            isSymbol = false;
            mLetterView.setKeyboard(mLetterKeyboard);
        }
    }

    //字母-符号，切换显示符号
    private void showSymbolView() {
        if (mLetterView != null) {
            isSymbol = true;
            mLetterView.setKeyboard(mSymbolKeyboard);
        }
    }

    //字母-数字，切换显示字母键盘
    private void showLetterView2() {
        try {
            if (mLetterView != null && mNumberView != null) {
                isNumber = false;
                mLetterView.setVisibility(View.VISIBLE);
                mNumberView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //数字-字母，显示数字键盘
    private void showNumberView() {
        try {
            if (mLetterView != null && mNumberView != null) {
                isNumber = true;
                mLetterView.setVisibility(View.INVISIBLE);
                mNumberView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Drawable icon = key.icon;
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
        try {
            int visibility = mLetterView.getVisibility();
            if (visibility == View.VISIBLE) {
                mHeaderView.setVisibility(View.GONE);
                mLetterView.setVisibility(View.GONE);
            }
            visibility = mNumberView.getVisibility();
            if (visibility == View.VISIBLE) {
                mHeaderView.setVisibility(View.GONE);
                mNumberView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示键盘
     * @param editText
     */
    public void showKeyboard(EditText editText) {
        try {
            this.mEditText = editText;
            int inputType = mEditText.getInputType();
            mHeaderView.setVisibility(View.VISIBLE);
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
                    showLetterView2();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
