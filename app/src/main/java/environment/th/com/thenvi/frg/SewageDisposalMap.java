package environment.th.com.thenvi.frg;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.ChuqinBean;
import environment.th.com.thenvi.bean.CompanyBean;
import environment.th.com.thenvi.bean.GongyeBean;
import environment.th.com.thenvi.bean.PopupInfoItem;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WushuiBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SewageDisposalMap extends BaseFragment implements View.OnClickListener,
        BaiduMap.OnMapClickListener {

    private HttpHandler handler;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private DrawerLayout mDrawerLayout;
    private TextView typeBtn;
    private EditText searchEdt;
    private int type=0; //跨界， 国控， 闸坝
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private List<CompanyBean> CopList=new ArrayList<>();
    private List<CompanyBean> CopFindList=new ArrayList<>();

    private ArrayList<CRiverInfoBean> gkList=new ArrayList<>();
    private ArrayList<CRiverInfoBean> gkFindList=new ArrayList<>();
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
                ArrayList<PopupInfoItem> datalist=null;
                String title="";
                Serializable bean = null;
                switch (type){
                    case 0:
                        CompanyBean RBean=(CompanyBean) markerExtraInfo.getSerializable("InfoBean");
                        bean=RBean;
                        datalist=RBean.getInfos();
                        title=RBean.getPSNAME();
                        break;
                    case 1:
                        WushuiBean WBean=(WushuiBean) markerExtraInfo.getSerializable("InfoBean");
                        bean=WBean;
//                        datalist=WBean.getInfos();
                        title=WBean.getPSNAME();
                        break;
                    case 2:
                        GongyeBean GBean=(GongyeBean) markerExtraInfo.getSerializable("InfoBean");
                        bean=GBean;
//                        datalist=GBean.getInfos();
                        title=GBean.getRUNIT();
                        break;
                    case 3:
                        ChuqinBean CBean=(ChuqinBean) markerExtraInfo.getSerializable("InfoBean");
                        bean=CBean;
//                        datalist=CBean.getInfos();
                        title=CBean.getFARM();
                        break;
                }

                showSupportContent(marker.getPosition(), height, title, bean);
                content.setListView(datalist);
                return true;
            }
        });

        initHandler();
        initView(mView);
        switch (type){
            case 0:
                handler.getYibanSiteList();
                break;
            case 1:
                handler.getWushuizdSiteList();
                break;
            case 2:
                handler.getGongyeSiteList();
                break;
            case 3:
                handler.getChuqinSiteList();
                break;
        }
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
        strings.add("跨界断面");
        strings.add("国控断面");
        strings.add("自动检测站");
        popup = new MenuPopup(getActivity(), strings, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeBtn.setText(strings.get(i));
                type=i;
                popup.dismiss();
                switch (type){
                    case 0:
                        handler.getYibanSiteList();
                        break;
                    case 1:
                        handler.getWushuizdSiteList();
                        break;
                    case 2:
//                        handler.getGateDamSiteList();
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
            switch (type){
                case 0:
                    CompanyBean kjbean=CopFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(kjbean.getY()),Double.valueOf(kjbean.getX())),
                            75, kjbean.getPSNAME(), kjbean);
                    content.setListView(kjbean.getInfos());
                    break;
                case 1:
                    CRiverInfoBean gkbean=gkFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(gkbean.getLATITUDE()),Double.valueOf(gkbean.getLONGITUDE())),
                            75, gkbean.getPNAME(), gkbean);
                    content.setListView(gkbean.getInfos());
                    break;
                case 2:
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
            ArrayList<String> names=new ArrayList<>();
            switch (type){
                case 0:
                    CopList.clear();
                    for (int i=0;i<CopList.size();i++){
                        CompanyBean site=CopList.get(i);
                        if(site.getPSNAME().startsWith(editable.toString())) {
                            CopFindList.add(site);
                            names.add(site.getPSNAME());
                        }
                    }
                    break;
                case 1:
                    gkFindList.clear();
                    for (int i=0;i<gkList.size();i++){
                        CRiverInfoBean site=gkList.get(i);
                        if(site.getPNAME().startsWith(editable.toString())) {
                            gkFindList.add(site);
                            names.add(site.getPNAME());
                        }
                    }
                    break;
            }

            siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
        }
    };

    public void showSupportContent(LatLng endpositon, int height, String title, final Serializable bean) {

        content = new MarkerSupportView(getActivity(), title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baiduMap.hideInfoWindow();
                Intent i=new Intent(getActivity(), ChatsInfoAct.class);
                i.putExtra("ChatData", bean);
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
                if(method.equals(ConstantUtil.method_YibanSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    CopList= JsonUtil.getArrayByJson(jsonData, CompanyBean.class);
                    ArrayList<String> names=new ArrayList<>();
                    for (CompanyBean bean : CopList) {
                        CopFindList.add(bean);
                        names.add(bean.getPSNAME());
                        View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
//                        mMarkerView.setBackgroundResource(R.mipmap.marker_blue_round);
                        TextView nameTxt= (TextView) mMarkerView.findViewById(R.id.nameTxt);
                        nameTxt.setText(bean.getPSNAME());
                        LatLng point = new LatLng(Double.parseDouble(bean.getY()), Double.parseDouble(bean.getX()));
                        Bundle bundle = new Bundle();
                        int dataType = 0;
                        bundle.putInt("mark_type", dataType);
                        bundle.putSerializable("InfoBean", bean);
                        //将标记添加到地图上
                        addMarkerToMap(point, bundle, mMarkerView);
                    }
                    siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
                    siteListview.setOnItemClickListener(itemClickListener);
                    if(CopList.size()>0){
                        CompanyBean bean = CopList.get(CopList.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getY()), Double.parseDouble(bean.getX()));
                        refreshMapStatus(point, 10);
                    }
                }else if(method.equals(ConstantUtil.method_GuokongSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    gkList= JsonUtil.getGuokongSite(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    for (CRiverInfoBean bean : gkList) {
                        gkFindList.add(bean);
                        names.add(bean.getPNAME());
                        View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
//                        mMarkerView.setBackgroundResource(R.mipmap.marker_blue_round);
                        TextView nameTxt= (TextView) mMarkerView.findViewById(R.id.nameTxt);
                        nameTxt.setText(bean.getPNAME());
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
                    if(gkList.size()>0){
                        CRiverInfoBean bean = gkList.get(gkList.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLONGITUDE()));
                        refreshMapStatus(point, 10);
                    }
                }
            }
        });
    }
}