package environment.th.com.thenvi.frg;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.activity.MainMenuAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by 可爱的蘑菇 on 2016/4/4.
 */
public class WaterQualityMap extends BaseFragment implements View.OnClickListener,
        BaiduMap.OnMapClickListener {

    private HttpHandler handler;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private DrawerLayout mDrawerLayout;
    private TextView typeBtn, startDate;
    private EditText searchEdt;
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private ArrayList<WaterQualityBean> siteList=new ArrayList<>();
    private ArrayList<WaterQualityBean> findList=new ArrayList<>();
    private ArrayList<TextView> labelTxts=new ArrayList<>();
    private Marker currentMarker;
    private String materialType="", queryDate="";
    private DatePickerDialog datePickerDialog;
    private String[] AMMONIA={">2.0","1.5-2.0","1.0-1.5","0.5-1.0","0.15-0.5"};
    private String[] BOD={">10","6-10","4-6","3-4","0-3"};
    private String[] COD={">40","30-40","20-30","15-20","0-15"};
    private String[] DISSOLVED_OXYGEN={"0-2","2-3","3-5","5-6","6-7.5",">7.5"};
    private String[] NITRATE={">2.0","1.5-2.0","1.0-1.5","0.5-1","0.2-0.5"};
    private String[] TOTAL_PHOSPHORUS={">0.4","0.3-0.4","0.2-0.3","0.1-0.2","0.02-0.1"};
    private View iconL1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.water_quality_layout, null);
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
                WaterQualityBean bean = (WaterQualityBean) markerExtraInfo.getSerializable("InfoBean");
                showSupportContent(marker.getPosition(), height, bean.getNAME(), bean);
                content.setListView(bean.getInfos());
                return true;
            }
        });
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        initHandler();
        initView(mView);

        handler.getShuizhiInfo("AMMONIA","2011-01-01");
        materialType="AMMONIA";
        startDate.setText("2011-01-01");
        return mView;
    }

    private void initView(View v) {
        iconL1=v.findViewById(R.id.iconL1);
        labelTxts.add((TextView)v.findViewById(R.id.ll5));
        labelTxts.add((TextView)v.findViewById(R.id.l5));
        labelTxts.add((TextView)v.findViewById(R.id.l4));
        labelTxts.add((TextView)v.findViewById(R.id.l3));
        labelTxts.add((TextView)v.findViewById(R.id.l2));
        labelTxts.add((TextView)v.findViewById(R.id.l1));
        searchEdt = (EditText) v.findViewById(R.id.searchEdt);
        searchEdt.addTextChangedListener(txtWatcher);
        startDate = (TextView)v.findViewById(R.id.startDate);
        startDate.setOnClickListener(this);
        v.findViewById(R.id.findOut).setOnClickListener(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        startDate.setText(str);
        v.findViewById(R.id.findTypeBtn).setOnClickListener(this);
        siteListview = (ListView) v.findViewById(R.id.siteList);
        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);
        typeBtn=(TextView)v.findViewById(R.id.typeBtn);
        typeBtn.setOnClickListener(this);
        v.findViewById(R.id.listLeftBtn).setOnClickListener(this);
        final ArrayList<String> strings=new ArrayList<>();
        strings.add("AMMONIA");
        strings.add("BOD");
        strings.add("COD");
        strings.add("DISSOLVED_OXYGEN");
        strings.add("NITRATE");
        strings.add("TOTAL_PHOSPHORUS");
        popup = new MenuPopup(getActivity(), strings, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeBtn.setText(strings.get(i));
                popup.dismiss();
                String[] labelStrings = new String[0];
                switch (i){
                    case 0:
                        labelStrings=AMMONIA;
                        materialType="AMMONIA";
                        break;
                    case 1:
                        labelStrings=BOD;
                        materialType="BOD";
                        break;
                    case 2:
                        labelStrings=COD;
                        materialType="COD";
                        break;
                    case 3:
                        labelStrings=DISSOLVED_OXYGEN;
                        materialType="DISSOLVED_OXYGEN";
                        break;
                    case 4:
                        labelStrings=NITRATE;
                        materialType="NITRATE";
                        break;
                    case 5:
                        labelStrings=TOTAL_PHOSPHORUS;
                        materialType="TOTAL_PHOSPHORUS";
                        break;
                }
                setLabels(labelStrings);
            }
        });
        setLabels(AMMONIA);
        typeBtn.setText(strings.get(0));
    }

    private void setLabels(String[] labelStrings) {
        iconL1.setVisibility(View.VISIBLE);
        for(int i=0; i<labelTxts.size();i++){
            if(i<labelStrings.length){
                labelTxts.get(i).setText(labelStrings[i]);
                labelTxts.get(i).setVisibility(View.VISIBLE);
            }else{
                labelTxts.get(i).setVisibility(View.GONE);
                iconL1.setVisibility(View.GONE);
            }
        }
    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mDrawerLayout.closeDrawer(menuLayout);
            WaterQualityBean site= findList.get(i);
            showSupportContent(new LatLng(Double.valueOf(site.getY()),Double.valueOf(site.getX())), 75, site.getNAME(), site);
            content.setListView(site.getInfos());

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
                WaterQualityBean site=siteList.get(i);
                if(site.getNAME().startsWith(editable.toString())) {
                    findList.add(site);
                    names.add(site.getNAME());
                }
            }
            siteListview.setAdapter(new SiteListAdapter(getActivity(), names));
        }
    };

    public void showSupportContent(LatLng endpositon, int height, String title, final WaterQualityBean bean) {

        content = new MarkerSupportView(getActivity(), title, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baiduMap.hideInfoWindow();
//                Intent i=new Intent(getActivity(), ChatsInfoAct.class);
//                i.putExtra(WaterQualityBean.Name, bean);
//                startActivity(i);
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
            case R.id.startDate:
                String[] dateStart=startDate.getText().toString().split("-");
                datePickerDialog=new DatePickerDialog(getActivity(), mDateSetListener, Integer.valueOf(dateStart[0]),
                        Integer.valueOf(dateStart[1])-1, Integer.valueOf(dateStart[2]));
                datePickerDialog.show();
                break;
            case R.id.findOut:
                handler.getShuizhiInfo(materialType, startDate.getText().toString());
                break;
            case R.id.findTypeBtn:
                ((MainMenuAct)getActivity()).addListFragment(new TongliangMap(),"menu31");
                break;
        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("---> 设置后: year="+year+", month="+monthOfYear+",day="+dayOfMonth);
            startDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

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
                if(method.equals(ConstantUtil.method_Shuizhi)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    siteList= JsonUtil.getWaterQualityInfo(jsonData);
                    ArrayList<String> names=new ArrayList<>();
                    findList.clear();
                    for (WaterQualityBean bean : siteList) {
                        findList.add(bean);
                        names.add(bean.getNAME());
                        View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.level_layout, null);
                        TextView nameTxt= (TextView) mMarkerView.findViewById(R.id.nameTxt);
                        String level="";
                        switch (bean.getLEVEL()){
                            case 1:
                                level="Ⅰ";
                                nameTxt.setBackgroundResource(R.mipmap.water_lv1);
                                break;
                            case 2:
                                level="Ⅱ";
                                nameTxt.setBackgroundResource(R.mipmap.water_lv2);
                                break;
                            case 3:
                                level="Ⅲ";
                                nameTxt.setBackgroundResource(R.mipmap.water_lv3);
                                break;
                            case 4:
                                level="Ⅳ";
                                nameTxt.setBackgroundResource(R.mipmap.water_lv4);
                                break;
                            case 5:
                                level="Ⅴ";
                                nameTxt.setBackgroundResource(R.mipmap.water_lv5);
                                break;
                            case 6:
                                level="劣V";
                                nameTxt.setBackgroundResource(R.mipmap.water_lvl5);
                                break;
                        }

                        nameTxt.setText(level);
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
                    if(siteList.size()>0){
                        WaterQualityBean bean = siteList.get(siteList.size()/2);
                        LatLng point = new LatLng(Double.parseDouble(bean.getY()), Double.parseDouble(bean.getX()));
                        refreshMapStatus(point, 10);
                    }
                }
            }
        });
    }
}
