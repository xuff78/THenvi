package environment.th.com.thenvi.frg;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.bean.WaterSourceBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by 可爱的蘑菇 on 2016/4/14.
 */
public class WaterSource extends BaseFragment implements BaiduMap.OnMapClickListener{

    MapView mMapView;
    BaiduMap baiduMap;
    //DrawerLayout中的左侧菜单控件
    private LinearLayout mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //    private ArrayList<MapAreaInfo> areaInfo=new ArrayList<>();
    private HttpHandler handler;
    private Handler postHandler=new Handler();
    private ArrayList<WaterSourceBean> wsb=new ArrayList<>();
    private ArrayList<WaterSourceBean> findList=new ArrayList<>();
    private EditText searchEdt;
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private LinearLayout menuLayout;
    private Marker currentMarker;

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, final String jsonData) {

                baiduMap.hideInfoWindow();
                mInfoWindow = null;
                baiduMap.clear();


                wsb=JsonUtil.getWaterSourceInfo(jsonData);
                ArrayList<String> names=new ArrayList<>();
                for (WaterSourceBean bean : wsb) {
                    findList.add(bean);
                    names.add(bean.getWWORKS());
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
//                        mMarkerView.setBackgroundResource(R.mipmap.marker_blue_round);
                    ImageView nameTxt= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    nameTxt.setImageResource(R.mipmap.icon_water);
                    LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLODEGREE()));
                    Bundle bundle = new Bundle();
                    int dataType = 0;
                    bundle.putInt("mark_type", dataType);
                    bundle.putSerializable("InfoBean", bean);
                    //将标记添加到地图上
                    addMarkerToMap(point, bundle, mMarkerView);
                }
                siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
                siteListview.setOnItemClickListener(itemClickListener);
                if(wsb.size()>0){
                    WaterSourceBean bean = wsb.get(wsb.size()/2);
                    LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLODEGREE()));
                    refreshMapStatus(point, 10);
                }



                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity()!=null) {
                            ArrayList<MapAreaInfo> areaInfo2 = JsonUtil.getAreaInfo2(jsonData, "shuiyuandi3");
                            showWorkingSpace(areaInfo2, 0x9900B2EE);
                        }
                    }
                },2000);

                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity()!=null) {
                            ArrayList<MapAreaInfo> areaInfo1 = JsonUtil.getAreaInfo2(jsonData, "luyu3");
                            showWorkingSpace(areaInfo1, 0xcc1b93e5);
//                            refreshMapStatus(areaInfo1.get(0).getPoints().get(0), 10);
                        }
                    }
                },4000);

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
        baiduMap.setOnMapClickListener(this);
        //普通地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                currentMarker = marker;
                Bundle markerExtraInfo = marker.getExtraInfo();
                int height = 0;
                if (markerExtraInfo != null) {
                    height = markerExtraInfo.getInt("height");
                    height += 5;
                }
                WaterSourceBean bean = (WaterSourceBean) markerExtraInfo.getSerializable("InfoBean");
                showSupportContent(marker.getPosition(), height, bean.getWWORKS(), bean);
                content.setListView(bean.getInfos());
                return true;
            }
        });
        initView(mView);

//        LatLng current_point=new LatLng(39.919209, 116.368666);
//        refreshMapStatus(current_point, 16);
        initHandler();
        handler.yinshuiyuan();
        return mView;
    }

    private void initView(View v) {
        TextView titleTxt = (TextView) v.findViewById(R.id.titleTxt);
        titleTxt.setText("饮水源");
        searchEdt = (EditText) v.findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(txtWatcher);
        siteListview = (ListView) v.findViewById(R.id.siteList);
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);
        v.findViewById(R.id.listLeftBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(menuLayout))
                    mDrawerLayout.closeDrawer(menuLayout);
                else
                    mDrawerLayout.openDrawer(menuLayout);
            }
        });
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
//            if(infos.size()>=3) {
//                OverlayOptions polygonOption = new PolygonOptions()
//                        .points(infos)
//                        .stroke(new Stroke(3, color))
//                        .fillColor(0x551b93e5);
//                //在地图上添加多边形Option，用于显示
//                baiduMap.addOverlay(polygonOption);
//            }else
            if(infos.size()>=2) {
                PolylineOptions polygonOption = new PolylineOptions()
                        .points(infos)
                        .zIndex(3)
                        .color(color);
                //在地图上添加多边形Option，用于显示
                baiduMap.addOverlay(polygonOption);
            }
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

    TextWatcher txtWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            ArrayList<String> names=new ArrayList<>();
            findList.clear();
            for (int i=0;i<findList.size();i++){
                WaterSourceBean site=findList.get(i);
                if(site.getWWORKS().startsWith(editable.toString())) {
                    findList.add(site);
                    names.add(site.getWWORKS());
                }
            }
            siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
        }
    };

    public void showSupportContent(LatLng endpositon, int height, String title, final Serializable bean) {

        content = new MarkerSupportView(getActivity(), title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baiduMap.hideInfoWindow();

            }
        });
        content.setDetailGone();
        View view = content.getMarkerContentView();
        mInfoWindow = new InfoWindow(view, endpositon, -height);
        //显示InfoWindow
        baiduMap.showInfoWindow(mInfoWindow);
        Point p=new Point();
        p.set(ScreenUtil.getScreenWidth(getActivity())/2, ScreenUtil.getScreenHeight(getActivity())/5*3);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(endpositon)
                .targetScreen(p)
                .zoom(ConstantUtil.Zoom)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);

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
        }catch (Exception e){

        }
    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mDrawerLayout.closeDrawer(menuLayout);

            WaterSourceBean kjbean=findList.get(i);
            showSupportContent(new LatLng(Double.valueOf(kjbean.getLATITUDE()),Double.valueOf(kjbean.getLODEGREE())),
                    75, kjbean.getWWORKS(), kjbean);
            content.setListView(kjbean.getInfos());
        }
    };

    @Override
    public void onMapClick(LatLng latLng) {
        baiduMap.hideInfoWindow();
        mInfoWindow = null;
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

}
