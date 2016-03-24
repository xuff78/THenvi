package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ChuqinBean implements Serializable{

    private String LPTYPE="";  //: "生猪",
    private String OZN="";  //": "18648",
    private String OZP="";  //": "3074.4",
    private String PZN="";  //": "5040",
    private String DOORPLATE="";  //": "先锋村",1
    private String PZP="";  //": "33300",
    private String NBREAD="";  //": "9000",
    private String OZNN="";  //": "11502",
    private String MUNICIPALITY="";  //": "江苏",1
    private String PNN="";  //": "16200",
    private String LODEGREE="";  //": "31.622195",1
    private String ANAME="";  //": "太仓市",1
    private String YEAR="";  //": "2011",1
    private String CITY="";  //": "苏州",1
    private String TOWN="";  //": "浮桥",1
    private String FARM="";  //": "320585-XC0056-(01)",
    private String RECEIVWCODE="";  //": "FM18000000",1
    private String OCO="";  //": "46008",
    private String RECEIVWNAME="";  //": "戚浦河",1
    private String ACODE="";  //": "320585",1
    private String LATITUDE="";  //": "121.147208",1
    private String PCO="";  //": "324000",
    private String COUNTY="";  //": "太仓"1

    public ArrayList<PopupInfoItem> getInfos() {
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        if(PNN.length()>0)
            datas.add(new PopupInfoItem("氨氮产生量(千克)", PNN));
        if(FARM.length()>0)
            datas.add(new PopupInfoItem("养殖场（小区）编号", FARM));
        if(OCO.length()>0)
            datas.add(new PopupInfoItem("排放量_化学需养量（千克）", OCO));
        if(PCO.length()>0)
            datas.add(new PopupInfoItem("生产量_化学需养量（千克）", PCO));
        if(LPTYPE.length()>0)
            datas.add(new PopupInfoItem("畜禽总量", LPTYPE));
        if(OZN.length()>0)
            datas.add(new PopupInfoItem("排放量_总氮（千克）", OZN));
        if(OZP.length()>0)
            datas.add(new PopupInfoItem("排放量_总磷（千克）", OZP));
        if(PZN.length()>0)
            datas.add(new PopupInfoItem("产生量_总磷（千克）", PZN));
        if(PZP.length()>0)
            datas.add(new PopupInfoItem("产生量_总氮（千克）", PZP));
        if(NBREAD.length()>0)
            datas.add(new PopupInfoItem("饲养量（头）", NBREAD));
        if(OZNN.length()>0)
            datas.add(new PopupInfoItem("排放量_氨氮（千克））", OZNN));
        if(LODEGREE.length()>0)
            datas.add(new PopupInfoItem("经度", LODEGREE));
        if(YEAR.length()>0)
            datas.add(new PopupInfoItem("统计年份", YEAR));
        if(RECEIVWNAME.length()>0)
            datas.add(new PopupInfoItem("受纳水体名称", RECEIVWNAME));
        if(ACODE.length()>0)
            datas.add(new PopupInfoItem("行政区划代码", ACODE));
        if(COUNTY.length()>0)
            datas.add(new PopupInfoItem("详细地址县", COUNTY));
        if(DOORPLATE.length()>0)
            datas.add(new PopupInfoItem("详细地址街道、门牌", DOORPLATE));
        if(MUNICIPALITY.length()>0)
            datas.add(new PopupInfoItem("详细地址省、直辖市", MUNICIPALITY));
        if(ANAME.length()>0)
            datas.add(new PopupInfoItem("行政区划名词", ANAME));
        if(CITY.length()>0)
            datas.add(new PopupInfoItem("详细地址地区市、州", CITY));
        if(TOWN.length()>0)
            datas.add(new PopupInfoItem("详细地址乡", TOWN));
        if(RECEIVWCODE.length()>0)
            datas.add(new PopupInfoItem("受纳水体代码", RECEIVWCODE));
        if(LATITUDE.length()>0)
            datas.add(new PopupInfoItem("纬度", LATITUDE));
        return datas;
    }

    public String getLPTYPE() {
        return LPTYPE;
    }

    public void setLPTYPE(String LPTYPE) {
        this.LPTYPE = LPTYPE;
    }

    public String getOZN() {
        return OZN;
    }

    public void setOZN(String OZN) {
        this.OZN = OZN;
    }

    public String getOZP() {
        return OZP;
    }

    public void setOZP(String OZP) {
        this.OZP = OZP;
    }

    public String getPZN() {
        return PZN;
    }

    public void setPZN(String PZN) {
        this.PZN = PZN;
    }

    public String getDOORPLATE() {
        return DOORPLATE;
    }

    public void setDOORPLATE(String DOORPLATE) {
        this.DOORPLATE = DOORPLATE;
    }

    public String getPZP() {
        return PZP;
    }

    public void setPZP(String PZP) {
        this.PZP = PZP;
    }

    public String getNBREAD() {
        return NBREAD;
    }

    public void setNBREAD(String NBREAD) {
        this.NBREAD = NBREAD;
    }

    public String getOZNN() {
        return OZNN;
    }

    public void setOZNN(String OZNN) {
        this.OZNN = OZNN;
    }

    public String getMUNICIPALITY() {
        return MUNICIPALITY;
    }

    public void setMUNICIPALITY(String MUNICIPALITY) {
        this.MUNICIPALITY = MUNICIPALITY;
    }

    public String getPNN() {
        return PNN;
    }

    public void setPNN(String PNN) {
        this.PNN = PNN;
    }

    public String getLODEGREE() {
        return LODEGREE;
    }

    public void setLODEGREE(String LODEGREE) {
        this.LODEGREE = LODEGREE;
    }

    public String getANAME() {
        return ANAME;
    }

    public void setANAME(String ANAME) {
        this.ANAME = ANAME;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getTOWN() {
        return TOWN;
    }

    public void setTOWN(String TOWN) {
        this.TOWN = TOWN;
    }

    public String getFARM() {
        return FARM;
    }

    public void setFARM(String FARM) {
        this.FARM = FARM;
    }

    public String getRECEIVWCODE() {
        return RECEIVWCODE;
    }

    public void setRECEIVWCODE(String RECEIVWCODE) {
        this.RECEIVWCODE = RECEIVWCODE;
    }

    public String getOCO() {
        return OCO;
    }

    public void setOCO(String OCO) {
        this.OCO = OCO;
    }

    public String getRECEIVWNAME() {
        return RECEIVWNAME;
    }

    public void setRECEIVWNAME(String RECEIVWNAME) {
        this.RECEIVWNAME = RECEIVWNAME;
    }

    public String getACODE() {
        return ACODE;
    }

    public void setACODE(String ACODE) {
        this.ACODE = ACODE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getPCO() {
        return PCO;
    }

    public void setPCO(String PCO) {
        this.PCO = PCO;
    }

    public String getCOUNTY() {
        return COUNTY;
    }

    public void setCOUNTY(String COUNTY) {
        this.COUNTY = COUNTY;
    }
}
