package environment.th.com.thenvi.frg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.activity.MainMenuAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.bean.PopupInfoItem;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ActUtil;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.utils.SharedPreferencesUtil;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by Administrator on 2016/3/9.
 */
public class WaterInfoMap extends BaseFragment implements View.OnClickListener,
        BaiduMap.OnMapClickListener, BaiduMap.OnMapStatusChangeListener {

    private HttpHandler handler;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private DrawerLayout mDrawerLayout;
    private TextView typeBtn;
    private EditText searchEdt;
    private int type=0; //水文， 雨量， 闸坝
    private int tmptype=0; //水文， 雨量， 闸坝
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private ArrayList<WaterSiteBean> siteList=new ArrayList<>();
    private ArrayList<WaterSiteBean> findList=new ArrayList<>();
    private ArrayList<Overlay> maplayers=new ArrayList<>();
    private ArrayList<Overlay> sitelayers=new ArrayList<>();
    private Marker currentMarker;

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

        baiduMap.setOnMapClickListener(this);
        baiduMap.setMyLocationEnabled(true);
        //普通地图
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        baiduMap.setOnMapStatusChangeListener(this);
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
                WaterSiteBean bean = (WaterSiteBean) markerExtraInfo.getSerializable("InfoBean");
                showSupportContent(marker.getPosition(), height, bean.getHSNAME(), bean);
                content.setListView(bean.getInfos());
                switch (type){
                    case 0:
                        handler.getSiteDetail(bean.getHSNAME(), bean.getRSNAME());
                        break;
                    case 1:
                        handler.getRainSiteDetail(bean.getHSNAME(), bean.getRSNAME());
                        break;
                    case 2:
                        handler.getGateDamDetail(bean.getHSNAME(), bean.getRSNAME());
                        break;
                }
                return true;
            }
        });
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        initHandler();
        initView(mView);
        switch (type){
            case 0:
                handler.getSiteList();
                break;
            case 1:
                handler.getRainSiteList();
                break;
            case 2:
                handler.getGateDamSiteList();
                break;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                maplayers=ActUtil.getCurrentMap(getActivity(), baiduMap);
            }
        },1500);

        return mView;
    }

    private void initView(View v) {
        searchEdt = (EditText) v.findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(txtWatcher);
        siteListview = (ListView) v.findViewById(R.id.siteList);
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);
        typeBtn=(TextView)v.findViewById(R.id.typeBtn);
        typeBtn.setOnClickListener(this);
        v.findViewById(R.id.listLeftBtn).setOnClickListener(this);
        final ArrayList<String> strings=new ArrayList<>();
        strings.add("水文站");
        strings.add("雨量站");
        strings.add("闸坝");
        popup = new MenuPopup(getActivity(), strings, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeBtn.setText(strings.get(i));
                tmptype=i;
                popup.dismiss();
                switch (i){
                    case 0:
                        handler.getSiteList();
                        break;
                    case 1:
                        handler.getRainSiteList();
                        break;
                    case 2:
                        handler.getGateDamSiteList();
                        break;
                }
            }
        });
        typeBtn.setText(strings.get(0));
    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mDrawerLayout.closeDrawer(menuLayout);
            WaterSiteBean site= findList.get(i);
            showSupportContent(new LatLng(Double.valueOf(site.getLATITUDE()),Double.valueOf(site.getLONGITUDE())), 75, site.getHSNAME(), site);
            content.setListView(site.getInfos());
            switch (type){
                case 0:
                    handler.getSiteDetail(site.getHSNAME(), site.getRSNAME());
                    break;
                case 1:
                    handler.getRainSiteDetail(site.getHSNAME(), site.getRSNAME());
                    break;
                case 2:
                    handler.getGateDamDetail(site.getHSNAME(), site.getRSNAME());
                    break;
            }
        }
    };

    TextWatcher txtWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            findList.clear();
            ArrayList<String> names=new ArrayList<>();
            for (int i=0;i<siteList.size();i++){
                WaterSiteBean site=siteList.get(i);
                if(site.getHSNAME().startsWith(editable.toString())) {
                    findList.add(site);
                    names.add(site.getHSNAME());
                }
            }
            siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
        }
    };

    public void showSupportContent(LatLng endpositon, int height, String title, final WaterSiteBean bean) {

        content = new MarkerSupportView(getActivity(), title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baiduMap.hideInfoWindow();
                Intent i=new Intent(getActivity(), ChatsInfoAct.class);
                switch (type) {
                    case 0:
                        i.putExtra(ChatsInfoAct.WaterSite, bean);
                        break;
                    case 1:
                        i.putExtra(ChatsInfoAct.RainSite, bean);
                        break;
                    case 2:
                        i.putExtra(ChatsInfoAct.GateDamSite, bean);
                        break;
                }
                i.putExtra("Title", bean.getHSNAME());
                startActivity(i);
            }
        });
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
        ActUtil.hideLayers(maplayers);

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
            sitelayers.add(marker);
        }catch (Exception e){

        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        baiduMap.hideInfoWindow();
        mInfoWindow = null;
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.listLeftBtn:
                if(mDrawerLayout.isDrawerOpen(menuLayout))
                    mDrawerLayout.closeDrawer(menuLayout);
                else
                    mDrawerLayout.openDrawer(menuLayout);
                break;
            case R.id.typeBtn:
                popup.showPopupWindow(view);
                break;
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
        baiduMap.clear();
        mMapView.onDestroy();
        super.onDestroy();
    }

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                if(method.equals(ConstantUtil.method_SiteList)||method.equals(ConstantUtil.method_RainSiteList)
                        ||method.equals(ConstantUtil.method_GateDamSiteList)){
                    type=tmptype;
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    siteList= JsonUtil.getSiteList(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    findList.clear();
                    ActUtil.removeLayers(sitelayers);
                    sitelayers.clear();
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    if(method.equals(ConstantUtil.method_SiteList))
                        iconImg.setImageResource(R.mipmap.icon_shuiwen);
                    else if(method.equals(ConstantUtil.method_RainSiteList))
                        iconImg.setImageResource(R.mipmap.icon_yuliang);
                    else if(method.equals(ConstantUtil.method_GateDamSiteList))
                        iconImg.setImageResource(R.mipmap.icon_zaba);
                    for (WaterSiteBean bean : siteList) {
                        findList.add(bean);
                        names.add(bean.getHSNAME());
                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLONGITUDE()));
                        Bundle bundle = new Bundle();
                        int dataType = 0;
                        bundle.putInt("mark_type", dataType);
                        bundle.putSerializable("InfoBean", bean);
                        //将标记添加到地图上
                        addMarkerToMap(point, bundle, mMarkerView);
                    }
                    siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
                    siteListview.setOnItemClickListener(itemClickListener);
                    if(siteList.size()>0){
                        WaterSiteBean bean = siteList.get(siteList.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLONGITUDE()));
                        refreshMapStatus(point, 10);
                    }
                }else if(method.equals(ConstantUtil.method_SiteDetail)||method.equals(ConstantUtil.method_RainSiteDetail)
                        ||method.equals(ConstantUtil.method_GateDamDetail)){
                    WaterSiteBean siteBean=JsonUtil.getSiteDetail(jsonData);
                    if(siteBean.getInfos().size()>0)
                        content.setListView(siteBean.getInfos());
                }
            }
        });
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        ActUtil.changeLayerStatus(maplayers, mapStatus);
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
    }
}
