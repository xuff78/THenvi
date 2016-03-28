package environment.th.com.thenvi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ActUtil;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by Administrator on 2016/3/11.
 */
public class BookContentAct extends AppCompatActivity {

    WebView mWebView;
    String testUrl="http://120.25.248.182:9090/web/viewer.html?file=001-2.pdf";
    String example="https://cors-anywhere.herokuapp.com/http://bhpr.hrsa.gov/healthworkforce/rnsurveys/rnsurveyfinal.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_content_act);

        initView();
    }

    private void initView() {
        TextView titleTxt=(TextView)findViewById(R.id.titleTxt);
        titleTxt.setText(getIntent().getStringExtra("NAME"));
        mWebView=(WebView)findViewById(R.id.mWebView);
//            mWebView.setBackgroundColor(1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.clearCache(false);
        mWebView.setFocusable(false);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(getIntent().getStringExtra("URL"));
//        mWebView.loadUrl("file:///android_asset/web/viewer.html?file="+"adobe8redader.pdf");
    }
}
