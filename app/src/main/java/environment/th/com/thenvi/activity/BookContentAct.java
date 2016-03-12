package environment.th.com.thenvi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import environment.th.com.thenvi.R;

/**
 * Created by Administrator on 2016/3/11.
 */
public class BookContentAct extends AppCompatActivity {

    WebView mWebView;
    String testUrl="http://www.chinapdf.com/PDF/Acrobat%208%20family.pdf";
    String example="https://cors-anywhere.herokuapp.com/http://bhpr.hrsa.gov/healthworkforce/rnsurveys/rnsurveyfinal.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_content_act);

        initView();
    }

    private void initView() {
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
        mWebView.loadUrl("file:///android_asset/web/viewer.html?file="+"adobe8redader.pdf");
    }
}
