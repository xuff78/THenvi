package environment.th.com.thenvi.services;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度定位服务
 * Created by Administrator on 2015/12/3.
 */
public class BDLocationService {

    private LocationClient mLocationClient;
    private LocationClientOption defualtLocationOption,DIYoption;
    private Object objLock = new Object();

    public BDLocationService(Context appContext){

        synchronized (objLock){
            if(mLocationClient==null){
                mLocationClient=new LocationClient(appContext); //声明LocationClient类
                mLocationClient.setLocOption(getDefualtLocationOption());
            }
        }
    }
    /**
     * 获得默认的location
     * @return
     */
    public LocationClientOption getDefualtLocationOption() {
        if(defualtLocationOption==null) {
            defualtLocationOption = new LocationClientOption();
            defualtLocationOption.setAddrType("all");
            defualtLocationOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            defualtLocationOption.setCoorType("bd09ll");
            defualtLocationOption.setScanSpan(60000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于10
            defualtLocationOption.setOpenGps(true);//可选，默认false,设置是否使用gps
            defualtLocationOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            defualtLocationOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            defualtLocationOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            defualtLocationOption.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
            defualtLocationOption.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        }
        return defualtLocationOption;
    }

    public boolean registerListener(BDLocationListener mListener){
        if(mListener!=null) {
            mLocationClient.registerLocationListener(mListener);
            return true;
        }
        return false;
    }
    public void unRegisterListener(BDLocationListener mListener){
        if(mListener!=null) {
            mLocationClient.unRegisterLocationListener(mListener);
        }
    }

    public boolean setLocationOption(LocationClientOption clientOption){

        if(clientOption!=null){
            if(mLocationClient.isStarted()){
                mLocationClient.stop();
            }
            DIYoption = clientOption;
            mLocationClient.setLocOption(clientOption);
        }

        return false;
    }
    //获取当前的option
    public LocationClientOption getOption(){
        return DIYoption;
    }

    public void start(){
        synchronized (objLock) {
            if(mLocationClient != null && !mLocationClient.isStarted()){
                mLocationClient.start();
            }
        }
    }
    public void stop(){
        synchronized (objLock) {
            if(mLocationClient != null && mLocationClient.isStarted()){
                mLocationClient.stop();
            }
        }
    }
}
