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
import com.baidu.mapapi.map.Overlay;
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
import environment.th.com.thenvi.bean.MapPointInfo;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.services.BDLocationService;
import environment.th.com.thenvi.utils.ActUtil;
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
    private ArrayList<MapPointInfo> areaInfo=new ArrayList<>();
    private HttpHandler handler;
    private Handler postHandler=new Handler();

    CallBack callBack;

    private void initHandler() {
        callBack=new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, final String jsonData) {
                SharedPreferencesUtil.setString(getActivity(), ConstantUtil.AreaInfo, jsonData);
                ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo(jsonData, "fenqu");
                ActUtil.showAreaSpace(getActivity(), baiduMap, areaInfo);

                ArrayList<MapAreaInfo> areaInfo2 = JsonUtil.getAreaInfo(jsonData, "duanMian");
                ActUtil.showWorkingLine(baiduMap, areaInfo2);

                postHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getActivity()!=null) {
                            ArrayList<MapAreaInfo> areaInfo = JsonUtil.getAreaInfo3(jsonData, "xian");
                            showWorkingLine(areaInfo, new int[]{0xFFFFD700, 0xFFEE0000, 0xFF436EEE});
                        }
                    }
                },2000);

                if(areaInfo.size()>0) {
                    List<LatLng> points=areaInfo.get(areaInfo.size()/2).getPoints();
                    if(points.size()>0) {
                        refreshMapStatus(points.get(0), 11);
                    }
                }

                for(int i=0;i<CurrentLoactionMap.this.areaInfo.size();i++) {
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
                    mMarkerView.setBackgroundResource(0);
                    MapPointInfo point = CurrentLoactionMap.this.areaInfo.get(i);
                    TextView nameTxt = (TextView) mMarkerView.findViewById(R.id.nameTxt);
                    nameTxt.setText(point.getNum());
                    if(i<6)
                        nameTxt.setTextSize(26);
                    else
                        nameTxt.setTextSize(10);
                    //将标记添加到地图上
                    addMarkerToMap(point.getLatLng(), null, mMarkerView, 0);
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

        initPoint();

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

    int[] indexz={-30, -30, -30, 30, 30, 30};

    public void showAreaSpace(ArrayList<MapAreaInfo> areaInfo) {
        int color=0;
        int linecolor=getResources().getColor(R.color.bg_coffee);
        for(int i=0;i<areaInfo.size();i++) {
            if(i==0){
                color=0x66FF7F24;
            }else if(i==1){
                color=0x66008B45;
            }else if(i==2){
                color=0x6600C5CD;
            }else if(i==3){
                color=0x66CD3333;
            }else if(i==4){
                color=0x661b93e5;
            }else if(i==5){
                color=0x66CD2990;
            }
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(3, linecolor))
                    .fillColor(color);
            //在地图上添加多边形Option，用于显示
            Overlay ol=baiduMap.addOverlay(polygonOption);


        }
    }

    public void showAreaSpace(ArrayList<MapAreaInfo> areaInfo, int color) {
        for(int i=0;i<areaInfo.size();i++) {
            List<LatLng> infos = areaInfo.get(i).getPoints();
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(infos)
                    .stroke(new Stroke(2, 0x66ffffff))
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

    public void addMarkerToMap(LatLng point, Bundle bundle, View markerView, int index) {
        try {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markerView);//fromResource(R.mipmap.touxiang2x);
            int height = markerView.getHeight();//获取marker的高度

            //构建MarkerOption，用于在地图上添加Marker  OverlayOptions
            MarkerOptions option = new MarkerOptions()
                    .position(point)
                    .perspective(true)
                    .icon(bitmapDescriptor);
//                    .zIndex(index);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) (baiduMap.addOverlay(option));
            bundle.putInt("height", height);
            marker.setExtraInfo(bundle);
        } catch (Exception e) {

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

    private void initPoint() {
        areaInfo.add(new MapPointInfo("①", 121.01955045100,	31.38602669180));
        areaInfo.add(new MapPointInfo("②", 120.649934629,31.0187802021));
        areaInfo.add(new MapPointInfo("③", 120.32059745500,	30.68944302740));
        areaInfo.add(new MapPointInfo("④", 120.87265185500,	30.77947765070));
        areaInfo.add(new MapPointInfo("⑤", 121.19725036600,	30.87188160620));
        areaInfo.add(new MapPointInfo("⑥", 121.05272110200,	31.09459883220));


        areaInfo.add(new MapPointInfo("13#", 120.17606819100,30.73682967120));
        areaInfo.add(new MapPointInfo("14#", 120.17369885900,30.57571508220));
        areaInfo.add(new MapPointInfo("11#", 120.24477882500,30.89320559590));
        areaInfo.add(new MapPointInfo("12#", 120.37509209500,30.78658564730));
        areaInfo.add(new MapPointInfo("16#", 120.51725202700,	30.65627237670));
        areaInfo.add(new MapPointInfo("15#", 120.38456942400,	30.54017509930));
        areaInfo.add(new MapPointInfo("10#", 120.50755257300,	30.89672257340));
        areaInfo.add(new MapPointInfo("18#", 120.74033946100,	30.85940559130));
        areaInfo.add(new MapPointInfo("19#", 120.75277845500,	30.76877863500));
        areaInfo.add(new MapPointInfo("17#", 120.60173352800,	30.77410963240));

        areaInfo.add(new MapPointInfo("20#", 120.90619271400,	30.7231689903));
        areaInfo.add(new MapPointInfo("22#", 120.93758636600,	30.83985860080));
        areaInfo.add(new MapPointInfo("21#", 120.89849238500,	30.95003254770));
        areaInfo.add(new MapPointInfo("9#", 120.72790046700,	31.00334252200));
        areaInfo.add(new MapPointInfo("28#", 120.99978133600,	31.07975348520));
        areaInfo.add(new MapPointInfo("27#", 121.06553030400,	30.90738456820));
        areaInfo.add(new MapPointInfo("26#", 121.18992024400,	30.83630460250));
        areaInfo.add(new MapPointInfo("23#", 121.10640128400,	30.76344763760));
        areaInfo.add(new MapPointInfo("24#", 121.11706327900,	30.66926668300));
        areaInfo.add(new MapPointInfo("25#", 121.33208017600,	30.75278564270));

        areaInfo.add(new MapPointInfo("5#", 121.01932832600,	31.21658241930));
        areaInfo.add(new MapPointInfo("6#", 120.92514737200,	31.21835941840));
        areaInfo.add(new MapPointInfo("8#", 120.67814449100,	31.13839445690));
        areaInfo.add(new MapPointInfo("7#", 120.78476443900,	31.27877738930));
        areaInfo.add(new MapPointInfo("29#", 121.09218529100,	31.20414342520));
        areaInfo.add(new MapPointInfo("30#", 121.17570425100,	31.32320236790));
        areaInfo.add(new MapPointInfo("1#", 121.11647094600,	31.61699955970));
        areaInfo.add(new MapPointInfo("2#", 121.12594827500,	31.48668628910));
        areaInfo.add(new MapPointInfo("31", 121.23256822400,	31.44403830970));

        areaInfo.add(new MapPointInfo("3#", 121.07856163100,	31.33978769320));
        areaInfo.add(new MapPointInfo("4#", 120.93640170000,	31.40139033020));
    }

}
