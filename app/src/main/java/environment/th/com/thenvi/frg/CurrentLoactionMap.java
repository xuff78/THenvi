package environment.th.com.thenvi.frg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.MainMenuAct;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.services.BDLocationService;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/3/8.
 */
public class CurrentLoactionMap extends BaseFragment {

    MapView mMapView;
    BaiduMap baiduMap;
    //DrawerLayout中的左侧菜单控件
    private LinearLayout mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
//    private ArrayList<MapAreaInfo> areaInfo=new ArrayList<>();
    private HttpHandler handler;
    private Handler postHandler=new Handler();

    CallBack callBack;

    private void initHandler() {
        callBack=new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, final String jsonData) {
                SharedPreferencesUtil.setString(getActivity(), ConstantUtil.AreaInfo, jsonData);
                ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo(jsonData, "fenqu");
                showAreaSpace(areaInfo);


                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo(jsonData, "duanMian");
                        showAreaSpace(areaInfo, getResources().getColor(R.color.trans));
                    }
                },2000);

                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo3(jsonData, "xian");
                        showWorkingLine(areaInfo, new int[]{0xFFFFD700, 0xFFEE0000, 0xFF436EEE});
                    }
                },4000);

                if(areaInfo.size()>0) {
                    List<LatLng> points=areaInfo.get(areaInfo.size()/2).getPoints();
                    if(points.size()>0) {
                        refreshMapStatus(points.get(0), 10);
                    }
                }
            }
        };
        handler=new HttpHandler(getActivity(), callBack);
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

        String MapData=SharedPreferencesUtil.getString(getActivity(), ConstantUtil.AreaInfo);
        if(!MapData.equals(SharedPreferencesUtil.FAILURE_STRING)){
            callBack.doSuccess(ConstantUtil.method_zidong, MapData);
        }else
            handler.getZidong();
        return mView;
    }

    private void initView(View v) {

    }

    public void showAreaSpace(ArrayList<MapAreaInfo> areaInfo) {
        int color=0;
        int linecolor=getResources().getColor(R.color.bg_coffee);
        for(int i=0;i<areaInfo.size();i++) {
            if(i<7){
                color=0x88FF7F24;
            }else if(i<11){
                color=0x88008B45;
            }else if(i<17){
                color=0x8800C5CD;
            }else if(i<25){
                color=0x88CD3333;
            }else if(i<28){
                color=0x551b93e5;
            }else if(i<32){
                color=0x88CD2990;
            }
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, linecolor))
                    .fillColor(color);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);

//            View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
//            TextView nameTxt = (TextView) mMarkerView.findViewById(R.id.nameTxt);
//            nameTxt.setText(areaInfo.get(i).getNum());
//            LatLng point = infos.get(0);
//            //将标记添加到地图上
//            addMarkerToMap(point, null, mMarkerView);
        }
    }

    public void showAreaSpace(ArrayList<MapAreaInfo> areaInfo, int color) {
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, Color.WHITE))
                    .fillColor(color);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
        }
    }

    public void addMarkerToMap(LatLng point, Bundle bundle, View markerView) {
        try {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markerView);//fromResource(R.mipmap.touxiang2x);
            int height = markerView.getHeight();//获取marker的高度

            //构建MarkerOption，用于在地图上添加Marker  OverlayOptions
            MarkerOptions option = new MarkerOptions()
                    .position(point)
                    .perspective(true)
                    .icon(bitmapDescriptor).zIndex(9);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) (baiduMap.addOverlay(option));
            bundle.putInt("height", height);
            marker.setExtraInfo(bundle);
        } catch (Exception e) {

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
                    .width(8)
                    .color(color);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
//        }
        }
    }

    public void showWorkingLine(ArrayList<MapAreaInfo> areaInfo, int[] color) {
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            PolylineOptions polygonOption = new PolylineOptions()
                    .points(infos)
                    .dottedLine(true)
                    .width(10)
                    .color(color[i%color.length]);
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
