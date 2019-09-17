package com.magicalrice.view.data_graph;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.magicalrice.common.base.BaseActivity;
import com.magicalrice.common.router.RouterTable;
import com.magicalrice.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adolph on 2018/6/19.
 */
@Route(path = RouterTable.ITEM_VIEW_DATA_GRAPH)
public class DataGraphActivity extends BaseActivity {
    private RadarView radarView;
    private HistogramView hisView;
    List<HistogramData> datas;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_data_graph;
    }

    @Override
    protected void initUI() {
        radarView = findViewById(R.id.radarView);
        hisView = findViewById(R.id.hisView);
    }

    @Override
    protected void initData() {
        List<Double> data=new ArrayList<>();
        data.add(40.0);
        data.add(80.0);
        data.add(80.0);
        data.add(80.0);

        List<Double> data1=new ArrayList<>();
        data1.add(80.0);
        data1.add(70.0);
        data1.add(30.0);
        data1.add(80.0);

        radarView.setData(data,data1);

        int[] values = {1,2,3,1,2};
        String [] days ={"Mon","Tue","Wed","Thu","Fri"};
        String [] xins ={"21","22","23","24","25"};
        datas = new ArrayList<>();
        for (int i=0;i<5;i++){
            HistogramData bean = new HistogramData();
            bean.setDay(xins[i]);
            bean.setXinqi(days[i]);
            bean.setValue(values[i]);
            if(values[i]==1){
                bean.setLiveFlag(true);
            }else if(values[i]==2){
                bean.setLiveFlag(true);
                bean.setQuestionFlag(true);
            }else if(values[i]==3){
                bean.setLiveFlag(true);
                bean.setQuestionFlag(true);
                bean.setVideoFlag(true);
            }
            datas.add(bean);
        }
        hisView.setData(datas);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getDemoName() {
        return null;
    }
}
