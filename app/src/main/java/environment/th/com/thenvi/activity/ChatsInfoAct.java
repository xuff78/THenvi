package environment.th.com.thenvi.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.ChatWaterSiteBean;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.chats.ChatFragment;
import environment.th.com.thenvi.chats.ChatWaterSite;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.LogUtil;

/**
 * Created by Administrator on 2016/3/16.
 */
public class ChatsInfoAct extends AppCompatActivity implements View.OnClickListener {

    private TextView startDate, endDate;
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private boolean isStart=true;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ChatFragment chartFrg;
    public static final String WaterSite="WaterSite";
    public static final String RainSite="RainSite";
    public static final String GateDamSite="GateDamSite";
    public static final String KuajieSite="KuajieSite";
    public static final String GuokongSite="GuokongSite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_layout);


        initView();
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        //获取日期
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.listLeftBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        startDate=(TextView)findViewById(R.id.startDate);
        endDate=(TextView)findViewById(R.id.endDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
//        startDate.setText(str);
        startDate.setText("2014-03-10");
        startDate.setOnClickListener(this);
//        endDate.setText(str);
        endDate.setText("2014-03-17");
        endDate.setOnClickListener(this);
        findViewById(R.id.findData).setOnClickListener(this);
        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                System.out.println("---> 设置后: year="+year+", month="+monthOfYear+",day="+dayOfMonth);
                if(isStart) {
                    startDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    endDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }else
                    endDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        };

        chartFrg=new ChatWaterSite();
        Bundle b=new Bundle();
        b.putString("startDate", startDate.getText().toString());
        b.putString("endDate", endDate.getText().toString());
        chartFrg.setArguments(b);
        addListFragment(chartFrg);
//        chartFrg.setDate(startDate.getText().toString(), endDate.getText().toString());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startDate:
                isStart=true;
                String[] dateStart=startDate.getText().toString().split("-");
                datePickerDialog=new DatePickerDialog(this, mDateSetListener, Integer.valueOf(dateStart[0]),
                        Integer.valueOf(dateStart[1])-1, Integer.valueOf(dateStart[2]));
                datePickerDialog.show();
                break;
            case R.id.endDate:
                isStart=false;
                String[] dateEnd=endDate.getText().toString().split("-");
                datePickerDialog=new DatePickerDialog(this, mDateSetListener, Integer.valueOf(dateEnd[0]),
                        Integer.valueOf(dateEnd[1])-1, Integer.valueOf(dateEnd[2]));
                datePickerDialog.show();
                break;
            case R.id.findData:
                chartFrg.setDate(startDate.getText().toString(), endDate.getText().toString());
                break;
        }

    }

    public void addListFragment(Fragment frg) {
        FragmentTransaction Transaction = getFragmentManager().beginTransaction();
        Transaction.replace(R.id.mainFrame, frg)
                .commit();
    }



    String[] formData1={"0.8", "2.2", "1.3", "1.5", "1.2", "0.7", "0.6", "0.7", "1", "1.2", "1.1", "0.7"};
    String[] formData2={"40", "40", "40", "45", "46", "48", "38", "35", "35", "40", "45", "30"};
    String[] formData3={"1.3", "1.9", "2.0", "1.4", "2.7", "1.7", "2.6", "1.2", "1.7", "0.8", "1.0", "2.3"};
    String[] formData4={"46.3", "32.2", "36.6", "75.1", "34.6", "50.2", "36.3", "32.2", "46.6", "75.1", "34.6", "50.2"};
    String[] tags={"46", "32", "36", "75", "34", "50", "36", "32", "46", "75", "34", "50"};
    private String getJsonData() {
        String jsonData="";
        try {
            JSONObject jsonObj=new JSONObject();
            JSONArray array=new JSONArray();
            for(int i=0;i<formData1.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData1[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo1", array);

            array=new JSONArray();
            for(int i=0;i<formData2.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData2[i]);
                array.put(i, objsub);
            }
            jsonObj.put("FristFormInfo2", array);

            array=new JSONArray();
            for(int i=0;i<formData3.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData3[i]);
                array.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo1", array);

            array=new JSONArray();
            for(int i=0;i<formData4.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", formData4[i]);
                array.put(i, objsub);
            }
            jsonObj.put("SecondFormInfo2", array);

            array=new JSONArray();
            for(int i=0;i<tags.length;i++){
                JSONObject objsub=new JSONObject();
                objsub.put("data", tags[i]);
                array.put(i, objsub);
            }
            jsonObj.put("tags", array);

            jsonData=jsonObj.toString();
            LogUtil.d("Weather", jsonObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
