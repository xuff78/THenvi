package environment.th.com.thenvi.frg;

import android.content.Intent;
import android.os.Bundle;
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
    private ArrayList<MapAreaInfo> areaInfo=new ArrayList<>();
    private HttpHandler handler;

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                areaInfo= JsonUtil.getAreaInfo(jsonData);
                showWorkingSpace();

                if(areaInfo.size()>0) {
                    List<LatLng> points=areaInfo.get(0).getPoints();
                    if(points.size()>0) {
                        refreshMapStatus(points.get(0), 14);
                    }
                }
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
        handler.getZidong();
        return mView;
    }

    private void initView(View v) {

    }

    public void showWorkingSpace() {
//        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos=areaInfo.get(3).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(infos.size(), 0xFF818181))
                    .fillColor(0x331b93e5);
            //在地图上添加多边形Option，用于显示
            baiduMap.addOverlay(polygonOption);
//        }
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
