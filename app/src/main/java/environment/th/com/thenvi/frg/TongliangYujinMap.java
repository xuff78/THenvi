package environment.th.com.thenvi.frg;

import android.app.DatePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.MainMenuAct;
import environment.th.com.thenvi.adapter.TongliangAdapter;
import environment.th.com.thenvi.adapter.TongliangYujingAdapter;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.bean.MapPointInfo;
import environment.th.com.thenvi.bean.TongliangBean;
import environment.th.com.thenvi.bean.TongliangYujingBean;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ActUtil;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;
import environment.th.com.thenvi.utils.SharedPreferencesUtil;
import environment.th.com.thenvi.view.ChatsDialog;
import environment.th.com.thenvi.view.MarkerSupportView;
import environment.th.com.thenvi.view.MenuPopup;

/**
 * Created by 可爱的蘑菇 on 2016/4/18.
 */
public class TongliangYujinMap extends BaseFragment implements View.OnClickListener,
        BaiduMap.OnMapClickListener{

    private HttpHandler mHandler;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private LinearLayout menuLayout;
    private TextView endDate, startDate;
    private MenuPopup popup;
    private ListView siteListview;
    public InfoWindow mInfoWindow;
    private MarkerSupportView content;
    private ArrayList<ArrayList<TongliangYujingBean>> siteList=new ArrayList<ArrayList<TongliangYujingBean>>();
    private ArrayList<WaterQualityBean> findList=new ArrayList<>();
    private Marker currentMarker;
    private String materialType="", queryDate="";
    private DatePickerDialog datePickerDialog;
    private View leftMenuView;
    private ArrayList<Overlay> showPoints=new ArrayList<>();
    private ArrayList<String> dates=new ArrayList<>();
    private TextView typeBtn, dateTxt;
    private ImageView playBtn;
    private SeekBar seekBar;
    private int stepWidth=1;
    private boolean isPlay=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.tongliang_yujing_layout, null);
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
                int pos = markerExtraInfo.getInt("pos", 0);
//                showSupportContent(marker.getPosition(), height, bean.getNAME(), bean);
                getChatsInfo(pos);
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
        mHandler.getWarningList("2011-12-10", "2011-12-20", "3");
        startDate.setText("2011-12-17");
        endDate.setText("2011-12-20");

        String tongliangMap= SharedPreferencesUtil.getString(getActivity(), ConstantUtil.TongliangMap);
        if(tongliangMap.equals(SharedPreferencesUtil.FAILURE_STRING)) {
            mHandler.getTongliangMap();
        }else {
            showTongliangMap(tongliangMap);
        }
        return mView;
    }

    private void initView(View v) {
        seekBar=(SeekBar) v.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dateTxt=(TextView)v.findViewById(R.id.dateTxt);
        playBtn=(ImageView) v.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);
        leftMenuView=v.findViewById(R.id.leftMenuView);
        startDate = (TextView)v.findViewById(R.id.startDate);
        startDate.setOnClickListener(this);
        endDate = (TextView)v.findViewById(R.id.endDate);
        endDate.setOnClickListener(this);
        v.findViewById(R.id.findOut).setOnClickListener(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        startDate.setText(str);
        endDate.setText(str);
        siteListview = (ListView) v.findViewById(R.id.siteList);
        siteListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TongliangYujingBean bean=siteList.get(seekBar.getProgress()).get(i);
                getChatsInfo(i);
            }
        });
        menuLayout=(LinearLayout)v.findViewById(R.id.leftMenuView);
        v.findViewById(R.id.listLeftBtn).setOnClickListener(this);

        typeBtn=(TextView)v.findViewById(R.id.typeBtn);
        typeBtn.setOnClickListener(this);
        typeBtn.setText("COD");
        final ArrayList<String> strings=new ArrayList<>();
        strings.add("COD");
        strings.add("AMMONIA");
        strings.add("TP");
        strings.add("TN");
        popup = new MenuPopup(getActivity(), strings, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeBtn.setText(strings.get(i));
                popup.dismiss();
            }
        });
    }

    private void getChatsInfo(int pos) {
        ArrayList<String> tongliangyuzhi=new ArrayList<String>();
        ArrayList<String> tongliangzhi=new ArrayList<String>();
        String name=siteList.get(0).get(pos).getDuanMianName();
        for(int i=0;i<siteList.size();i++){
            ArrayList<TongliangYujingBean> sites=siteList.get(i);
            tongliangyuzhi.add(sites.get(pos).getTongLiangYuZhi());
            tongliangzhi.add(sites.get(pos).getChaoBiaoTongLiang());
        }
        ChatsDialog dialog = new ChatsDialog();
        Bundle b=new Bundle();
        b.putStringArrayList("tongliangyuzhi", tongliangyuzhi);
        b.putStringArrayList("tongliangzhi", tongliangzhi);
        b.putStringArrayList("date", dates);
        b.putString("name", name);
        dialog.setArguments(b);
        dialog.show(getFragmentManager(), "loginDialog");
    }

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


    private ArrayList<Overlay> sitelayers=new ArrayList<>();
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
                if(leftMenuView.isShown())
                    hideMenu(leftMenuView);
                else
                    showMenu(leftMenuView);
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
                playBtn.setImageResource(R.mipmap.map_icon_play);
                isPlay=false;
                stopTimer();
                String type=typeBtn.getText().toString();
                if(type.equals("COD"))
                    type="3";
                else if(type.equals("AMMONIA"))
                    type="4";
                else if(type.equals("TP"))
                    type="5";
                else if(type.equals("TN"))
                    type="6";
                mHandler.getWarningList(startDate.getText().toString(), endDate.getText().toString(), type);
