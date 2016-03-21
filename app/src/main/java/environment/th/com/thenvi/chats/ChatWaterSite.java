package environment.th.com.thenvi.chats;

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
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
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
    private HttpHandler handler;
    private ArrayList<ChatWaterSiteBean> datalist=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.chat_detail_layout, null);

        site= (WaterSiteBean) getActivity().getIntent().getSerializableExtra(WaterSiteBean.Name);
        initHandler();
        initView(mView);
        Bundle b=getArguments();
        setDate(b.getString("startDate"), b.getString("endDate"));
        return mView;
    }

    public void setDate(String startDate, String endDate){
        handler.getSiteChart(site.getHSNAME(), site.getRSNAME(), startDate, endDate);
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
                mWebView.loadUrl("javascript:setData('" + getJsonStr() + "')");
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
                datalist= JsonUtil.getChatWateSite(jsonData);
                mWebView.loadUrl("file:///android_asset/form2.html");
            }
        });
    }

    private String getJsonStr() {
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
}
