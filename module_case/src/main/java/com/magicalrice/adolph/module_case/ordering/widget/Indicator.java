package com.magicalrice.adolph.module_case.ordering.widget;

import android.view.View;
import android.view.ViewGroup;

import com.magicalrice.adolph.module_case.ordering.widget.slidebar.ScrollBar;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Adolph on 2018/6/5.
 */

public interface Indicator {
    /**
     * 设置适配器
     */
    void setAdapter(IndicatorAdapter adapter);

    /**
     * 设置选中监听
     *
     * @param onItemSelectedListener
     */
    void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener);

    /**
     * 获取选中监听
     *
     * @return
     */
    OnItemSelectedListener getOnItemSelectListener();

    /**
     * 设置滑动变化的转换监听，tab在切换过程中会调用此监听。<br>
     * 设置它可以自定义实现在滑动过程中，tab项的字体变化，颜色变化等等效果<br>
     * 目前提供的子类
     *
     * @param onTransitionListener
     */
    void setOnTransitionListener(OnTransitionListener onTransitionListener);

    OnTransitionListener getOnTransitionListener();

    /**
     * 设置滑动块<br>
     * 设置它可以自定义滑动块的样式。<br>
     * 目前提供的子类 {}
     *
     * @param scrollBar
     */
    void setScrollBar(ScrollBar scrollBar);

    IndicatorAdapter getAdapter();

    /**
     * 设置当前项.<br>
     * 如果使用IndicatorViewPager则使用IndicatorViewPager.setCurrentItem而不是在调用该方法
     *
     * @param item
     */
    void setCurrentItem(int item);

    void setCurrentItem(int item, boolean anim);

    /**
     * 获取每一项的tab
     *
     * @param item 索引
     * @return
     */
    View getItemView(int item);

    /**
     * 获取当前选中项
     *
     * @return
     */
    int getCurrentItem();

    /**
     * 获取之前选中的项,可能返回-1，表示之前没有选中
     *
     * @return
     */
    int getPreSelectItem();

    /**
     * ViewPager切换变化的函数
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * 适配器
     */
    abstract class IndicatorAdapter {
        private Set<DataSetObserver> observers = new LinkedHashSet<>(2);

        public abstract int getCount();

        public abstract View getView(int position, View convertView, ViewGroup parent, int
                currentSelectedIndex);

        public void notifyDataSetChanged() {
            for (DataSetObserver dataSetObserver : observers) {
                dataSetObserver.onChange();
            }
        }

        public void registDataSetObserver(DataSetObserver observer) {
            observers.add(observer);
        }

        public void unRegistDataSetObserver(DataSetObserver observer) {
            observers.remove(observer);
        }

    }

    /**
     * @version 1.0 数据源观察者
     * @author试着飞
     * @date 2014年11月1日
     */
    interface DataSetObserver {
        void onChange();
    }

    /**
     * tab项选中监听
     */
    interface OnItemSelectedListener {
        /**
         * 注意 preItem 可能为 -1。表示之前没有选中过,每次adapter.notifyDataSetChanged也会将preItem
         * 设置为-1；
         *
         * @param selectItemView 当前选中的view
         * @param select         当前选中项的索引
         * @param preSelect      之前选中项的索引
         */
        void onItemSelected(View selectItemView, int select, int preSelect);
    }

    /**
     * tab滑动变化的转换监听，tab在切换过程中会调用此监听。<br>
     * 通过它可以自定义实现在滑动过程中，tab项的字体变化，颜色变化等等效果<br>
     * 目前提供的子类
     */
    interface OnTransitionListener {
        void onTransition(View view, int position, float selectPercent);
    }
}
