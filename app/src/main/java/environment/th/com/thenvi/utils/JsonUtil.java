package environment.th.com.thenvi.utils;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.ChatGateDam;
import environment.th.com.thenvi.bean.ChatGuokong;
import environment.th.com.thenvi.bean.ChatKuaJie;
import environment.th.com.thenvi.bean.ChatRainFall;
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
import environment.th.com.thenvi.bean.ChuqinBean;
import environment.th.com.thenvi.bean.Company2Bean;
import environment.th.com.thenvi.bean.CompanyBean;
import environment.th.com.thenvi.bean.EmergencySupplies;
import environment.th.com.thenvi.bean.GongyeBean;
import environment.th.com.thenvi.bean.JsonMessage;
import environment.th.com.thenvi.bean.MapAreaInfo;
import environment.th.com.thenvi.bean.MapPointInfo;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.TongliangBean;
import environment.th.com.thenvi.bean.TongliangDataBean;
import environment.th.com.thenvi.bean.TongliangItem;
import environment.th.com.thenvi.bean.TongliangYujingBean;
import environment.th.com.thenvi.bean.WaterQualityBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.bean.WaterSourceBean;

/**
 * Created by Administrator on 2015/9/7.
 */
public class JsonUtil {

    public static JsonMessage getJsonMessage(String jsonStr){
        JsonMessage jsonMsg=new JsonMessage();
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("code"))
                jsonMsg.setCode(json.getString("code"));
            if(!json.isNull("msg"))
                jsonMsg.setMsg(json.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMsg;
    }

