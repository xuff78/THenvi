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
//                if(!subJson.isNull("RSNAME"))
//                    site.setRSNAME(subJson.getString("RSNAME"));
//                if(!subJson.isNull("RSNAME"))
//                    site.setRSNAME(subJson.getString("RSNAME"));
//                if(!subJson.isNull("RSNAME"))
//                    site.setRSNAME(subJson.getString("RSNAME"));
                sitelist.add(site);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sitelist;
    }
}
