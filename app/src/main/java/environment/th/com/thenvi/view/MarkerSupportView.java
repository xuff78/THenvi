package environment.th.com.thenvi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.adapter.PopupInfoAdapter;
import environment.th.com.thenvi.bean.PopupInfoItem;

/**
 * 显示公共设施状况详情
 * Created by haiyunlong on 2015/11/27.
 */
public class MarkerSupportView extends View implements View.OnClickListener {

    private View contentView;
    private Activity mContext;
    private ListView infoList;
    private TextView titleTxt;
    private String title;
    private int columnSize=1;
    private  float density;
    private ArrayList<PopupInfoItem> datas;

    public MarkerSupportView(Activity context, String title) {
        super(context);
        this.mContext=context;
        this.title=title;
    }

    public MarkerSupportView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarkerSupportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View getMarkerContentView(){
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.mark_detail_view, null);
        setOnClickListener(this);
        titleTxt = (TextView) contentView.findViewById(R.id.titleTxt);
        titleTxt.setText(title);
        infoList = (ListView) contentView.findViewById(R.id.infoList);


        //获取屏幕大小，以及图片的数量，来控制滚动图片的容器宽度
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) (display.getWidth() * 0.6), (int) (display.getHeight() * 0.4));
        infoList.setLayoutParams(layoutParams);
        return contentView;
    }

    public void setListView(ArrayList<PopupInfoItem> datas){
        this.datas=datas;
        infoList.setAdapter(new PopupInfoAdapter(mContext, datas));
    }

    @Override
    public void onClick(View v) {

    }
}
