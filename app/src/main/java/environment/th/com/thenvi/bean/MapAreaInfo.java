package environment.th.com.thenvi.bean;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public class MapAreaInfo {

    private String code="";
    private String SmPerimeter="";
    private String num="0";
    private List<LatLng> points=new ArrayList<LatLng>();

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSmPerimeter() {
        return SmPerimeter;
    }

    public void setSmPerimeter(String smPerimeter) {
        SmPerimeter = smPerimeter;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
