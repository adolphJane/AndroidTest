package com.magicalrice.adolph.custom_widget.recyclerview;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Adolph on 2018/1/30.
 */

public class BaseMagicalViewHolder extends RecyclerView.ViewHolder {

    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> views;

    public BaseMagicalViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseMagicalViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public BaseMagicalViewHolder setText(@IdRes int viewId, CharSequence content) {
        View view = getView(viewId);
        if (view instanceof Button) {
            ((Button) view).setText(content);
        } else if (view instanceof TextView) {
            ((TextView) view).setText(content);
        }
        return this;
    }

    public BaseMagicalViewHolder setTextColor(@IdRes int viewId, @ColorInt int colorId) {
        View view = getView(viewId);
        if (view instanceof Button) {
            ((Button) view).setTextColor(colorId);
        } else if (view instanceof TextView) {
            ((TextView) view).setTextColor(colorId);
        }
        return this;
    }

    public BaseMagicalViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
        return this;
    }

    public BaseMagicalViewHolder setImageRes(@IdRes int viewId, @DrawableRes int imageRes) {
        ImageView view = getView(viewId);
        view.setImageResource(imageRes);
        return this;
    }

    public BaseMagicalViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        ImageView view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseMagicalViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseMagicalViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseMagicalViewHolder setAlpha(@IdRes int viewId, float value) {
        getView(viewId).setAlpha(value);
        return this;
    }

    public BaseMagicalViewHolder setVisible(@IdRes int viewId, int visible) {
        getView(viewId).setVisibility(visible);
        return this;
    }

    public BaseMagicalViewHolder addLink(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseMagicalViewHolder setTypeFace(@IdRes int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public BaseMagicalViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseMagicalViewHolder setMax(@IdRes int viewId, int max) {
        View view = getView(viewId);
        if (view instanceof ProgressBar) {
            ((ProgressBar) view).setMax(max);
        } else if (view instanceof RatingBar) {
            ((RatingBar) view).setMax(max);
        }
        return this;
    }

    public BaseMagicalViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }
}