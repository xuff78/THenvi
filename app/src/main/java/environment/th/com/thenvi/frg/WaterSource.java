package environment.th.com.thenvi.frg;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/14.
 */
public class WaterSource extends BaseFragment {

    MapView mMapView;
    BaiduMap baiduMap;
    //DrawerLayout中的左侧菜单控件
    private LinearLayout mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //    private ArrayList<MapAreaInfo> areaInfo=new ArrayList<>();
    private HttpHandler handler;
    private Handler postHandler=new Handler();

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, final String jsonData) {
                MapAreaInfo areaInfo1 = JsonUtil.getAreaInfo2(jsonData, "luyu3");
                MapAreaInfo areaInfo2 = JsonUtil.getAreaInfo2(jsonData, "shuiyuandi3");
                JsonUtil.getWaterSourceInfo(jsonData);
//                showAreaSpace(areaInfo);//0xcc1b93e5);
//
//
//                postHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo(jsonData, "fengqu");
//                        showWorkingSpace(areaInfo, 0x99ff0000);
//                    }
//                },2000);
//
//                postHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo(jsonData, "xian");
//                        showWorkingLine(areaInfo, 0x99cc0000);
//                    }
//                },4000);
//
//                if(areaInfo.size()>0) {
//                    List<LatLng> points=areaInfo.get(areaInfo.size()/2).getPoints();
//                    if(points.size()>0) {
//                        refreshMapStatus(points.get(0), 10);
//                    }
//                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.frg_current_location_map, null);
//        mCallBack = new MapRequestCallBack(getActivity());
//        mCMHandler = new CMHandler(getActivity(), mCallBack);
        //获取地图控件引用
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(true);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //普通地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        initView(mView);

//        LatLng current_point=new LatLng(39.919209, 116.368666);
//        refreshMapStatus(current_point, 16);
        initHandler();
        handler.yinshuiyuan();
        return mView;
    }

    private void initView(View v) {

    }

    int colors[]={};
    public void showAreaSpace(ArrayList<MapAreaInfo> areaInfo) {
        int color=0;
        for(int i=0;i<areaInfo.size();i++) {
            if(i<6){
                color=0x88FF7F24;
            }else if(i<10){
                color=0x88008B45;
            }else if(i<16){
                color=0x8800C5CD;
            }else if(i<24){
                color=0x88CD3333;
            }else if(i<27){
                color=0x551b93e5;
            }else if(i<31){
                color=0x88CD2990;
            }
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, Color.WHITE))
                    .fillColor(color);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
//        }
        }
    }

    public void showWorkingSpace(ArrayList<MapAreaInfo> areaInfo, int color) {
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, color))
                    .fillColor(0x551b93e5);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
//        }
        }
    }

    public void showWorkingLine(ArrayList<MapAreaInfo> areaInfo, int color) {
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            PolylineOptions polygonOption = new PolylineOptions()
                    .points(infos)
                    .zIndex(3)
                    .color(color);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
//        }
        }
    }

    /**
     * 更新地图状态，并定义中心点
     *
     * @param centerPoint 要作为中心点的坐标
     * @param zoomLevel   地图缩放级别
     */
    public void refreshMapStatus(LatLng centerPoint, int zoomLevel) {
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(centerPoint)
                .zoom(zoomLevel)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

}
