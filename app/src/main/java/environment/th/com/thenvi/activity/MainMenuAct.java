package environment.th.com.thenvi.activity;

import android.animation.Animator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.frg.BookPage;
import environment.th.com.thenvi.frg.CurrentLoactionMap;
import environment.th.com.thenvi.frg.EmergencySuppliesMap;
import environment.th.com.thenvi.frg.SewageDisposalMap;
import environment.th.com.thenvi.frg.WaterDatabaseMap;
import environment.th.com.thenvi.frg.WaterInfoMap;
import environment.th.com.thenvi.frg.WaterQualityMap;
import environment.th.com.thenvi.frg.WaterSource;
import environment.th.com.thenvi.services.BDLocationService;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.LogUtil;
import environment.th.com.thenvi.utils.SharedPreferencesUtil;

public class MainMenuAct extends AppCompatActivity implements View.OnClickListener {

    public BDLocationService locationService;
    public Fragment frg;
    public MyLocationListener listenerLocation=new MyLocationListener();
    public View selectBtn=null, select2Btn=null, menu_level2_layout;
    private int selectPos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initView();
//        initLocation();
        frg = new CurrentLoactionMap();
        addListFragment(frg, "menu1");
    }

    private void initView() {
        View menu1=findViewById(R.id.menu_btn1);
        menu1.setOnClickListener(this);
        menu1.setSelected(true);
        selectBtn=menu1;
        findViewById(R.id.menu_btn2).setOnClickListener(this);
        findViewById(R.id.menu_btn3).setOnClickListener(this);
        findViewById(R.id.menu_btn5).setOnClickListener(this);

        View menu21=findViewById(R.id.menu_btn21);
        menu21.setOnClickListener(listener);
        select2Btn=menu21;
        findViewById(R.id.menu_btn22).setOnClickListener(listener);
        findViewById(R.id.menu_btn23).setOnClickListener(listener);
        findViewById(R.id.menu_btn24).setOnClickListener(listener);
        findViewById(R.id.menu_btn25).setOnClickListener(listener);

        menu_level2_layout=findViewById(R.id.menu_level2_layout);
        menu_level2_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMenu();
            }
        });
    }

    @Override
    public void onClick(View view) {
        boolean hide=false;
        if(menu_level2_layout.isShown()) {
            hideMenu();
            hide=true;
        }
        if(view.getId()==R.id.menu_btn2&&!menu_level2_layout.isShown()&&!hide) {
            showMenu();
        }
        if(selectBtn==view)
            return;
        selectBtn.setSelected(false);
        selectBtn=view;
        view.setSelected(true);
        switch (view.getId()){
            case R.id.menu_btn1:
                selectPos=0;
                frg = new CurrentLoactionMap();
                addListFragment(frg, "menu1");
                break;
            case R.id.menu_btn2:
                selectPos=1;
                frg = new WaterInfoMap();
                addListFragment(frg, "menu2");
                break;
            case R.id.menu_btn3:
                selectPos=2;
                frg = new WaterQualityMap();
                addListFragment(frg, "menu3");
                break;
            case R.id.menu_btn5:
                selectPos=1;
                frg = new BookPage();
                addListFragment(frg, "menu5");
                break;
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(select2Btn==view)
                return;
            select2Btn.setBackgroundResource(R.color.trans);
            select2Btn=view;
            view.setBackgroundResource(R.color.alphagray);
            view.setSelected(true);
            switch (view.getId()) {
                case R.id.menu_btn21:
                    frg = new WaterInfoMap();
                    addListFragment(frg, "menu21");
                    break;
                case R.id.menu_btn22:
                    frg = new WaterDatabaseMap();
                    addListFragment(frg, "menu22");
                    break;
                case R.id.menu_btn23:
                    frg = new WaterSource();
                    addListFragment(frg, "menu23");
                    break;
                case R.id.menu_btn24:
                    frg = new EmergencySuppliesMap();
                    addListFragment(frg, "menu24");
                    break;
                case R.id.menu_btn25:
                    frg = new SewageDisposalMap();
                    addListFragment(frg, "menu25");
                    break;
            }
        }
    };

    private void hideMenu(){
        Animation anima= AnimationUtils.loadAnimation(this ,R.anim.trans_down_out);
        anima.setAnimationListener(animalistener);
        menu_level2_layout.startAnimation(anima);
    }

    private void showMenu(){
        Animation anima= AnimationUtils.loadAnimation(this ,R.anim.trans_up_in);
        anima.setAnimationListener(animalistener);
        menu_level2_layout.startAnimation(anima);
    }

    Animation.AnimationListener animalistener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(menu_level2_layout.isShown())
                menu_level2_layout.setVisibility(View.GONE);
            else
                menu_level2_layout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private void initLocation() {
        locationService =  new BDLocationService(getApplicationContext());
        locationService.registerListener(listenerLocation);
        locationService.start();
    }

    public void addListFragment(Fragment frg, String frgId) {
        FragmentTransaction Transaction = getFragmentManager().beginTransaction();
        Transaction.replace(R.id.mainFrame, frg, frgId)
                .commit();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null) {

//                BigDecimal la = new BigDecimal(bdLocation.getLatitude());
//                BigDecimal lo = new BigDecimal(bdLocation.getLongitude());
                Double lat = bdLocation.getLatitude();
                Double lon = bdLocation.getLongitude();

                String old_lat= SharedPreferencesUtil.getString(MainMenuAct.this, ConstantUtil.lat);
                String old_lon=SharedPreferencesUtil.getString(MainMenuAct.this, ConstantUtil.lon);
                if (!old_lat.equals(lat) || !old_lon.equals(lon)){
                    LatLng current_point=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                    SharedPreferencesUtil.setString(MainMenuAct.this, ConstantUtil.lon, String.valueOf(lon)); //location.get("lng")+"");
                    SharedPreferencesUtil.setString(MainMenuAct.this, ConstantUtil.lat, String.valueOf(lat)); //location.get("lat")+"");
//                    ((CurrentLoactionMap)frg).refreshMapStatus(current_point, 18);
                    LogUtil.i("Location","location: lat: "+lat+"   lon: "+lon);

                }
                switch (bdLocation.getLocType()) {

                    case BDLocation.TypeGpsLocation:
                    case BDLocation.TypeNetWorkLocation:
                        List<Poi> list= bdLocation.getPoiList();
                       /* if (list!=null){
                            for (int i=0;i<list.size();i++){
                               LogUtils.i("HttpAsyncTask" + "="+list.get(i).getName()+"="+list.get(i).getRank()+"="+list.get(i).describeContents());

                            }
                        }*/
                        String currentCity=bdLocation.getCity();
                        if(currentCity != null){
                            SharedPreferencesUtil.setString(MainMenuAct.this, ConstantUtil.current_city, bdLocation.getCity());
                        }
                        if (list != null){
                            SharedPreferencesUtil.setString(MainMenuAct.this, ConstantUtil.current_addr,  list.get(0).getName());
                        }else {
                            break;
                        }
                        locationService.stop();
                        locationService.unRegisterListener(listenerLocation);
                        break;
                    case BDLocation.TypeNetWorkException:
                        break;
                }

            }
        }
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            exithandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

    private static boolean isExit = false;

    Handler exithandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onDestroy() {
        if(locationService!=null) {
            locationService.stop();
            locationService.unRegisterListener(listenerLocation);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        exit();
    }
}
