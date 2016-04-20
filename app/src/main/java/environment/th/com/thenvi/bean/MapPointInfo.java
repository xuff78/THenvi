package environment.th.com.thenvi.bean;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/4/17.
 */
public class MapPointInfo implements Serializable{

    private LatLng latLng;
    private String num;
    private String code;

    public MapPointInfo(String num, double x, double y) {
        this.num = num;
        this.latLng=new LatLng(y,x);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
