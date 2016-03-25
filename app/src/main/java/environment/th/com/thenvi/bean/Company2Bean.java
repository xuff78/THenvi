package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/25.
 */
public class Company2Bean implements Serializable{

    private String SLEVEL=""; //: "null",
    private String DOORPLATE=""; //": "长江北路398号",1
    private String BDATA=""; //": "null",
    private String NAME=""; //": "昆山建邦环境投资有限公司北区污水处理厂",
    private String MUNICIPALITY=""; //": "江苏",1
    private String ORGANIZATION=""; //": "null",
    private String COST=""; //": "3505.78",
    private String NINVESTMENT=""; //": "67.495",
    private String YEAR=""; //": "2011",1
    private String CITY=""; //": "苏州",1
    private String TOWN=""; //": "周市",1
    private String RUNINGDAY=""; //": "365",
    private String LSEWAGE=""; //": "3651.825",
    private String INVESTMENT=""; //": "19696",
    private String REPRESENTATIVE=""; //": "null",
    private String TEL=""; //": "null",
    private String RECEIVWCODE=""; //": "娄江",1
    private String RECEIVWNAME=""; //": "FM19010000",1
    private String HANDLE=""; //": "3651.825",
    private String COUNTY=""; //": "昆山"1
    private String LATITUDE=""; //-经度
    private String LODEGREEE=""; //-纬度

    public ArrayList<PopupInfoItem> getInfos() {
        ArrayList<PopupInfoItem> datas = new ArrayList<>();

        if(SLEVEL.length()>0)
            datas.add(new PopupInfoItem("污水处理级别", SLEVEL));
        if(BDATA.length()>0)
            datas.add(new PopupInfoItem("建成时间", BDATA));
        if(NAME.length()>0)
            datas.add(new PopupInfoItem("单位名称(公章)", NAME));
        if(ORGANIZATION.length()>0)
            datas.add(new PopupInfoItem("组织机构代码", ORGANIZATION));
        if(COST.length()>0)
            datas.add(new PopupInfoItem("运行费用(万元)", COST));
        if(NINVESTMENT.length()>0)
            datas.add(new PopupInfoItem("新增固定资产投资(万元)", NINVESTMENT));
        if(RUNINGDAY.length()>0)
            datas.add(new PopupInfoItem("运行天数(天)", RUNINGDAY));
        if(LSEWAGE.length()>0)
            datas.add(new PopupInfoItem("生活污水处理量(万吨)", LSEWAGE));
        if(INVESTMENT.length()>0)
            datas.add(new PopupInfoItem("累计完成投资(万元)", INVESTMENT));
        if(REPRESENTATIVE.length()>0)
            datas.add(new PopupInfoItem("法定代表人", REPRESENTATIVE));
        if(TEL.length()>0)
            datas.add(new PopupInfoItem("联系方式电话号码", TEL));
        if(HANDLE.length()>0)
            datas.add(new PopupInfoItem("污水实际处理量(万吨)", HANDLE));
        if(YEAR.length()>0)
            datas.add(new PopupInfoItem("统计年份", YEAR));
        if(RECEIVWNAME.length()>0)
            datas.add(new PopupInfoItem("受纳水体名称", RECEIVWNAME));
        if(COUNTY.length()>0)
            datas.add(new PopupInfoItem("详细地址县", COUNTY));
        if(DOORPLATE.length()>0)
            datas.add(new PopupInfoItem("详细地址街道、门牌", DOORPLATE));
        if(MUNICIPALITY.length()>0)
            datas.add(new PopupInfoItem("详细地址省、直辖市", MUNICIPALITY));
        if(CITY.length()>0)
            datas.add(new PopupInfoItem("详细地址地区市、州", CITY));
        if(TOWN.length()>0)
            datas.add(new PopupInfoItem("详细地址乡", TOWN));
        if(RECEIVWCODE.length()>0)
            datas.add(new PopupInfoItem("受纳水体代码", RECEIVWCODE));
        return datas;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLODEGREEE() {
        return LODEGREEE;
    }

    public void setLODEGREEE(String LODEGREEE) {
        this.LODEGREEE = LODEGREEE;
    }

    public String getSLEVEL() {
        return SLEVEL;
    }

    public void setSLEVEL(String SLEVEL) {
        this.SLEVEL = SLEVEL;
    }

    public String getDOORPLATE() {
        return DOORPLATE;
    }

    public void setDOORPLATE(String DOORPLATE) {
        this.DOORPLATE = DOORPLATE;
    }

    public String getBDATA() {
        return BDATA;
    }

    public void setBDATA(String BDATA) {
        this.BDATA = BDATA;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMUNICIPALITY() {
        return MUNICIPALITY;
    }

    public void setMUNICIPALITY(String MUNICIPALITY) {
        this.MUNICIPALITY = MUNICIPALITY;
    }

    public String getORGANIZATION() {
        return ORGANIZATION;
    }

    public void setORGANIZATION(String ORGANIZATION) {
        this.ORGANIZATION = ORGANIZATION;
    }

    public String getCOST() {
        return COST;
    }

    public void setCOST(String COST) {
        this.COST = COST;
    }

    public String getNINVESTMENT() {
        return NINVESTMENT;
    }

    public void setNINVESTMENT(String NINVESTMENT) {
        this.NINVESTMENT = NINVESTMENT;
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

    public String getRUNINGDAY() {
        return RUNINGDAY;
    }

    public void setRUNINGDAY(String RUNINGDAY) {
        this.RUNINGDAY = RUNINGDAY;
    }

    public String getLSEWAGE() {
        return LSEWAGE;
    }

    public void setLSEWAGE(String LSEWAGE) {
        this.LSEWAGE = LSEWAGE;
    }

    public String getINVESTMENT() {
        return INVESTMENT;
    }

    public void setINVESTMENT(String INVESTMENT) {
        this.INVESTMENT = INVESTMENT;
    }

    public String getREPRESENTATIVE() {
        return REPRESENTATIVE;
    }

    public void setREPRESENTATIVE(String REPRESENTATIVE) {
        this.REPRESENTATIVE = REPRESENTATIVE;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getRECEIVWCODE() {
        return RECEIVWCODE;
    }

    public void setRECEIVWCODE(String RECEIVWCODE) {
        this.RECEIVWCODE = RECEIVWCODE;
    }

    public String getRECEIVWNAME() {
        return RECEIVWNAME;
    }

    public void setRECEIVWNAME(String RECEIVWNAME) {
        this.RECEIVWNAME = RECEIVWNAME;
    }

    public String getHANDLE() {
        return HANDLE;
    }

    public void setHANDLE(String HANDLE) {
        this.HANDLE = HANDLE;
    }

    public String getCOUNTY() {
        return COUNTY;
    }

    public void setCOUNTY(String COUNTY) {
        this.COUNTY = COUNTY;
    }
}
