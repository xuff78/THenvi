package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/4/16.
 */
public class WaterSourceBean implements Serializable{

    private String CICLR=""; //: "null",
    private String LODEGREE=""; //": "长江北路398号",1
    private String CODEN=""; //": "null",
    private String CITY=""; //": "昆山建邦环境投资有限公司北区污水处理厂",
    private String WRESOURCE=""; //": "江苏",1
    private String INFORMATION=""; //": "null",
    private String LATITUDE=""; //": "3505.78",
    private String REMARK=""; //": "67.495",
    private String WWORKS=""; //": "67.495",

    public ArrayList<PopupInfoItem> getInfos(){
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        if(CODEN.length()>0)
            datas.add(new PopupInfoItem("代号", CODEN));
        if(CITY.length()>0)
            datas.add(new PopupInfoItem("城市", CITY));
        if(REMARK.length()>0)
            datas.add(new PopupInfoItem("备注", REMARK));
        if(WRESOURCE.length()>0)
            datas.add(new PopupInfoItem("水源地", WRESOURCE));
        datas.add(new PopupInfoItem("经度", LODEGREE));
        datas.add(new PopupInfoItem("纬度", LATITUDE));
        if(CICLR.length()>0)
            datas.add(new PopupInfoItem("范围", CICLR));
        if(INFORMATION.length()>0)
            datas.add(new PopupInfoItem("详细信息", INFORMATION));
        return datas;
    }

    public String getCICLR() {
        return CICLR;
    }

    public void setCICLR(String CICLR) {
        this.CICLR = CICLR;
    }

    public String getLODEGREE() {
        return LODEGREE;
    }

    public void setLODEGREE(String LODEGREE) {
        this.LODEGREE = LODEGREE;
    }

    public String getCODEN() {
        return CODEN;
    }

    public void setCODEN(String CODEN) {
        this.CODEN = CODEN;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getWRESOURCE() {
        return WRESOURCE;
    }

    public void setWRESOURCE(String WRESOURCE) {
        this.WRESOURCE = WRESOURCE;
    }

    public String getINFORMATION() {
        return INFORMATION;
    }

    public void setINFORMATION(String INFORMATION) {
        this.INFORMATION = INFORMATION;
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

    public String getWWORKS() {
        return WWORKS;
    }

    public void setWWORKS(String WWORKS) {
        this.WWORKS = WWORKS;
    }
}
