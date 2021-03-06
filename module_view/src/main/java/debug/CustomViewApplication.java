package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.magicalrice.adolph.module_view.BuildConfig;
import com.magicalrice.common.base.BaseApplication;

/**
 * Created by Adolph on 2018/2/8.
 */

public class CustomViewApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouter();
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
