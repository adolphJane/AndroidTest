package com.magicalrice.adolph.custom_widget.recyclerview.typeTwo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.adolph.common.base.BaseActivity;
import com.magicalrice.adolph.common.base.RouterTable;
import com.magicalrice.adolph.custom_widget.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterTable.ITEM_WIDGET_RECYCLERVIEW)
public class RecyclerviewTypeTwoActivity extends BaseActivity {
    /**服务器端一共多少条数据*/
    private static final int TOTAL_COUNTER = 64;

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 10;

    /**已经获取到多少条数据了*/
    private int mCurrentCounter = 0;

    private RecyclerView mRecyclerView = null;

    private DataAdapter mDataAdapter = null;

    private PreviewHandler mHandler = new PreviewHandler(this);

    @Override
    protected int getContentViewId() {
        return R.layout.w_activity_recycler_type2;
    }

    @Override
    protected void initUI() {
        mRecyclerView = findViewById(R.id.list);

    }

    @Override
    protected void initData() {
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("item" + i);
        }

        mCurrentCounter = dataList.size();

        mDataAdapter = new DataAdapter(dataList,this);
        mRecyclerView.setAdapter(mDataAdapter);

        GridLayoutManager manager = new GridLayoutManager(this,2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup(mDataAdapter,manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);

        mDataAdapter.addHeaderView(new ItemHeader(this));
        mDataAdapter.setFooterViewState(REQUEST_COUNT,LoadingFooter.NORMAL,null);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return null;
    }

    private void addItems(ArrayList<String> list) {
        mDataAdapter.addAll(list);
        mCurrentCounter += list.size();
    }

    private void notifyDataSetChanged() {
        mDataAdapter.notifyDataSetChanged();
    }

    private static class PreviewHandler extends Handler {

        private WeakReference<RecyclerviewTypeTwoActivity> ref;

        PreviewHandler(RecyclerviewTypeTwoActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final RecyclerviewTypeTwoActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case -1:
                    int currentSize = activity.mDataAdapter.getItemCount();

                    //模拟组装10个数据
                    ArrayList<String> newList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (newList.size() + currentSize >= TOTAL_COUNTER) {
                            break;
                        }

                        newList.add("item" + (currentSize + i));
                    }

                    activity.addItems(newList);
                    activity.mDataAdapter.setFooterViewState(LoadingFooter.NORMAL);
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    break;
                case -3:
                    activity.mDataAdapter.setFooterViewState(REQUEST_COUNT,LoadingFooter.THE_END,null);
                    break;
            }
        }
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            @LoadingFooter.State int state = mDataAdapter.getFooterViewState();
            if(state == LoadingFooter.LOADING) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more
                mDataAdapter.setFooterViewState(REQUEST_COUNT, LoadingFooter.LOADING, null);
                requestData();
            } else {
                //the end
                mDataAdapter.setFooterViewState(REQUEST_COUNT, LoadingFooter.THE_END, null);
            }
        }
    };

    /**
     * 模拟请求网络
     */
    private void requestData() {

        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(-1);
            }
        }.start();
    }

    private class DataAdapter extends MagicalRecyclerAdapter<String> {
        private LayoutInflater mInflater;
        private List<String> mDataList = new ArrayList<>();

        public DataAdapter(List<String> mList, Context context) {
            super(mList);
            mInflater = LayoutInflater.from(context);
            mDataList = mList;
        }

        private void addAll(ArrayList<String> list) {
            int lastIndex = this.mDataList.size();
            if (this.mDataList.addAll(list)) {
                notifyItemRangeInserted(lastIndex, list.size());
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateBaseViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DataHolder(mInflater.inflate(R.layout.w_layout_item_recycler2,parent,false));
        }

        @Override
        public void onBindBaseViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String item = mDataList.get(position);
            DataHolder dataHolder = (DataHolder) holder;
            dataHolder.textView.setText(item);
        }

        class DataHolder extends ViewHolder {
            private ViewGroup wrapperView;
            private TextView textView;

            public DataHolder(View itemView) {
                super(itemView);

                wrapperView = itemView.findViewById(R.id.wrapper_view);
                textView = itemView.findViewById(R.id.tv_info);
            }
        }
    }
}
