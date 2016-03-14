package environment.th.com.thenvi.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/3/14.
 */
public class WaterSiteBean {

    private String HSNAME="";
    private String RSNAME="";

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
        datas.add(new PopupInfoItem("保证水位", RSNAME));
        datas.add(new PopupInfoItem("堤顶高度", RSNAME));
        datas.add(new PopupInfoItem("经度", RSNAME));
        datas.add(new PopupInfoItem("纬度", RSNAME));
        return datas;
    }
}
