package com.magicalrice.adolph.custom_widget.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import com.magicalrice.adolph.custom_widget.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Adolph on 2018/3/13.
 */

public class CustomKeyboardView extends KeyboardView {

    private Context mContext;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        List<Key> keys = null;
        if (keyboard != null) {
            keys = keyboard.getKeys();
        }
        if (keys != null) {
            for (Key key : keys) {
                if (key.codes[0] == -5) {
                    drawKeyBackground(R.drawable.cw_layerlist_keyboard_word_eng_del, canvas, key);
                } else if (key.codes[0] == -35) {
                    drawKeyBackground(R.drawable.cw_layerlist_keyboard_word_num_del, canvas, key);
                } else if (key.codes[0] == -1) {
                    if (BaseKeyboardView.isUpper) {
                        drawKeyBackground(R.drawable.cw_layerlist_keyboard_word_shift_da, canvas, key);
                    } else {
                        drawKeyBackground(R.drawable.cw_layerlist_keyboard_word_shift, canvas, key);
                    }
                } else if (key.codes[0] == -2 || key.codes[0] == 90001) {
                    drawKeyBackground(R.drawable.cw_selector_keyboard_bg, canvas, key);
                    drawText(canvas, key);
                }
            }
        }
    }

    private void drawKeyBackground(int drawableId, Canvas canvas, Key key) {
        Drawable dr = mContext.getResources().getDrawable(drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            dr.setState(drawableState);
        }
        dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        dr.draw(canvas);
    }

    private void drawText(Canvas canvas, Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        if (key.label != null) {
            String label = key.label.toString();
            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }
            paint.getTextBounds(key.label.toString(), 0, key.label.toString().length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2), key.y + (key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2, key.x + (key.width + key.icon.getIntrinsicWidth()) / 2, key.y + (key.height + key.icon.getIntrinsicHeight()) / 2);
            key.icon.draw(canvas);
        }
    }
}
