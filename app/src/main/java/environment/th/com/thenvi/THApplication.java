package environment.th.com.thenvi;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2015/11/16.
 */
public class THApplication extends Application {
//    public BDLocationService locationService;
    @Override
    public void onCreate() {
        super.onCreate();
//        locationService=new BDLocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
    }
}
