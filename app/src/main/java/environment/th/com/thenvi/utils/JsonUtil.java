package environment.th.com.thenvi.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import environment.th.com.thenvi.bean.JsonMessage;
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
                if (!subJson.isNull("rsname"))
                    site.setRSNAME(subJson.getString("rsname"));
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
}
