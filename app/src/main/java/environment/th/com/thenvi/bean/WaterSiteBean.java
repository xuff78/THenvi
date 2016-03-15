package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/3/14.
 */
public class WaterSiteBean implements Serializable{

    private String HSNAME="";
    private String RSNAME="";
    private String LONGITUDE="";
    private String LATITUDE="";
    private String SAFESTAGE="";
    private String CRESTELEVATION="";

    public String getCRESTELEVATION() {
        return CRESTELEVATION;
    }

    public void setCRESTELEVATION(String CRESTELEVATION) {
        this.CRESTELEVATION = CRESTELEVATION;
    }

    public String getSAFESTAGE() {
        return SAFESTAGE;
    }

    public void setSAFESTAGE(String SAFESTAGE) {
        this.SAFESTAGE = SAFESTAGE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getHSNAME() {
        return HSNAME;
    }

    public void setHSNAME(String HSNAME) {
        this.HSNAME = HSNAME;
    }

    public String getRSNAME() {
        return RSNAME;
    }

    public void setRSNAME(String RSNAME) {
        this.RSNAME = RSNAME;
    }

    public ArrayList<PopupInfoItem> getInfos(){
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        datas.add(new PopupInfoItem("水系", RSNAME));
        datas.add(new PopupInfoItem("保证水位", SAFESTAGE));
        datas.add(new PopupInfoItem("堤顶高度", CRESTELEVATION));
        datas.add(new PopupInfoItem("经度", LONGITUDE));
        datas.add(new PopupInfoItem("纬度", LATITUDE));
        return datas;
    }
}
