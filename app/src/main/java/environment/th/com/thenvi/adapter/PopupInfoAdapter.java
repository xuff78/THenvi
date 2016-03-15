package environment.th.com.thenvi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.PopupInfoItem;
import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by Administrator on 2016/3/15.
 */
public class PopupInfoAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context con;
    private ArrayList<PopupInfoItem> dataList;
    private int itemHeight = 0;

    public PopupInfoAdapter(Context context, ArrayList<PopupInfoItem> dataList) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        con = context;
        this.itemHeight = ScreenUtil.dip2px(context, 40);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.form_info_item, null);
        TextView title = (TextView)v.findViewById(R.id.nameTxt);
        TextView contentTxt = (TextView)v.findViewById(R.id.contentTxt);
        title.setText(dataList.get(position).getName());
        contentTxt.setText(dataList.get(position).getContent());
        return v;
    }
}