//                handler.getShuizhiInfo(materialType, queryDate);
                break;
            case R.id.findTypeBtn:
                ((MainMenuAct)getActivity()).addListFragment(new WaterQualityMap(),"menu32");
                break;
            case R.id.playBtn:
                if(isPlay){
                    stopTimer();
                    playBtn.setImageResource(R.mipmap.map_icon_play);
                }else{
                    startTimer();
                    playBtn.setImageResource(R.mipmap.map_icon_pause);
                }
                isPlay=!isPlay;
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
        mHandler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                if(method.equals(ConstantUtil.method_warningList)){
                    baiduMap.hideInfoWindow();
                    mInfoWindow = null;
                    siteList= JsonUtil.getTongliangYujingList(jsonData);
                    playBtn.setImageResource(R.mipmap.map_icon_play);
                    seekBar.setProgress(0);
                    seekBar.setMax(siteList.size());
                    if(siteList.size()>0) {
                        dates=JsonUtil.getYujingDate(jsonData);
                        siteListview.setAdapter(new TongliangYujingAdapter(getActivity(), siteList.get(0)));
                        showMenu(leftMenuView);
                        showTongliangyujin(siteList.get(0));
                        dateTxt.setText(dates.get(0));
                    }
                }else if(method.equals(ConstantUtil.method_TongliangShengdm)){
                    if(showPoints.size()>0) {
                        ActUtil.removeLayers(showPoints);
                        showPoints.clear();
                    }
                    ArrayList<MapAreaInfo> datalist=JsonUtil.getTongliangMapArea(jsonData);
                    showPoints=ActUtil.showAreaSpace(getActivity(), baiduMap, datalist, 0xaaAEEEEE);
                    refreshMapStatus(datalist.get(0).getPoints().get(0), 10);
                }else if(method.equals(ConstantUtil.method_TongliangKuajiedm)){
                    if(showPoints.size()>0) {
                        ActUtil.removeLayers(showPoints);
                        showPoints.clear();
                    }
                    ArrayList<MapAreaInfo> datalist=JsonUtil.getTongliangMapArea(jsonData);
                    showPoints=ActUtil.showAreaSpace(getActivity(), baiduMap, datalist, 0xaaAEEEEE);
                    refreshMapStatus(datalist.get(0).getPoints().get(0), 10);
                }else if(method.equals(ConstantUtil.method_TongliangMap)){
                    SharedPreferencesUtil.setString(getActivity(), ConstantUtil.TongliangMap, jsonData);
                    showTongliangMap(jsonData);
                }
            }
        });
    }

    private void showTongliangyujin(ArrayList<TongliangYujingBean> beans) {
        for (int i=0; i<beans.size();i++) {
            TongliangYujingBean bean=beans.get(i);
            float beishu=Float.valueOf(bean.getChaoBiaoBeiShu());
            int resId=0;
            if(beishu>1&&beishu<5){
                resId=R.drawable.shape_round_tongliangyujin3;
            }else if(beishu<10&&beishu>=5){
                resId=R.drawable.shape_round_tongliangyujin2;
            }else if(beishu>10){
                resId=R.drawable.shape_round_tongliangyujin1;
            }
            if(resId!=0) {
                View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.tongliang_mark, null);
                TextView numTxt= (TextView) mMarkerView.findViewById(R.id.numTxt);
                View roundIcon=mMarkerView.findViewById(R.id.roundIcon);
                roundIcon.setBackgroundResource(resId);
                numTxt.setText(bean.getChaoBiaoTongLiang());
                LatLng point = new LatLng(Double.parseDouble(bean.getY()), Double.parseDouble(bean.getX()));
                Bundle bundle = new Bundle();
                bundle.putInt("pos", i);
                //将标记添加到地图上
                addMarkerToMap(point, bundle, mMarkerView);
            }
        }
    }

    private void showTongliangMap(String jsonData) {
        ArrayList<MapAreaInfo> infos=JsonUtil.getAreaInfo(jsonData, "14fenqu");
//        ArrayList<MapPointInfo> points=JsonUtil.getPoints(jsonData);
//        int width=ScreenUtil.dip2px(getActivity(),12);
//        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(width,width);
//        for (MapPointInfo bean : points) {
//            View mMarkerView = LayoutInflater.from(getActivity()).inflate(R.layout.icon_layout, null);
//            ImageView nameTxt= (ImageView) mMarkerView.findViewById(R.id.iconImg);
//            nameTxt.setImageResource(R.mipmap.icon_duanmian);
//            nameTxt.setLayoutParams(llp);
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("InfoBean", bean);
//            addMarkerToMap(bean.getLatLng(), bundle, mMarkerView);
//        }
        ActUtil.showAreaSpace(getActivity(), baiduMap, infos, 0x44A4D3EE, getResources().getColor(R.color.hardtranswhite));
//        ActUtil.showWorkingLine(baiduMap, infos);
        if(infos.size()>0)
            refreshMapStatus(infos.get(0).getPoints().get(0), 10);
    }

    private void hideMenu(final View v){
        Animation anima= AnimationUtils.loadAnimation(getActivity() ,R.anim.trans_down_out);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anima);
    }

    private void showMenu(final View v){
        Animation anima= AnimationUtils.loadAnimation(getActivity() ,R.anim.trans_up_in);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anima);
    }

    private Handler handler=new Handler();
    private TimerTask task;
    private Timer timer=new Timer();
    private void startTimer() {

        task = new TimerTask(){

            @Override
            public void run() {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        toNextInfo();
                    }

                });
            }

        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void toNextInfo() {
        if(seekBar.getProgress()+stepWidth>=100||seekBar.getProgress()+stepWidth>(siteList.size()-1)*stepWidth) {
            seekBar.setProgress(0);
            dateTxt.setText(dates.get(0));
            playBtn.setImageResource(R.mipmap.map_icon_play);
            isPlay=false;
            stopTimer();
        }else {
            int progress=seekBar.getProgress() + stepWidth;
            seekBar.setProgress(progress);

            dateTxt.setText(dates.get(progress));
            siteListview.setAdapter(new TongliangYujingAdapter(getActivity(), siteList.get(progress)));
            showTongliangyujin(siteList.get(progress));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(task!=null)
            stopTimer();
    }

    private void stopTimer() {
        if(task!=null)
            task.cancel();
    }
}
