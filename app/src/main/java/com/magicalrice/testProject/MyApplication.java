package com.magicalrice.testProject;

import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.common.base.BaseApplication;

/**
 * Created by Adolph on 2018/2/8.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
