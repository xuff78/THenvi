package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RiverInfoBean implements Serializable{

    private String PROVICES=""; //-省界
    private String HNAME=""; //-省界缓冲区名称
    private String SNAME=""; //-断面名称
    private String RIVER=""; //-河流
    private String LONGITUDE=""; //-经度
    public static String Name="RiverInfoBean";

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getRIVER() {
        return RIVER;
    }

    public void setRIVER(String RIVER) {
        this.RIVER = RIVER;
    }

    public String getSNAME() {
        return SNAME;
    }

    public void setSNAME(String SNAME) {
        this.SNAME = SNAME;
    }

    public String getHNAME() {
        return HNAME;
    }

    public void setHNAME(String HNAME) {
        this.HNAME = HNAME;
    }

    public String getPROVICES() {
        return PROVICES;
    }

    public void setPROVICES(String PROVICES) {
        this.PROVICES = PROVICES;
    }

    private String LATITUDE=""; //-纬度

    public ArrayList<PopupInfoItem> getInfos(){
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        datas.add(new PopupInfoItem("省界", PROVICES));
        if(HNAME.length()>0)
            datas.add(new PopupInfoItem("省界缓冲区", HNAME));
//        if(SNAME.length()>0)
//            datas.add(new PopupInfoItem("断面名称", SNAME));
        if(RIVER.length()>0)
            datas.add(new PopupInfoItem("河流", RIVER));
        datas.add(new PopupInfoItem("经度", LONGITUDE));
        datas.add(new PopupInfoItem("纬度", LATITUDE));
        return datas;
    }
}
