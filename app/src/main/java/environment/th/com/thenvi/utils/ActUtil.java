package environment.th.com.thenvi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baidu.mapapi.model.LatLng;

import java.io.File;

/**
 * Created by 可爱的蘑菇 on 2016/3/13.
 */
public class ActUtil {

    public static void closeSoftPan(Activity act) {
        View view = act.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager)act.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

//    public static void bd_encrypt(double gg_lat, double gg_lon, double &bd_lat, double &bd_lon)
//    {
//        double x = gg_lon, y = gg_lat;
//        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
//        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
//        bd_lon = z * Math.cos(theta) + 0.0065;
//        bd_lat = z * Math.sin(theta) + 0.006;
//    }
//
//    public static void bd_decrypt(double bd_lat, double bd_lon, double &gg_lat, double &gg_lon)
//    {
//        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
//        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
//        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
//        gg_lon = z * Math.cos(theta);
//        gg_lat = z * Math.sin(theta);
//    }

    //墨卡托转经纬度
    public static LatLng Mercator2lonLat(double mx, double my)
    {
        double x = mx / 20037508.34 * 180;
        double y = my / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        LatLng latLng = new LatLng(y,x);
        return latLng;
    }

}
