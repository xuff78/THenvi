package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
public class EmergencySupplies implements Serializable{

    private String zipCode="";
    private String address="";
    private String unitName="";
    private String pointX="";
    private String pointY="";
    private String phone="";
    private String contactMan="";
    private String contactPhone="";
    private String province="";

    public ArrayList<PopupInfoItem> getInfos(){
        ArrayList<PopupInfoItem> datas = new ArrayList<>();
        if(zipCode.length()>0)
            datas.add(new PopupInfoItem("地区代号", zipCode));
        if(address.length()>0)
            datas.add(new PopupInfoItem("地址", address));
        if(phone.length()>0)
            datas.add(new PopupInfoItem("电话", phone));
        if(contactMan.length()>0)
            datas.add(new PopupInfoItem("联系人", contactMan));
        if(contactPhone.length()>0)
            datas.add(new PopupInfoItem("联系电话", contactPhone));
        if(province.length()>0)
            datas.add(new PopupInfoItem("省份", province));
        datas.add(new PopupInfoItem("经度", pointX));
        datas.add(new PopupInfoItem("纬度", pointY));
        return datas;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPointX() {
        return pointX;
    }

    public void setPointX(String pointX) {
        this.pointX = pointX;
    }

    public String getPointY() {
        return pointY;
    }

    public void setPointY(String pointY) {
        this.pointY = pointY;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
