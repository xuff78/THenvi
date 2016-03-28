package environment.th.com.thenvi.chats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
import environment.th.com.thenvi.bean.RiverInfoBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.frg.BaseFragment;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ChatWaterSite extends ChatFragment {

    private WebView mWebView;
    private WaterSiteBean site;
    private RiverInfoBean Riversite;
    private CRiverInfoBean Criversite;
    private HttpHandler handler;
    private ArrayList<ChatWaterSiteBean> datalist=new ArrayList<>();
    private int requestType=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.chat_detail_layout, null);


        Intent intent=getActivity().getIntent();
        if(intent.hasExtra(ChatsInfoAct.WaterSite)){
            site= (WaterSiteBean) intent.getSerializableExtra(ChatsInfoAct.WaterSite);
            requestType=0;
        }else if(intent.hasExtra(ChatsInfoAct.RainSite)){
            site= (WaterSiteBean) intent.getSerializableExtra(ChatsInfoAct.RainSite);
            requestType=1;
        }else if(intent.hasExtra(ChatsInfoAct.GateDamSite)){
            site= (WaterSiteBean) intent.getSerializableExtra(ChatsInfoAct.GateDamSite);
            requestType=2;
        }else if(intent.hasExtra(ChatsInfoAct.KuajieSite)){
            Riversite = (RiverInfoBean) intent.getSerializableExtra(ChatsInfoAct.KuajieSite);
            requestType=3;
        }else if(intent.hasExtra(ChatsInfoAct.GuokongSite)){
            Criversite = (CRiverInfoBean) intent.getSerializableExtra(ChatsInfoAct.GuokongSite);
            requestType=4;
        }
        initHandler();
        initView(mView);
        Bundle b=getArguments();
        setDate(b.getString("startDate"), b.getString("endDate"));
        return mView;
    }

    public void setDate(String startDate, String endDate){
        switch (requestType){
            case 0:
                handler.getSiteChart(site.getHSNAME(), site.getRSNAME(), startDate, endDate);
                break;
            case 1:
                handler.getRainSiteChart(site.getHSNAME(), site.getRSNAME(), startDate, endDate);
                break;
            case 2:
                handler.getGateDamChart(site.getHSNAME(), site.getRSNAME(), startDate, endDate);
                break;
            case 3:
                handler.getKuajieChart(Riversite.getRIVER(), Riversite.getPROVICES(), startDate, endDate);
                break;
            case 4:
                handler.getGuokongSiteChart(Criversite.getPNAME(), Criversite.getCITY(), startDate, endDate);
                break;
        }
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
                String jsonString="";
                switch (requestType){
                    case 0:
                        jsonString=JsonUtil.getWaterSiteJsonStr(datalist);
                        break;
                    case 1:
                        jsonString=JsonUtil.getRainSiteJsonStr(datalist);
                        break;
                    case 2:
                        jsonString=JsonUtil.getWaterSiteJsonStr(datalist);
                        break;
                    case 3:
                        jsonString=JsonUtil.getWaterSiteJsonStr(datalist);
                        break;
                    case 4:
                        jsonString=JsonUtil.getWaterSiteJsonStr(datalist);
                        break;
                }
                mWebView.loadUrl("javascript:setData('" + jsonString + "')");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                switch (requestType){
                    case 0:
                        datalist= JsonUtil.getChatWateSite(jsonData);
                        mWebView.loadUrl("file:///android_asset/watersite.html");
                        break;
                    case 1:
                        datalist= JsonUtil.getChatWateSite(jsonData);
                        mWebView.loadUrl("file:///android_asset/rainsite.html");
                        break;
                    case 2:
                        mWebView.loadUrl("file:///android_asset/gatedamsite.html");
                        break;
                    case 3:
                        mWebView.loadUrl("file:///android_asset/form2.html");
                        break;
                    case 4:
                        mWebView.loadUrl("file:///android_asset/form2.html");
                        break;
                }
            }
        });
    }


}