    public static String getJsonData(String jsonStr) {
        String data="";
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("data"))
                data=json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getString(String jsonStr, String key) {
        String data="";
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key))
                data=json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static int getJsonInt(String jsonStr, String key) {
        int data=0;
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key))
                data=json.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ArrayList<WaterSiteBean> getSiteList(String jsonData) {
        ArrayList<WaterSiteBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                WaterSiteBean site=new WaterSiteBean();
                if(!subJson.isNull("HSNAME"))
                    site.setHSNAME(subJson.getString("HSNAME"));
                else if(!subJson.isNull("NAME"))
                    site.setHSNAME(subJson.getString("NAME"));
                else if(!subJson.isNull("DNAME"))
                    site.setHSNAME(subJson.getString("DNAME"));
                if(!subJson.isNull("RSNAME"))
                    site.setRSNAME(subJson.getString("RSNAME"));
                if(!subJson.isNull("LONGITUDE"))
                    site.setLONGITUDE(subJson.getString("LONGITUDE"));
                if(!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
//                if(!subJson.isNull("RSNAME"))
//                    site.setRSNAME(subJson.getString("RSNAME"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<WaterQualityBean> getWaterQualityInfo(String jsonData) {
        ArrayList<WaterQualityBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                WaterQualityBean site=new WaterQualityBean();
                if(!subJson.isNull("CODE"))
                    site.setCODE(subJson.getString("CODE"));
                if(!subJson.isNull("LEVEL"))
                    site.setLEVEL(subJson.getInt("LEVEL"));
                if(!subJson.isNull("NAME"))
                    site.setNAME(subJson.getString("NAME"));
                if(!subJson.isNull("X"))
                    site.setX(subJson.getString("X"));
                if(!subJson.isNull("Y"))
                    site.setY(subJson.getString("Y"));
//                Map<String, Double> map = Translate.convertMC2LL(Double.valueOf(site.getX()), Double.valueOf(site.getY()));
//                site.setX(String.valueOf(map.get("lng")));
//                site.setY(String.valueOf(map.get("lat")));
//                LatLng ll=ActUtil.Mercator2lonLat(Double.valueOf(site.getX()), Double.valueOf(site.getY()));
//                site.setX(String.valueOf(ll.longitude));
//                site.setY(String.valueOf(ll.latitude));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static WaterSiteBean getSiteDetail(String jsonStr) {
        WaterSiteBean site=new WaterSiteBean();
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("siteDetail")) {
                JSONObject subJson=json.getJSONObject("siteDetail");
                if (!subJson.isNull("hsname"))
                    site.setHSNAME(subJson.getString("hsname"));
                else if (!subJson.isNull("RFNAME"))
                    site.setHSNAME(subJson.getString("RFNAME"));
                if (!subJson.isNull("rsname"))
                    site.setRSNAME(subJson.getString("rsname"));
                else if (!subJson.isNull("RSNAME"))
                    site.setRSNAME(subJson.getString("RSNAME"));
                if (!subJson.isNull("LONGITUDE"))
                    site.setLONGITUDE(subJson.getString("LONGITUDE"));
                if (!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
                if (!subJson.isNull("SAFESTAGE"))
                    site.setSAFESTAGE(subJson.getString("SAFESTAGE"));
                if (!subJson.isNull("CRESTELEVATION"))
                    site.setCRESTELEVATION(subJson.getString("CRESTELEVATION"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static ArrayList<ChatWaterSiteBean> getChatWateSite(String jsonData) {
        ArrayList<ChatWaterSiteBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatWaterSiteBean site=new ChatWaterSiteBean();
                if(!subJson.isNull("DATA"))
                    site.setDATE(subJson.getString("DATA"));
                if(!subJson.isNull("STAGE"))
                    site.setSTAGE(subJson.getString("STAGE"));
                if(!subJson.isNull("LONGITUDE"))
                    site.setLONGITUDE(subJson.getString("LONGITUDE"));
                if(!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
                if(!subJson.isNull("FLOW"))
                    site.setFLOW(subJson.getString("FLOW"));
                if(!subJson.isNull("RAINFALL"))
                    site.setRAINFALL(subJson.getString("RAINFALL"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ChatRainFall> getChatRainFall(String jsonData) {
        ArrayList<ChatRainFall> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatRainFall site=new ChatRainFall();
                if(!subJson.isNull("DATA"))
                    site.setDATE(subJson.getString("DATA"));
                if(!subJson.isNull("RAINFALL"))
                    site.setRAINFALL(subJson.getString("RAINFALL"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ChatGateDam> getChatGateDam(String jsonData) {
        ArrayList<ChatGateDam> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatGateDam site=new ChatGateDam();
                if(!subJson.isNull("DATA"))
                    site.setDATE(subJson.getString("DATA"));
                if(!subJson.isNull("DIVERSION"))
                    site.setDIVERSION(subJson.getString("DIVERSION"));
                if(!subJson.isNull("DRAINAGE"))
                    site.setDRAINAGE(subJson.getString("DRAINAGE"));
                if(!subJson.isNull("UPPERSLUICE"))
                    site.setUPPERSLUICE(subJson.getString("UPPERSLUICE"));
                if(!subJson.isNull("UNDERSLUICE"))
                    site.setUNDERSLUICE(subJson.getString("UNDERSLUICE"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<RiverInfoBean> getKuajieSite(String jsonData) {
        ArrayList<RiverInfoBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                RiverInfoBean site=new RiverInfoBean();
                if(!subJson.isNull("HNAME"))
                    site.setHNAME(subJson.getString("HNAME"));
                if(!subJson.isNull("SNAME"))
                    site.setSNAME(subJson.getString("SNAME"));
                if(!subJson.isNull("RIVER"))
                    site.setRIVER(subJson.getString("RIVER"));
                if(!subJson.isNull("LONGITUDE"))
                    site.setLONGITUDE(subJson.getString("LONGITUDE"));
                if(!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
                if(!subJson.isNull("PROVICES"))
                    site.setPROVICES(subJson.getString("PROVICES"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<EmergencySupplies> getEmergencyInfo(String jsonData) {
        ArrayList<EmergencySupplies> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                EmergencySupplies site=new EmergencySupplies();
                if(!subJson.isNull("zipCode"))
                    site.setZipCode(subJson.getString("zipCode"));
                if(!subJson.isNull("address"))
                    site.setAddress(subJson.getString("address"));
                if(!subJson.isNull("unitName"))
                    site.setUnitName(subJson.getString("unitName"));
                else if(!subJson.isNull("name"))
                    site.setUnitName(subJson.getString("name"));
                if(!subJson.isNull("pointX"))
                    site.setPointX(subJson.getString("pointX"));
                if(!subJson.isNull("pointY"))
                    site.setPointY(subJson.getString("pointY"));
                if(!subJson.isNull("phone"))
                    site.setPhone(subJson.getString("phone"));
                if(!subJson.isNull("contactMan"))
                    site.setContactMan(subJson.getString("contactMan"));
                if(!subJson.isNull("contactPhone"))
                    site.setContactMan(subJson.getString("contactPhone"));
                if(!subJson.isNull("province"))
                    site.setProvince(subJson.getString("province"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<CRiverInfoBean> getGuokongSite(String jsonData) {
        ArrayList<CRiverInfoBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                CRiverInfoBean site=new CRiverInfoBean();
                if(!subJson.isNull("PNAME"))
                    site.setPNAME(subJson.getString("PNAME"));
                if(!subJson.isNull("CITY"))
                    site.setCITY(subJson.getString("CITY"));
                if(!subJson.isNull("RIVER"))
                    site.setRIVER(subJson.getString("RIVER"));
                if(!subJson.isNull("TYPE1"))
                    site.setTYPE1(subJson.getString("TYPE1"));
                if(!subJson.isNull("TYPE2"))
                    site.setTYPE2(subJson.getString("TYPE2"));
                if(!subJson.isNull("LONGITUDE"))
                    site.setLONGITUDE(subJson.getString("LONGITUDE"));
                if(!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
                if(!subJson.isNull("REMARK"))
                    site.setREMARK(subJson.getString("REMARK"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<MapAreaInfo> getAreaInfo(String jsonData, String dataName) {
        ArrayList<MapAreaInfo> sitelist=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject(jsonData);
            JSONArray items=obj.getJSONArray(dataName);
            for(int j=0;j<items.length();j++) {
                JSONObject item=items.getJSONObject(j);
//                LogUtil.i("json", item.toString());
                JSONArray array = item.getJSONArray("pointData");
                MapAreaInfo site = new MapAreaInfo();
                List<LatLng> points = new ArrayList<LatLng>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject subJson = array.getJSONObject(i);
                    Double lon = 0d, lat = 0d;
                    if (!subJson.isNull("6mian"))
                        site.setNum(subJson.getString("6mian"));
                    if (!subJson.isNull("POINT_X"))
                        lon = Double.valueOf(subJson.getString("POINT_X"));
                    if (!subJson.isNull("POINT_Y"))
                        lat = Double.valueOf(subJson.getString("POINT_Y"));
                    LatLng ll = new LatLng(lat, lon);
                    points.add(ll);
                }
                site.setPoints(points);
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<MapAreaInfo> getAreaInfo2(String jsonData, String dataName) {
        ArrayList<MapAreaInfo> sitelist=new ArrayList<>();
        MapAreaInfo site = new MapAreaInfo();
        try {
            JSONObject obj=new JSONObject(jsonData);
            JSONArray items=obj.getJSONArray(dataName);
            List<LatLng> points = new ArrayList<>();
            String num="";
            for(int j=0;j<items.length();j++) {
                JSONObject subJson=items.getJSONObject(j);
                LogUtil.i("json", subJson.toString());
                Double lon = 0d, lat = 0d;
                if (!subJson.isNull("POINT_X"))
                    lon = Double.valueOf(subJson.getString("POINT_X"));
                if (!subJson.isNull("POINT_Y"))
                    lat = Double.valueOf(subJson.getString("POINT_Y"));
                LatLng ll = new LatLng(lat, lon);
                String tmpnum="";
                if (!subJson.isNull("NUM"))
                    tmpnum = subJson.getString("NUM");
                if(num.equals(tmpnum)){
                    points.add(ll);
                }else {
                    if (j != 0) {
                        site.setPoints(points);
                        sitelist.add(site);
                        site = new MapAreaInfo();
                        points=new ArrayList<>();
                    }
                    points.add(ll);
                    num=tmpnum;
                }
                site.setPoints(points);
                sitelist.add(site);
            }
            site.setPoints(points);
            sitelist.add(site);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<MapAreaInfo> getAreaInfo3(String jsonData, String dataName) {
        ArrayList<MapAreaInfo> sitelist=new ArrayList<>();
        MapAreaInfo site = new MapAreaInfo();
        try {
            JSONObject obj=new JSONObject(jsonData);
            JSONObject data=obj.getJSONObject(dataName);
            List<LatLng> points = new ArrayList<>();
            JSONArray items=data.getJSONArray("suhu");
            for(int j=0;j<items.length();j++) {
                JSONObject itemjson=items.getJSONObject(j);
                JSONObject subJson=itemjson.getJSONObject("pointData");
                LogUtil.i("json", subJson.toString());
                Double lon = 0d, lat = 0d;
                if (!subJson.isNull("POINT_X"))
                    lon = Double.valueOf(subJson.getString("POINT_X"));
                if (!subJson.isNull("POINT_Y"))
                    lat = Double.valueOf(subJson.getString("POINT_Y"));
                LatLng ll = new LatLng(lat, lon);
                points.add(ll);
            }
            site.setPoints(points);
            sitelist.add(site);

            site = new MapAreaInfo();
            points = new ArrayList<>();
            items=data.getJSONArray("suzhe");
            for(int j=0;j<items.length();j++) {
                JSONObject itemjson=items.getJSONObject(j);
                JSONObject subJson=itemjson.getJSONObject("pointData");
                LogUtil.i("json", subJson.toString());
                Double lon = 0d, lat = 0d;
                if (!subJson.isNull("POINT_X"))
                    lon = Double.valueOf(subJson.getString("POINT_X"));
                if (!subJson.isNull("POINT_Y"))
                    lat = Double.valueOf(subJson.getString("POINT_Y"));
                LatLng ll = new LatLng(lat, lon);
                points.add(ll);
            }
            site.setPoints(points);
            sitelist.add(site);

            site = new MapAreaInfo();
            points = new ArrayList<>();
            items=data.getJSONArray("zhehu");
            for(int j=0;j<items.length();j++) {
                JSONObject itemjson=items.getJSONObject(j);
                JSONObject subJson=itemjson.getJSONObject("pointData");
                LogUtil.i("json", subJson.toString());
                Double lon = 0d, lat = 0d;
                if (!subJson.isNull("POINT_X"))
                    lon = Double.valueOf(subJson.getString("POINT_X"));
                if (!subJson.isNull("POINT_Y"))
                    lat = Double.valueOf(subJson.getString("POINT_Y"));
                LatLng ll = new LatLng(lat, lon);
                points.add(ll);
            }
            site.setPoints(points);
            sitelist.add(site);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<WaterSourceBean> getWaterSourceInfo(String jsonData) {
        ArrayList<WaterSourceBean> sitelist=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject(jsonData);
            JSONArray items=obj.getJSONArray("siteList");
            for(int j=0;j<items.length();j++) {
                JSONObject subJson=items.getJSONObject(j);
//                LogUtil.i("json", subJson.toString());
                WaterSourceBean site=new WaterSourceBean();
                if(!subJson.isNull("CICLR"))
                    site.setCICLR(subJson.getString("CICLR"));
                if(!subJson.isNull("LODEGREE"))
                    site.setLODEGREE(subJson.getString("LODEGREE"));
                if(!subJson.isNull("CODEN"))
                    site.setCODEN(subJson.getString("CODEN"));
                if(!subJson.isNull("CITY"))
                    site.setCITY(subJson.getString("CITY"));
                if(!subJson.isNull("WRESOURCE"))
                    site.setWRESOURCE(subJson.getString("WRESOURCE"));
                if(!subJson.isNull("INFORMATION"))
                    site.setINFORMATION(subJson.getString("INFORMATION"));
                if(!subJson.isNull("LATITUDE"))
                    site.setLATITUDE(subJson.getString("LATITUDE"));
                if(!subJson.isNull("REMARK"))
                    site.setREMARK(subJson.getString("REMARK"));
                if(!subJson.isNull("WWORKS"))
                    site.setWWORKS(subJson.getString("WWORKS"));
                sitelist.add(site);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ChatKuaJie> getChatKuajie(String jsonData, int showPos) {
        ArrayList<ChatKuaJie> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatKuaJie site=new ChatKuaJie();
                if(!subJson.isNull("CODN")&&showPos==0)
                    site.setCODN(subJson.getString("CODN"));
                if(!subJson.isNull("NDN")&&showPos==1)
                    site.setNDN(subJson.getString("NDN"));
                if(!subJson.isNull("PN")&&showPos==2)
                    site.setPN(subJson.getString("PN"));
                if(!subJson.isNull("NN")&&showPos==3)
                    site.setNN(subJson.getString("NN"));
                if(!subJson.isNull("FLOW"))
                    site.setFLOW(subJson.getString("FLOW"));
                if(!subJson.isNull("DATA"))
                    site.setDATA(subJson.getString("DATA"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ChatGuokong> getChatGuokong(String jsonData, int showPos) {
        ArrayList<ChatGuokong> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatGuokong site=new ChatGuokong();
                if(!subJson.isNull("CLCLE"))
                    site.setCLCLE(subJson.getString("CLCLE"));
                if(!subJson.isNull("PH")&&showPos==0)
                    site.setPH(subJson.getString("PH"));
                if(!subJson.isNull("DO")&&showPos==1)
                    site.setDO(subJson.getString("DO"));
                if(!subJson.isNull("CODMN")&&showPos==2)
                    site.setCODMN(subJson.getString("CODMN"));
                if(!subJson.isNull("NH3N")&&showPos==3)
                    site.setNH3N(subJson.getString("NH3N"));
                if(!subJson.isNull("WQUALITY"))
                    site.setWQUALITY(subJson.getString("WQUALITY"));
                if(!subJson.isNull("LQUALITY"))
                    site.setLQUALITY(subJson.getString("LQUALITY"));
                if(!subJson.isNull("DATA"))
                    site.setDATA(subJson.getString("DATA"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }


    public static CompanyBean getCompanyDetail(String jsonData){
        CompanyBean site=new CompanyBean();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull("siteDetail")) {
                JSONObject jsonItem=json.getJSONObject("siteDetail");
                site=getCompanyBean(jsonItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static ArrayList<CompanyBean> getCompanyList(String jsonData) {
        ArrayList<CompanyBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                sitelist.add(getCompanyBean(subJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static CompanyBean getCompanyBean(JSONObject subJson) throws JSONException {
        CompanyBean site=new CompanyBean();
        if(!subJson.isNull("STATECONTR"))
            site.setSTATECONTR(subJson.getString("STATECONTR"));
        if(!subJson.isNull("PSALIAS"))
            site.setPSALIAS(subJson.getString("PSALIAS"));
        if(!subJson.isNull("UPDATETIME"))
            site.setUPDATETIME(subJson.getString("UPDATETIME"));
        if(!subJson.isNull("PSCODE"))
            site.setPSCODE(subJson.getString("PSCODE"));
        if(!subJson.isNull("TOTALAREA"))
            site.setTOTALAREA(subJson.getString("TOTALAREA"));
        if(!subJson.isNull("COMMENTS"))
            site.setCOMMENTS(subJson.getString("COMMENTS"));
        if(!subJson.isNull("REGISTTYPE"))
            site.setREGISTTYPE(subJson.getString("REGISTTYPE"));
        if(!subJson.isNull("EMAIL"))
            site.setEMAIL(subJson.getString("EMAIL"));
        if(!subJson.isNull("PSALIAS"))
            site.setPSALIAS(subJson.getString("PSALIAS"));
        if(!subJson.isNull("UNITTYPENA"))
            site.setUNITTYPENA(subJson.getString("UNITTYPENA"));
        if(!subJson.isNull("ISMONITOR"))
            site.setISMONITOR(subJson.getString("ISMONITOR"));
        if(!subJson.isNull("INDUSTRYTY"))
            site.setINDUSTRYTY(subJson.getString("INDUSTRYTY"));
        if(!subJson.isNull("X"))
            site.setX(subJson.getString("X"));
        if(!subJson.isNull("Y"))
            site.setY(subJson.getString("Y"));
        if(!subJson.isNull("ENVIRONMEN"))
            site.setENVIRONMEN(subJson.getString("ENVIRONMEN"));
        if(!subJson.isNull("ATTENTIOND"))
            site.setATTENTIOND(subJson.getString("ATTENTIOND"));
        if(!subJson.isNull("POSTALCODE"))
            site.setPOSTALCODE(subJson.getString("POSTALCODE"));
        if(!subJson.isNull("VALLEYNAME"))
            site.setVALLEYNAME(subJson.getString("VALLEYNAME"));
        if(!subJson.isNull("PSNUMBER"))
            site.setPSNUMBER(subJson.getString("PSNUMBER"));
        if(!subJson.isNull("SUBJECTION"))
            site.setSUBJECTION(subJson.getString("SUBJECTION"));

        if(!subJson.isNull("PSCLASSNAM"))
            site.setPSCLASSNAM(subJson.getString("PSCLASSNAM"));
        if(!subJson.isNull("RUNDATE"))
            site.setRUNDATE(subJson.getString("RUNDATE"));
        if(!subJson.isNull("REGIONNAME"))
            site.setREGIONNAME(subJson.getString("REGIONNAME"));
        if(!subJson.isNull("PSWEBSITE"))
            site.setPSWEBSITE(subJson.getString("PSWEBSITE"));
        if(!subJson.isNull("MOBILEPHON"))
            site.setMOBILEPHON(subJson.getString("MOBILEPHON"));
        if(!subJson.isNull("PSSCALENAM"))
            site.setPSSCALENAM(subJson.getString("PSSCALENAM"));

        if(!subJson.isNull("PSADDRESS"))
            site.setPSADDRESS(subJson.getString("PSADDRESS"));
        if(!subJson.isNull("PSNAME"))
            site.setPSNAME(subJson.getString("PSNAME"));
        if(!subJson.isNull("COMMUNICAT"))
            site.setCOMMUNICAT(subJson.getString("COMMUNICAT"));
        if(!subJson.isNull("OPENACOCUN"))
            site.setOPENACOCUN(subJson.getString("OPENACOCUN"));
        if(!subJson.isNull("BANKACCOUN"))
            site.setBANKACCOUN(subJson.getString("BANKACCOUN"));
        if(!subJson.isNull("AREANAME"))
            site.setAREANAME(subJson.getString("AREANAME"));

        if(!subJson.isNull("CORPORATIO"))
            site.setCORPORATIO(subJson.getString("CORPORATIO"));
        if(!subJson.isNull("FAX"))
            site.setFAX(subJson.getString("FAX"));
        if(!subJson.isNull("PSENVIRONM"))
            site.setPSENVIRONM(subJson.getString("PSENVIRONM"));
        return site;
    }



    public static ArrayList<GongyeBean> getGongyeList(String jsonData) {
        ArrayList<GongyeBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                sitelist.add(getGongyeBean(subJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static GongyeBean getGongyeDetail(String jsonData){
        GongyeBean site=new GongyeBean();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull("siteDetail")) {
                JSONObject jsonItem=json.getJSONObject("siteDetail");
                site=getGongyeBean(jsonItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static GongyeBean getGongyeBean(JSONObject subJson) throws JSONException {
        GongyeBean site=new GongyeBean();
        if(!subJson.isNull("NN"))
            site.setNN(subJson.getString("NN"));
        if(!subJson.isNull("LOSECOND"))
            site.setLOSECOND(subJson.getString("LOSECOND"));
        if(!subJson.isNull("LADEGREE"))
            site.setLADEGREE(subJson.getString("LADEGREE"));
        if(!subJson.isNull("LONGITUDE"))
            site.setLONGITUDE(subJson.getString("LONGITUDE"));
        if(!subJson.isNull("LODEGREE"))
            site.setLODEGREE(subJson.getString("LODEGREE"));
        if(!subJson.isNull("BASINNAME"))
            site.setBASINNAME(subJson.getString("BASINNAME"));
        if(!subJson.isNull("YEAR"))
            site.setYEAR(subJson.getString("YEAR"));
        if(!subJson.isNull("VERTICAL"))
            site.setVERTICAL(subJson.getString("VERTICAL"));
        if(!subJson.isNull("COD"))
            site.setCOD(subJson.getString("COD"));
        if(!subJson.isNull("LAPORTION"))
            site.setLAPORTION(subJson.getString("LAPORTION"));
        if(!subJson.isNull("DTYPE"))
            site.setDTYPE(subJson.getString("DTYPE"));
        if(!subJson.isNull("RECEIVWNAME"))
            site.setRECEIVWNAME(subJson.getString("RECEIVWNAME"));
        if(!subJson.isNull("ACODE"))
            site.setACODE(subJson.getString("ACODE"));
        if(!subJson.isNull("LOPORTION"))
            site.setLOPORTION(subJson.getString("LOPORTION"));
        if(!subJson.isNull("COUNTY"))
            site.setCOUNTY(subJson.getString("COUNTY"));
        if(!subJson.isNull("DOORPLATE"))
            site.setDOORPLATE(subJson.getString("DOORPLATE"));
        if(!subJson.isNull("DCODE"))
            site.setDCODE(subJson.getString("DCODE"));
        if(!subJson.isNull("MUNICIPALITY"))
            site.setMUNICIPALITY(subJson.getString("MUNICIPALITY"));
        if(!subJson.isNull("ANAME"))
            site.setANAME(subJson.getString("ANAME"));
        if(!subJson.isNull("CITY"))
            site.setCITY(subJson.getString("CITY"));

        if(!subJson.isNull("BASINCODE"))
            site.setBASINCODE(subJson.getString("BASINCODE"));
        if(!subJson.isNull("ZN"))
            site.setZN(subJson.getString("ZN"));
        if(!subJson.isNull("TOWN"))
            site.setTOWN(subJson.getString("TOWN"));
        if(!subJson.isNull("LASECOND"))
            site.setLASECOND(subJson.getString("LASECOND"));
        if(!subJson.isNull("ZP"))
            site.setZP(subJson.getString("ZP"));
        if(!subJson.isNull("RECEIVWCODE"))
            site.setRECEIVWCODE(subJson.getString("RECEIVWCODE"));

        if(!subJson.isNull("RUNIT"))
            site.setRUNIT(subJson.getString("RUNIT"));
        if(!subJson.isNull("LATITUDE"))
            site.setLATITUDE(subJson.getString("LATITUDE"));

        return site;
    }



    public static ArrayList<ChuqinBean> getChuqinList(String jsonData) {
        ArrayList<ChuqinBean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                sitelist.add(getChuqinBean(subJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ChuqinBean getChuqinDetail(String jsonData){
        ChuqinBean site=new ChuqinBean();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull("siteDetail")) {
                JSONObject jsonItem=json.getJSONObject("siteDetail");
                site=getChuqinBean(jsonItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static ChuqinBean getChuqinBean(JSONObject subJson) throws JSONException {
        ChuqinBean site=new ChuqinBean();
        if(!subJson.isNull("LPTYPE"))
            site.setLPTYPE(subJson.getString("LPTYPE"));
        if(!subJson.isNull("OZN"))
            site.setOZN(subJson.getString("OZN"));
        if(!subJson.isNull("OZP"))
            site.setOZP(subJson.getString("OZP"));
        if(!subJson.isNull("PZN"))
            site.setPZN(subJson.getString("PZN"));
        if(!subJson.isNull("DOORPLATE"))
            site.setDOORPLATE(subJson.getString("DOORPLATE"));
        if(!subJson.isNull("PZP"))
            site.setPZP(subJson.getString("PZP"));
        if(!subJson.isNull("NBREAD"))
            site.setNBREAD(subJson.getString("NBREAD"));
        if(!subJson.isNull("OZNN"))
            site.setOZNN(subJson.getString("OZNN"));
        if(!subJson.isNull("MUNICIPALITY"))
            site.setMUNICIPALITY(subJson.getString("MUNICIPALITY"));
        if(!subJson.isNull("PNN"))
            site.setPNN(subJson.getString("PNN"));
        if(!subJson.isNull("LODEGREE"))
            site.setLODEGREE(subJson.getString("LODEGREE"));
        else if(!subJson.isNull("LONGITUDE"))
            site.setLODEGREE(subJson.getString("LONGITUDE"));
        if(!subJson.isNull("ANAME"))
            site.setANAME(subJson.getString("ANAME"));
        if(!subJson.isNull("YEAR"))
            site.setYEAR(subJson.getString("YEAR"));
        if(!subJson.isNull("CITY"))
            site.setCITY(subJson.getString("CITY"));
        if(!subJson.isNull("TOWN"))
            site.setCOUNTY(subJson.getString("TOWN"));
        if(!subJson.isNull("FARM"))
            site.setFARM(subJson.getString("FARM"));
        if(!subJson.isNull("RECEIVWCODE"))
            site.setRECEIVWCODE(subJson.getString("RECEIVWCODE"));
        if(!subJson.isNull("OCO"))
            site.setOCO(subJson.getString("OCO"));
        if(!subJson.isNull("RECEIVWNAME"))
            site.setRECEIVWNAME(subJson.getString("RECEIVWNAME"));
        if(!subJson.isNull("ACODE"))
            site.setACODE(subJson.getString("ACODE"));

        if(!subJson.isNull("LATITUDE"))
            site.setLATITUDE(subJson.getString("LATITUDE"));
        if(!subJson.isNull("PCO"))
            site.setPCO(subJson.getString("PCO"));
        if(!subJson.isNull("COUNTY"))
            site.setCOUNTY(subJson.getString("COUNTY"));

        return site;
    }


    public static ArrayList<Company2Bean> getCompany2List(String jsonData) {
        ArrayList<Company2Bean> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                sitelist.add(getCompany2Bean(subJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static Company2Bean getCompany2Detail(String jsonData){
        Company2Bean site=new Company2Bean();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull("siteDetail")) {
                JSONObject jsonItem=json.getJSONObject("siteDetail");
                site=getCompany2Bean(jsonItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return site;
    }

    public static Company2Bean getCompany2Bean(JSONObject subJson) throws JSONException {
        Company2Bean site=new Company2Bean();
        if(!subJson.isNull("SLEVEL"))
            site.setSLEVEL(subJson.getString("SLEVEL"));
        if(!subJson.isNull("DOORPLATE"))
            site.setDOORPLATE(subJson.getString("DOORPLATE"));
        if(!subJson.isNull("BDATA"))
            site.setBDATA(subJson.getString("BDATA"));
        if(!subJson.isNull("NAME"))
            site.setNAME(subJson.getString("NAME"));
        if(!subJson.isNull("MUNICIPALITY"))
            site.setMUNICIPALITY(subJson.getString("MUNICIPALITY"));
        if(!subJson.isNull("ORGANIZATION"))
            site.setORGANIZATION(subJson.getString("ORGANIZATION"));
        if(!subJson.isNull("COST"))
            site.setCOST(subJson.getString("COST"));
        if(!subJson.isNull("NINVESTMENT"))
            site.setNINVESTMENT(subJson.getString("NINVESTMENT"));
        if(!subJson.isNull("YEAR"))
            site.setYEAR(subJson.getString("YEAR"));
        if(!subJson.isNull("CITY"))
            site.setCITY(subJson.getString("CITY"));
        if(!subJson.isNull("TOWN"))
            site.setTOWN(subJson.getString("TOWN"));
        if(!subJson.isNull("RUNINGDAY"))
            site.setRUNINGDAY(subJson.getString("RUNINGDAY"));
        if(!subJson.isNull("LSEWAGE"))
            site.setLSEWAGE(subJson.getString("LSEWAGE"));
        if(!subJson.isNull("INVESTMENT"))
            site.setINVESTMENT(subJson.getString("INVESTMENT"));
        if(!subJson.isNull("REPRESENTATIVE"))
            site.setREPRESENTATIVE(subJson.getString("REPRESENTATIVE"));
        if(!subJson.isNull("TEL"))
            site.setTEL(subJson.getString("TEL"));
        if(!subJson.isNull("RECEIVWCODE"))
            site.setRECEIVWCODE(subJson.getString("RECEIVWCODE"));
        if(!subJson.isNull("RECEIVWNAME"))
            site.setRECEIVWNAME(subJson.getString("RECEIVWNAME"));
        if(!subJson.isNull("HANDLE"))
            site.setHANDLE(subJson.getString("HANDLE"));
        if(!subJson.isNull("COUNTY"))
            site.setCOUNTY(subJson.getString("COUNTY"));
        if(!subJson.isNull("LATITUDE"))
            site.setLATITUDE(subJson.getString("LATITUDE"));
        if(!subJson.isNull("LODEGREE"))
            site.setLODEGREE(subJson.getString("LODEGREE"));

        return site;
    }

    public static String getWaterSiteJsonStr(ArrayList<ChatWaterSiteBean> datalist) {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(datalist.size()<max)
                max=datalist.size();

            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<datalist.size();i++){

                JSONObject objsub=new JSONObject();

                JSONObject direct=new JSONObject();
                direct.put("value", datalist.get(i).getSTAGE());
//                direct.put("symbol", "arrow");
//                direct.put("symbolSize", 5);
                objsub.put("data",  direct);
                array3.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getFLOW());
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=datalist.get(i).getDATE();
                objsub.put("data", time);
                array5.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo1", array3);
            jsonObj.put("SecondFormInfo2", array4);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static String getRainSiteJsonStr(ArrayList<ChatWaterSiteBean> datalist) {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(datalist.size()<max)
                max=datalist.size();

            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<datalist.size();i++){

                JSONObject objsub=new JSONObject();

                objsub.put("data", datalist.get(i).getRAINFALL());
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=datalist.get(i).getDATE();
                objsub.put("data", time);
                array5.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo1", array4);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static ArrayList<BookBean> getPDFTypes(String jsonData){
        ArrayList<BookBean> sites=new ArrayList();
        try {

            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                BookBean book=new BookBean();
                if(!subJson.isNull("TYPES"))
                    book.setBookName(subJson.getString("TYPES"));
                if(!subJson.isNull("TCODE"))
                    book.setType(subJson.getString("TCODE"));
                sites.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sites;
    }

    public static ArrayList<BookBean> getPDFInfo(String jsonData){
        ArrayList<BookBean> sites=new ArrayList();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull("content")) {
                JSONArray array=json.getJSONArray("content");
                for(int i=0;i<array.length();i++){
                    JSONObject subJson=array.getJSONObject(i);
                    BookBean book=new BookBean();
                    if(!subJson.isNull("URL"))
                        book.setBookUrl(subJson.getString("URL"));
                    if(!subJson.isNull("NAME"))
                        book.setBookName(subJson.getString("NAME"));
                    sites.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sites;
    }

    public static String getGateDamJsonStr(ArrayList<ChatGateDam> datalist) {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(datalist.size()<max)
                max=datalist.size();

            JSONArray array1=new JSONArray();
            JSONArray array2=new JSONArray();
            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<datalist.size();i++){

                JSONObject objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getUPPERSLUICE());
                array1.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getUNDERSLUICE());
                array2.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getDIVERSION());
                array3.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getDRAINAGE());
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=datalist.get(i).getDATE();
                objsub.put("data", time);
                array5.put(i, objsub);
            }

            jsonObj.put("FirstFormInfo1", array1);
            jsonObj.put("FirstFormInfo2", array2);
            jsonObj.put("SecondFormInfo1", array3);
            jsonObj.put("SecondFormInfo2", array4);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static String getKuajieJsonStr(ArrayList<ChatKuaJie> datalist) {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(datalist.size()<max)
                max=datalist.size();

            JSONArray array0=new JSONArray();
            JSONArray array1=new JSONArray();
            JSONArray array2=new JSONArray();
            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<datalist.size();i++){

                JSONObject objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getFLOW());
                array0.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getCODN());
                array1.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getNDN());
                array2.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getPN());
                array3.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getNN());
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=datalist.get(i).getDATA();
                objsub.put("data", time);
                array5.put(i, objsub);
            }

            jsonObj.put("FLOW", array0);
            jsonObj.put("FirstFormInfo1", array1);
            jsonObj.put("FirstFormInfo2", array2);
            jsonObj.put("SecondFormInfo1", array3);
            jsonObj.put("SecondFormInfo2", array4);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static String getGuokongJsonStr(ArrayList<ChatGuokong> datalist) {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();
            if(datalist.size()<max)
                max=datalist.size();

            JSONArray array0=new JSONArray();
            JSONArray array1=new JSONArray();
            JSONArray array2=new JSONArray();
            JSONArray array3=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<datalist.size();i++){

                JSONObject objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getNH3N());
                array0.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getPH());
                array1.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getDO());
                array2.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", datalist.get(i).getCODMN());
                array3.put(i, objsub);

                objsub=new JSONObject();
                String time=datalist.get(i).getDATA();
                objsub.put("data", time);
                array5.put(i, objsub);
            }

            jsonObj.put("FLOW", array0);
            jsonObj.put("FirstFormInfo1", array1);
            jsonObj.put("FirstFormInfo2", array2);
            jsonObj.put("SecondFormInfo1", array3);
            jsonObj.put("tags", array5);

            jsonData=jsonObj.toString();
            Log.i("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static ArrayList<TongliangBean> getTongliangList(String jsonData) {
        ArrayList<TongliangBean> sitelist=new ArrayList<>();
        try {
            JSONArray items=new JSONArray(jsonData);
            for(int j=0;j<items.length();j++) {
                TongliangBean tlbean=new TongliangBean();
                JSONObject item=items.getJSONObject(j);
                if(!item.isNull("firstName"))
                    tlbean.setFirstName(item.getString("firstName"));
                if(!item.isNull("secondId"))
                    tlbean.setSecondId(item.getString("secondId"));
                if(!item.isNull("firstId"))
                    tlbean.setFirstId(item.getString("firstId"));
                if(!item.isNull("secondName"))
                    tlbean.setSecondName(item.getString("secondName"));
                tlbean.setNegativeList(getTongliangitem(item.toString(), "negativeList"));
                tlbean.setPositiveList(getTongliangitem(item.toString(), "positiveList"));
                tlbean.setNetList(getTongliangitem(item.toString(), "netList"));
                sitelist.add(tlbean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<TongliangItem> getTongliangitem(String jsonData, String name){
        ArrayList<TongliangItem> sites=new ArrayList();
        try {
            JSONObject json=new JSONObject(jsonData);
            if(!json.isNull(name)) {
                JSONArray array=json.getJSONArray(name);
                for(int i=0;i<array.length();i++){
                    JSONObject subJson=array.getJSONObject(i);
                    TongliangItem book=new TongliangItem();
                    if(!subJson.isNull("AMMONIA"))
                        book.setAMMONIA(subJson.getString("AMMONIA"));
                    if(!subJson.isNull("COD"))
                        book.setCOD(subJson.getString("COD"));
                    if(!subJson.isNull("TN"))
                        book.setTN(subJson.getString("TN"));
                    if(!subJson.isNull("TP"))
                        book.setTP(subJson.getString("TP"));
                    sites.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(sites.size()==0){
            sites.add(new TongliangItem());
        }
        return sites;
    }

    public static ArrayList<MapAreaInfo> getTongliangMapArea(String jsonData) {
        ArrayList<MapAreaInfo> sitelist=new ArrayList<>();
        try {
            JSONArray items=new JSONArray(jsonData);
            for(int j=0;j<items.length();j++) {
                JSONObject item=items.getJSONObject(j);
//                LogUtil.i("json", item.toString());
                JSONArray array = item.getJSONArray("pointData");
                MapAreaInfo site = new MapAreaInfo();
                List<LatLng> points = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject subJson = array.getJSONObject(i);
                    Double lon = 0d, lat = 0d;
                    if (!subJson.isNull("POINT_X"))
                        lon = Double.valueOf(subJson.getString("POINT_X"));
                    if (!subJson.isNull("POINT_Y"))
                        lat = Double.valueOf(subJson.getString("POINT_Y"));
                    LatLng ll = new LatLng(lat, lon);
                    points.add(ll);
                }
                site.setPoints(points);
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<MapPointInfo> getPoints(String jsonData) {
        ArrayList<MapPointInfo> sitelist=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject(jsonData);
            JSONArray array=obj.getJSONArray("duanMianDian");
            for(int i=0;i<array.length();i++){
                JSONObject subJson = array.getJSONObject(i);
                Double lon = 0d, lat = 0d;
                String name="";
                if(!subJson.isNull("NAME"))
                    name=subJson.getString("NAME");
                if (!subJson.isNull("X"))
                    lat = Double.valueOf(subJson.getString("X"));
                if (!subJson.isNull("Y"))
                    lon = Double.valueOf(subJson.getString("Y"));
                MapPointInfo site=new MapPointInfo(name, lat, lon);
                if(!subJson.isNull("CODE"))
                    site.setCode(subJson.getString("CODE"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ArrayList<TongliangYujingBean>> getTongliangYujingList(String jsonData) {
        ArrayList<ArrayList<TongliangYujingBean>> sitelist=new ArrayList<ArrayList<TongliangYujingBean>>();
        try {
            JSONArray itemArray=new JSONArray(jsonData);
            for(int i=0;i<itemArray.length();i++) {
                JSONObject obj=itemArray.getJSONObject(i);
                JSONArray items=obj.getJSONArray("data");
                ArrayList<TongliangYujingBean> subitem = new ArrayList<>();
                for (int j = 0; j < items.length(); j++) {
                    TongliangYujingBean tlbean = new TongliangYujingBean();
                    JSONObject item = items.getJSONObject(j);
                    if (!item.isNull("tongLiangYuZhi"))
                        tlbean.setTongLiangYuZhi(item.getString("tongLiangYuZhi"));
                    if (!item.isNull("duanMianName"))
                        tlbean.setDuanMianName(item.getString("duanMianName"));
                    if (!item.isNull("provinceName"))
                        tlbean.setProvinceName(item.getString("provinceName"));
                    if (!item.isNull("chaoBiaoTongLiang"))
                        tlbean.setChaoBiaoTongLiang(item.getString("chaoBiaoTongLiang"));
                    if (!item.isNull("chaoBiaoBeiShu"))
                        tlbean.setChaoBiaoBeiShu(item.getString("chaoBiaoBeiShu"));
                    if (!item.isNull("x"))
                        tlbean.setX(item.getString("x"));
                    if (!item.isNull("y"))
                        tlbean.setY(item.getString("y"));
                    subitem.add(tlbean);
                }
                sitelist.add(subitem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<String> getYujingDate(String jsonData) {
        ArrayList<String> sitelist=new ArrayList<>();
        try {
            JSONArray itemArray=new JSONArray(jsonData);
            for(int i=0;i<itemArray.length();i++) {
                JSONObject obj=itemArray.getJSONObject(i);
                if(!obj.isNull("date"))
                    sitelist.add(obj.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<TongliangDataBean> getTongliangChatsData(String jsonStr, String key) {
        ArrayList<TongliangDataBean> sites=new ArrayList<>();
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key)) {
                JSONArray items=json.getJSONArray(key);
                for(int j=0;j<items.length();j++) {
                    JSONObject subJson = items.getJSONObject(j);
                    TongliangDataBean site = new TongliangDataBean();
                    if (!subJson.isNull("date"))
                        site.setDate(subJson.getString("date"));
                    if (!subJson.isNull("data"))
                        site.setValue(subJson.getString("data"));
                    sites.add(site);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sites;
    }
}
