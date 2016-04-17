package environment.th.com.thenvi.frg;

import android.app.DatePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
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
import android.widget.SlidingDrawer;
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
import environment.th.com.thenvi.activity.MainMenuAct;
import environment.th.com.thenvi.adapter.SiteListAdapter;
import environment.th.com.thenvi.adapter.TongliangAdapter;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.bean.MapPointInfo;
import environment.th.com.thenvi.bean.TongliangBean;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ActUtil;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by 可爱的蘑菇 on 2016/4/16.
 */
public class TongliangMap extends BaseFragment implements View.OnClickListener,
        BaiduMap.OnMapClickListener, TongliangAdapter.OnAreaItemClick {

    private HttpHandler handler;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private TextView endDate, startDate;
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private ArrayList<TongliangBean> siteList=new ArrayList<>();
    private ArrayList<WaterQualityBean> findList=new ArrayList<>();
    private Marker currentMarker;
    private String materialType="", queryDate="";
    private DatePickerDialog datePickerDialog;
    private SlidingDrawer slidingDrawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.tongliang_layout, null);
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

                return true;
            }
        });
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        initHandler();
        initView(mView);

//        handler.getShuizhiInfo("COD","2011-01-01");
        handler.getTongliangList("2011-12-17", "2011-12-20");
        return mView;
    }

    private void initView(View v) {
        slidingDrawer=(SlidingDrawer)v.findViewById(R.id.slidingDrawer);
        startDate = (TextView)v.findViewById(R.id.startDate);
        startDate.setOnClickListener(this);
        endDate = (TextView)v.findViewById(R.id.endDate);
        endDate.setOnClickListener(this);
        v.findViewById(R.id.findOut).setOnClickListener(this);
        v.findViewById(R.id.findTypeBtn).setOnClickListener(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        startDate.setText(str);
        endDate.setText(str);
        siteListview = (ListView) v.findViewById(R.id.siteList);
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);
        v.findViewById(R.id.listLeftBtn).setOnClickListener(this);
    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            WaterQualityBean site= findList.get(i);
            showSupportContent(new LatLng(Double.valueOf(site.getY()),Double.valueOf(site.getX())), 75, site.getNAME(), site);

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
                if(slidingDrawer.isOpened())
                    slidingDrawer.close();
                else
                    slidingDrawer.open();
                break;
            case R.id.typeBtn:
                popup.showPopupWindow(view);
                break;
            case R.id.startDate:
                String[] dateStart=startDate.getText().toString().split("-");
                isStart=true;
                datePickerDialog=new DatePickerDialog(getActivity(), mDateSetListener, Integer.valueOf(dateStart[0]),
                        Integer.valueOf(dateStart[1])-1, Integer.valueOf(dateStart[2]));
                datePickerDialog.show();
                break;
            case R.id.endDate:
                String[] dateEnd=startDate.getText().toString().split("-");
                isStart=false;
                datePickerDialog=new DatePickerDialog(getActivity(), mDateSetListener, Integer.valueOf(dateEnd[0]),
                        Integer.valueOf(dateEnd[1])-1, Integer.valueOf(dateEnd[2]));
                datePickerDialog.show();
                break;
            case R.id.findOut:
                handler.getTongliangList(startDate.getText().toString(), endDate.getText().toString());
//                handler.getShuizhiInfo(materialType, queryDate);
                break;
            case R.id.findTypeBtn:
                ((MainMenuAct)getActivity()).addListFragment(new WaterQualityMap(),"menu32");
                break;
        }
    }

    boolean isStart=true;
    DatePickerDialog.OnDateSetListener mDateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("---> 设置后: year="+year+", month="+monthOfYear+",day="+dayOfMonth);
            if(isStart)
                startDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            else
                endDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
                if(method.equals(ConstantUtil.method_TongliangList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    baiduMap.clear();
                    siteList= JsonUtil.getTongliangList(jsonData);
                    siteListview.setAdapter(new TongliangAdapter(getActivity(), siteList, TongliangMap.this));
                    slidingDrawer.open();
                }else if(method.equals(ConstantUtil.method_TongliangShengdm)){
//                    baiduMap.clear();
//                    ArrayList<MapAreaInfo> datalist=JsonUtil.getTongliangMapArea(jsonData);
//                    ActUtil.showAreaSpace(getActivity(), baiduMap, datalist, getResources().getColor(R.color.normal_blue));
//                    refreshMapStatus(datalist.get(0).getPoints().get(0), 11);
                }else if(method.equals(ConstantUtil.method_TongliangKuajiedm)){
//                    baiduMap.clear();
//                    ArrayList<MapAreaInfo> datalist=JsonUtil.getTongliangMapArea(jsonData);
//                    ActUtil.showAreaSpace(getActivity(), baiduMap, datalist, getResources().getColor(R.color.normal_blue));
//                    refreshMapStatus(datalist.get(0).getPoints().get(0), 11);
                }
            }
        });
    }

    @Override
    public void onFirstNameClick(int pos) {
        TongliangBean bean=siteList.get(pos);
//        handler.getShengdm(bean.getFirstId());
    }

    @Override
    public void onSeccondNameClick(int pos) {
        TongliangBean bean=siteList.get(pos);
//        handler.getKuajiedm(bean.getSecondId());
    }
}
