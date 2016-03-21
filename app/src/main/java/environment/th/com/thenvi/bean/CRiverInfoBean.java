package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class CRiverInfoBean implements Serializable {

    private String PNAME=""; //-断面名称
    private String CITY=""; //-市区
    private String RIVER=""; //-河流
    private String TYPE1=""; //-类型
    private String TYPE2=""; //-
    private String LONGITUDE=""; //-经度
    private String LATITUDE=""; //-纬度
    private String REMARK=""; //-备注
    public static String Name="CRiverInfoBean";

    public String getPNAME() {
        return PNAME;
    }

    public void setPNAME(String PNAME) {
        this.PNAME = PNAME;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getRIVER() {
        return RIVER;
    }

    public void setRIVER(String RIVER) {
        this.RIVER = RIVER;
    }

    public String getTYPE1() {
        return TYPE1;
    }

    public void setTYPE1(String TYPE1) {
        this.TYPE1 = TYPE1;
    }

    public String getTYPE2() {
        return TYPE2;
    }

    public void setTYPE2(String TYPE2) {
        this.TYPE2 = TYPE2;
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

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public ArrayList<PopupInfoItem> getInfos(){
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        datas.add(new PopupInfoItem("市区", CITY));
//        if(PNAME.length()>0)
//            datas.add(new PopupInfoItem("断面名称", PNAME));
        if(RIVER.length()>0)
            datas.add(new PopupInfoItem("河流", RIVER));
        if(TYPE1.length()>0)
            datas.add(new PopupInfoItem("类型1", TYPE1));
        if(TYPE2.length()>0)
            datas.add(new PopupInfoItem("类型2", TYPE2));
        if(REMARK.length()>0)
            datas.add(new PopupInfoItem("备注", REMARK));
        datas.add(new PopupInfoItem("经度", LONGITUDE));
        datas.add(new PopupInfoItem("纬度", LATITUDE));
        return datas;
    }
}
