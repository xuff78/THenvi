package environment.th.com.thenvi.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.ChatGateDam;
import environment.th.com.thenvi.bean.ChatGuokong;
import environment.th.com.thenvi.bean.ChatKuaJie;
import environment.th.com.thenvi.bean.ChatRainFall;
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
import environment.th.com.thenvi.bean.JsonMessage;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WaterSiteBean;

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

    public static ArrayList<ChatKuaJie> getChatKuajie(String jsonData) {
        ArrayList<ChatKuaJie> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatKuaJie site=new ChatKuaJie();
                if(!subJson.isNull("NN"))
                    site.setNN(subJson.getString("NN"));
                if(!subJson.isNull("CODN"))
                    site.setCODN(subJson.getString("CODN"));
                if(!subJson.isNull("NDN"))
                    site.setNDN(subJson.getString("NDN"));
                if(!subJson.isNull("PN"))
                    site.setPN(subJson.getString("PN"));
                if(!subJson.isNull("FLOW"))
                    site.setFLOW(subJson.getString("FLOW"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }

    public static ArrayList<ChatGuokong> getChatGuokong(String jsonData) {
        ArrayList<ChatGuokong> sitelist=new ArrayList<>();
        try {
            JSONArray array=new JSONArray(jsonData);
            for(int i=0;i<array.length();i++){
                JSONObject subJson=array.getJSONObject(i);
                ChatGuokong site=new ChatGuokong();
                if(!subJson.isNull("CLCLE"))
                    site.setCLCLE(subJson.getString("CLCLE"));
                if(!subJson.isNull("PH"))
                    site.setPH(subJson.getString("PH"));
                if(!subJson.isNull("DO"))
                    site.setDO(subJson.getString("DO"));
                if(!subJson.isNull("CODMN"))
                    site.setCODMN(subJson.getString("CODMN"));
                if(!subJson.isNull("NH3N"))
                    site.setNH3N(subJson.getString("NH3N"));
                if(!subJson.isNull("WQUALITY"))
                    site.setWQUALITY(subJson.getString("WQUALITY"));
                if(!subJson.isNull("LQUALITY"))
                    site.setLQUALITY(subJson.getString("LQUALITY"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }
}
