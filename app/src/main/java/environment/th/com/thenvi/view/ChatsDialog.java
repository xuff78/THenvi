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
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by Administrator on 2016/4/20.
 */
public class ChatsDialog extends DialogFragment
{

    private WebView mWebView;
    ArrayList<String> tongliangyuzhi=new ArrayList<String>();
    ArrayList<String> tongliangzhi=new ArrayList<String>();
    String name="";
    private ArrayList<String> dates=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = inflater.inflate(R.layout.chats_dialog, null);

        initView(mView);
        Bundle b=getArguments();
        tongliangyuzhi=b.getStringArrayList("tongliangyuzhi");
        tongliangzhi=b.getStringArrayList("tongliangzhi");
        dates=b.getStringArrayList("date");
        name=b.getString("name");
        mWebView.loadUrl("file:///android_asset/tongliangyujing.html");
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
                String jsonString=getTongliangChatString();
                mWebView.loadUrl("javascript:setData('" + jsonString + "')");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public String getTongliangChatString() {
        String jsonData="";
        try {
            int max=12;
            JSONObject jsonObj=new JSONObject();

            JSONArray array3=new JSONArray();
            JSONArray array4=new JSONArray();
            JSONArray array5=new JSONArray();
            for(int i=0;i<dates.size();i++){

                JSONObject objsub=new JSONObject();

                JSONObject direct=new JSONObject();
                direct.put("value", tongliangzhi.get(i));
                objsub.put("data",  direct);
                array3.put(i, objsub);

                objsub=new JSONObject();
                objsub.put("data", tongliangyuzhi.get(i));
                array4.put(i, objsub);

                objsub=new JSONObject();
                String time=dates.get(i);
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
}
