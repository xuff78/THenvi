package environment.th.com.thenvi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.MapAreaInfo;

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

    public static ArrayList<Overlay> showAreaSpace(Activity act, BaiduMap baiduMap, ArrayList<MapAreaInfo> areaInfo, int filterColor) {
        ArrayList<Overlay> layers=new ArrayList<>();
        int linecolor=act.getResources().getColor(R.color.white);
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, linecolor))
                    .fillColor(filterColor);
            //在地图上添加多边形Option，用于显示
            Overlay ol=baiduMap.addOverlay(polygonOption);
            layers.add(ol);
        }
        return layers;
    }

    public static ArrayList<Overlay> showAreaSpace(Activity act, BaiduMap baiduMap, ArrayList<MapAreaInfo> areaInfo) {
        ArrayList<Overlay> layers=new ArrayList<>();
        int color=0;
        int linecolor=act.getResources().getColor(R.color.bg_coffee);
        for(int i=0;i<areaInfo.size();i++) {
            if(i==0){
                color=0xaaFF7F24;
            }else if(i==1){
                color=0xaa008B45;
            }else if(i==2){
                color=0xaa00C5CD;
            }else if(i==3){
                color=0xaaCD3333;
            }else if(i==4){
                color=0xaa1b93e5;
            }else if(i==5){
                color=0xaaCD2990;
            }
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, linecolor))
                    .fillColor(color);
            //在地图上添加多边形Option，用于显示
            Overlay ol=baiduMap.addOverlay(polygonOption);
            layers.add(ol);
        }
        return layers;
    }

    public static ArrayList<Overlay> showWorkingLine(BaiduMap baiduMap, ArrayList<MapAreaInfo> areaInfo) {
        ArrayList<Overlay> layers=new ArrayList<>();
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            PolylineOptions polygonOption = new PolylineOptions()
                    .points(infos)
                    .width(5)
                    .color(0x66ffffff);
            //在地图上添加多边形Option，用于显示
            layers.add(baiduMap.addOverlay(polygonOption));
        }
        return layers;
    }

    public static void changeLayerStatus(ArrayList<Overlay> layers, MapStatus mapStatus){
        if(mapStatus.zoom>12){
            ActUtil.hideLayers(layers);
        }else if(mapStatus.zoom<=12) {
            ActUtil.showLayers(layers);
        }
    }

    public static void hideLayers(ArrayList<Overlay> layers) {
        if(layers.size()>0&&layers.get(0).isVisible())
        for(int i=0;i<layers.size();i++) {
            layers.get(i).setVisible(false);
        }
    }

    public static void showLayers(ArrayList<Overlay> layers) {
        if(layers.size()>0&&!layers.get(0).isVisible())
        for(int i=0;i<layers.size();i++) {
            layers.get(i).setVisible(true);
        }
    }

    public static void removeLayers(ArrayList<Overlay> layers) {
        for(int i=0;i<layers.size();i++) {
            layers.get(i).remove();
        }
    }

}
