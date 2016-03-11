package environment.th.com.thenvi.frg;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import environment.th.com.thenvi.R;

/**
 * Created by Administrator on 2016/3/9.
 */
public class WaterInfoMap extends BaseFragment implements View.OnClickListener{

    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.frg_water_info_map, null);
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


        initView(mView);
        return mView;
    }

    private void initView(View v) {
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);

        v.findViewById(R.id.listLeftBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(mDrawerLayout.isDrawerOpen(menuLayout))
            mDrawerLayout.closeDrawer(menuLayout);
        else
            mDrawerLayout.openDrawer(menuLayout);
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
