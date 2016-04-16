package environment.th.com.thenvi.frg;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.ChuqinBean;
import environment.th.com.thenvi.bean.Company2Bean;
import environment.th.com.thenvi.bean.CompanyBean;
import environment.th.com.thenvi.bean.GongyeBean;
import environment.th.com.thenvi.bean.PopupInfoItem;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.bean.WushuiBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.LogUtil;
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
    private int type=0; //一般工业企业，污水处理厂，工业企业， 污水处理厂，禽畜养殖
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private float startX = 0;
    private float startY = 0;

    private List<CompanyBean> CopList=new ArrayList<>();
    private List<CompanyBean> CopFindList=new ArrayList<>();
    private List<CompanyBean> WsList=new ArrayList<>();
    private List<CompanyBean> WsFindList=new ArrayList<>();
    private List<GongyeBean> GyList=new ArrayList<>();
    private List<GongyeBean> GyFindList=new ArrayList<>();
    private List<Company2Bean> Ws2List=new ArrayList<>();
    private List<Company2Bean> Ws2FindList=new ArrayList<>();
    private List<ChuqinBean> CqList=new ArrayList<>();
    private List<ChuqinBean> CqFindList=new ArrayList<>();


    private Marker currentMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.frg_water_info_map, null);
//        mCallBack = new MapRequestCallBack(getActivity());
//        mCMHandler = new CMHandler(getActivity(), mCallBack);
        //获取地图控件引用
        TextView titleTxt = (TextView) mView.findViewById(R.id.titleTxt);
        titleTxt.setText("重点风险源");
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
                String title="";
                switch (type){
                    case 0:
                        CompanyBean CpyBean=(CompanyBean) markerExtraInfo.getSerializable("InfoBean");
                        title=CpyBean.getPSNAME();
                        handler.getYibanSiteDetail(CpyBean.getPSCODE());
                        break;
                    case 1:
                        CompanyBean WBean=(CompanyBean) markerExtraInfo.getSerializable("InfoBean");
                        title=WBean.getPSNAME();
                        handler.getWushuizdSiteDetail(WBean.getPSCODE());
                        break;
                    case 2:
                        GongyeBean GBean=(GongyeBean) markerExtraInfo.getSerializable("InfoBean");
                        title=GBean.getRUNIT();
                        handler.getGongyeSiteDetail(GBean.getRUNIT());
                        break;
                    case 3:
                        Company2Bean Cpy2Bean=(Company2Bean) markerExtraInfo.getSerializable("InfoBean");
                        title=Cpy2Bean.getNAME();
                        handler.getWushuipcDetail(Cpy2Bean.getNAME());
                        break;
                    case 4:
                        ChuqinBean CBean=(ChuqinBean) markerExtraInfo.getSerializable("InfoBean");
                        title=CBean.getFARM();
                        handler.getChuqinSiteDetail(CBean.getFARM());
                        break;
                }
                showSupportContent(marker.getPosition(), height, title);
                return true;
            }
        });
        baiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
            @Override
            public void onMapDoubleClick(LatLng latLng) {
                getDataOnScreen();
            }
        });

        baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                //触摸滑动时，根据滑动的距离，来判断是否需要刷新
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getRawX();
                        startY = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //第一种方法，获取两点间距离
                        float endX = motionEvent.getRawX() - startX;
                        float endY = motionEvent.getRawY() - startY;
                        double distance = Math.sqrt(endX * endX + endY * endY);//
                        if (distance > 100) {
                            getDataOnScreen();
                        }
                        break;
                }
            }
        });

        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        initHandler();
        initView(mView);

        handler.getYibanSiteList();
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
        strings.add("一般工业企业");
        strings.add("污水处理厂\n（重点检查）");
        strings.add("工业企业");
        strings.add("污水处理厂\n（污染普查）");
        strings.add("禽畜养殖");
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
                        getDataOnScreen();
                        break;
                    case 3:
                        handler.getWushuipcSiteList();
                        break;
                    case 4:
                        getDataOnScreen();
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
                    CompanyBean bean1=CopFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(bean1.getY()),Double.valueOf(bean1.getX())),
                            75, bean1.getPSNAME());
                    content.setListView(bean1.getInfos());
                    break;
                case 1:
                    CompanyBean bean2=WsFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(bean2.getY()),Double.valueOf(bean2.getX())),
                            75, bean2.getPSNAME());
                    content.setListView(bean2.getInfos());
                    break;
                case 2:
                    GongyeBean bean3=GyFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(bean3.getLATITUDE()),Double.valueOf(bean3.getLONGITUDE())),
                            75, bean3.getRUNIT());
                    content.setListView(bean3.getInfos());
                    break;
                case 3:
                    Company2Bean bean4=Ws2FindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(bean4.getLATITUDE()),Double.valueOf(bean4.getLODEGREE())),
                            75, bean4.getNAME());
                    content.setListView(bean4.getInfos());
                    break;
                case 4:
                    ChuqinBean bean5=CqFindList.get(i);
                    showSupportContent(new LatLng(Double.valueOf(bean5.getLATITUDE()),Double.valueOf(bean5.getLODEGREE())),
                            75, bean5.getFARM());
                    content.setListView(bean5.getInfos());
                    break;
            }
            String title="";
            LatLng ll=null;
            switch (type){
                case 0:
                    CompanyBean CpyBean=CopFindList.get(i);
                    title=CpyBean.getPSNAME();
                    ll=new LatLng(Double.valueOf(CpyBean.getY()),Double.valueOf(CpyBean.getX()));
                    handler.getYibanSiteDetail(CpyBean.getPSCODE());
                    break;
                case 1:
                    CompanyBean WBean=WsFindList.get(i);
                    title=WBean.getPSNAME();
                    ll=new LatLng(Double.valueOf(WBean.getY()),Double.valueOf(WBean.getX()));
                    handler.getWushuizdSiteDetail(WBean.getPSCODE());
                    break;
                case 2:
                    GongyeBean GBean=GyFindList.get(i);
                    title=GBean.getRUNIT();
                    ll=new LatLng(Double.valueOf(GBean.getLATITUDE()),Double.valueOf(GBean.getLONGITUDE()));
                    handler.getGongyeSiteDetail(GBean.getRUNIT());
                    break;
                case 3:
                    Company2Bean Cpy2Bean=Ws2FindList.get(i);
                    title=Cpy2Bean.getNAME();
                    ll=new LatLng(Double.valueOf(Cpy2Bean.getLATITUDE()),Double.valueOf(Cpy2Bean.getLODEGREE()));
                    handler.getWushuipcDetail(Cpy2Bean.getNAME());
                    break;
                case 4:
                    ChuqinBean CBean=CqFindList.get(i);
                    title=CBean.getFARM();
                    ll=new LatLng(Double.valueOf(CBean.getLATITUDE()),Double.valueOf(CBean.getLODEGREE()));
                    handler.getChuqinSiteDetail(CBean.getFARM());
                    break;
            }
            showSupportContent(ll, 75, title);
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
                    CopFindList.clear();
                    for (int i=0;i<CopList.size();i++){
                        CompanyBean site=CopList.get(i);
                        if(site.getPSNAME().startsWith(editable.toString())) {
                            CopFindList.add(site);
                            names.add(site.getPSNAME());
                        }
                    }
                    break;
                case 1:
                    WsFindList.clear();
                    for (int i=0;i<WsList.size();i++){
                        CompanyBean site=WsList.get(i);
                        if(site.getPSNAME().startsWith(editable.toString())) {
                            WsFindList.add(site);
                            names.add(site.getPSNAME());
                        }
                    }
                    break;
                case 2:
                    GyFindList.clear();
                    for (int i=0;i<GyList.size();i++){
                        GongyeBean site=GyList.get(i);
                        if(site.getRUNIT().startsWith(editable.toString())) {
                            GyFindList.add(site);
                            names.add(site.getRUNIT());
                        }
                    }
                    break;
                case 3:
                    Ws2FindList.clear();
                    for (int i=0;i<Ws2List.size();i++){
                        Company2Bean site=Ws2List.get(i);
                        if(site.getNAME().startsWith(editable.toString())) {
                            Ws2FindList.add(site);
                            names.add(site.getNAME());
                        }
                    }
                    break;
                case 4:
                    CqFindList.clear();
                    for (int i=0;i<CqList.size();i++){
                        ChuqinBean site=CqList.get(i);
                        if(site.getFARM().startsWith(editable.toString())) {
                            CqFindList.add(site);
                            names.add(site.getFARM());
                        }
                    }
                    break;
            }

            siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
        }
    };

    public void showSupportContent(LatLng endpositon, int height, String title) {

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
                    CopList= JsonUtil.getCompanyList(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    CopFindList.clear();
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    iconImg.setImageResource(R.mipmap.icon_yibanqiye);
                    for (CompanyBean bean : CopList) {
                        CopFindList.add(bean);
                        names.add(bean.getPSNAME());
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
                }else if(method.equals(ConstantUtil.method_WushuizdSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    WsList= JsonUtil.getCompanyList(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    WsFindList.clear();
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    iconImg.setImageResource(R.mipmap.icon_wushuichuli);
                    for (CompanyBean bean : WsList) {
                        WsFindList.add(bean);
                        names.add(bean.getPSNAME());
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
                    if(WsList.size()>0){
                        CompanyBean bean = WsList.get(WsList.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getY()), Double.parseDouble(bean.getX()));
                        refreshMapStatus(point, 10);
                    }
                }else if(method.equals(ConstantUtil.method_GongyeSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    GyList= JsonUtil.getGongyeList(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    GyFindList.clear();
                    int showNum=GyList.size();
                    if(showNum>40)
                        showNum=40;
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    iconImg.setImageResource(R.mipmap.icon_yibanqiye);
                    for(int i=0;i<showNum;i++){
                        GongyeBean bean=GyList.get(i);
                        GyFindList.add(bean);
                        names.add(bean.getRUNIT());
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
//                    if(GyList.size()>0){
//                        GongyeBean bean = GyList.get(GyList.size()/2);
//                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLONGITUDE()));
//                        refreshMapStatus(point, 10);
//                    }
                }else if(method.equals(ConstantUtil.method_WushuipcSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    Ws2List= JsonUtil.getCompany2List(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    Ws2FindList.clear();
                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    iconImg.setImageResource(R.mipmap.icon_wushuichuli);
                    for (Company2Bean bean : Ws2List) {
                        Ws2FindList.add(bean);
                        names.add(bean.getNAME());
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
                    if(Ws2List.size()>0){
                        Company2Bean bean = Ws2List.get(Ws2List.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLODEGREE()));
                        refreshMapStatus(point, 10);
                    }
                }else if(method.equals(ConstantUtil.method_ChuqinSiteList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    CqList= JsonUtil.getChuqinList(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    CqFindList.clear();
                    int showNum=CqList.size();
                    if(showNum>40)
                        showNum=40;

                    View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
                    ImageView iconImg= (ImageView) mMarkerView.findViewById(R.id.iconImg);
                    iconImg.setImageResource(R.mipmap.icon_chuxu);
                    for(int i=0;i<showNum;i++){
                        ChuqinBean bean=CqList.get(i);
                        CqFindList.add(bean);
                        names.add(bean.getFARM());
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
//                    if(CqList.size()>0){
//                        ChuqinBean bean = CqList.get(CqList.size()/2);
//                        LatLng point = new LatLng(Double.parseDouble(bean.getLATITUDE()), Double.parseDouble(bean.getLODEGREE()));
//                        refreshMapStatus(point, 10);
//                    }
                }else if(method.equals(ConstantUtil.method_YibanSiteDetail)||method.equals(ConstantUtil.method_WushuizdSiteDetail)){
                    CompanyBean siteBean=JsonUtil.getCompanyDetail(jsonData);
                    content.setListView(siteBean.getInfos());
                }else if(method.equals(ConstantUtil.method_GongyeSiteDetail)){
                    GongyeBean siteBean=JsonUtil.getGongyeDetail(jsonData);
                    content.setListView(siteBean.getInfos());
                }else if(method.equals(ConstantUtil.method_WushuipcSiteDetail)){
                    Company2Bean siteBean=JsonUtil.getCompany2Detail(jsonData);
                    content.setListView(siteBean.getInfos());
                }else if(method.equals(ConstantUtil.method_ChuqinSiteDetail)){
                    ChuqinBean siteBean=JsonUtil.getChuqinDetail(jsonData);
                    content.setListView(siteBean.getInfos());
                }
            }
        });
    }

    public void getDataOnScreen() {
        int[] location = new int[2];
        mMapView.getLocationOnScreen(location);
        int height = mMapView.getHeight();
        int width = mMapView.getWidth();
        Point viewLeftTop = new Point(0, location[1]);
        Point viewRightTop = new Point(width, location[1]);
        Point viewLeftBottom = new Point(0, location[1] + height);
        Point viewRightBottom = new Point(width, location[1] + height);
        LatLng pointLeftTop = baiduMap.getProjection().fromScreenLocation(viewLeftTop);
        LatLng pointRightTop = baiduMap.getProjection().fromScreenLocation(viewRightTop);
        LatLng pointLeftBottom = baiduMap.getProjection().fromScreenLocation(viewLeftBottom);
        LatLng pointRightBottom = baiduMap.getProjection().fromScreenLocation(viewRightBottom);
        LogUtil.d("HttpAsyncTask", " width=" + location[0] + ",height=" + location[1]);
        if (type == 2) {
            //根据屏幕所显示的范围请求数据
            String fourCoords=pointLeftTop.longitude+","+pointLeftTop.latitude+";"+pointRightTop.longitude+","+pointRightTop.latitude
                    +";"+pointLeftBottom.longitude+","+pointLeftBottom.latitude+";"+pointRightBottom.longitude+","+pointRightBottom.latitude;
            handler.getGongyeSiteList(fourCoords);
        } else if (type == 4) {
            String fourCoords=pointLeftTop.longitude+","+pointLeftTop.latitude+";"+pointRightTop.longitude+","+pointRightTop.latitude
                    +";"+pointLeftBottom.longitude+","+pointLeftBottom.latitude+";"+pointRightBottom.longitude+","+pointRightBottom.latitude;
            handler.getChuqinSiteList(fourCoords);
        }

    }
}
