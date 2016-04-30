package environment.th.com.thenvi.view;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.ChatsInfoAct;
import environment.th.com.thenvi.bean.CRiverInfoBean;
import environment.th.com.thenvi.bean.ChatGateDam;
import environment.th.com.thenvi.bean.ChatGuokong;
import environment.th.com.thenvi.bean.ChatKuaJie;
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.TongliangDataBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.chats.ChatFragment;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/20.
 */
public class ChatsDialog2 extends DialogFragment
{

    private WebView mWebView;
    ArrayList<TongliangDataBean> AMMONIAs=new ArrayList<>();
    ArrayList<TongliangDataBean> CODs=new ArrayList<>();
    ArrayList<TongliangDataBean> TNs=new ArrayList<>();
    ArrayList<TongliangDataBean> TPs=new ArrayList<>();
    String jsonData="";
    private ArrayList<String> dates=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = inflater.inflate(R.layout.chats_dialog, null);

        initView(mView);
        Bundle b=getArguments();
        jsonData=b.getString("jsonData");
        AMMONIAs=JsonUtil.getTongliangChatsData(jsonData, "AMMONIA");
        CODs=JsonUtil.getTongliangChatsData(jsonData, "COD");
        TNs=JsonUtil.getTongliangChatsData(jsonData, "TN");
        TPs=JsonUtil.getTongliangChatsData(jsonData, "TP");
        mWebView.loadUrl("file:///android_asset/tongliangchats.html");
        return mView;
    }

    private void initView(View v) {

        mWebView=(WebView)v.findViewById(R.id.mWebView);
//            mWebView.setBackgroundColor(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(getResources().getColor(R.color.hard_gray));
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.clearCache(false);
        mWebView.setFocusable(false);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String jsonString=getGateDamJsonStr();
                mWebView.loadUrl("javascript:setData('" + jsonString + "')");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public String getGateDamJsonStr() {
        String jsonData="";
        try {
            JSONObject jsonObj=new JSONObject();

            JSONArray array1=new JSONArray();
            JSONArray array2=new JSONArray();
            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<AMMONIAs.size();i++){

                JSONObject objsub=new JSONObject();
                String data1=AMMONIAs.get(i).getValue();
                if(data1.equals("0"))
                    data1="-";
                objsub.put("data", data1);
                array1.put(i, objsub);

                objsub=new JSONObject();
                String data2=CODs.get(i).getValue();
                if(data2.equals("0"))
                    data2="-";
                objsub.put("data", data2);
                array2.put(i, objsub);

                objsub=new JSONObject();
                String data3=TNs.get(i).getValue();
                if(data3.equals("0"))
                    data3="-";
                objsub.put("data", data3);
                array3.put(i, objsub);

                objsub=new JSONObject();
                String data4=TPs.get(i).getValue();
                if(data4.equals("0"))
                    data4="-";
                objsub.put("data", data4);
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=AMMONIAs.get(i).getDate();
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
}
