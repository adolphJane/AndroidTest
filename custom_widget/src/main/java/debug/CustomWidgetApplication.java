package debug;

import com.magicalrice.adolph.common.base.BaseApplication;
import com.magicalrice.adolph.common.utils.ApplicationUtils;

/**
 * Created by Adolph on 2018/2/8.
 */

public class CustomWidgetApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtils.init(this);
    }
}
