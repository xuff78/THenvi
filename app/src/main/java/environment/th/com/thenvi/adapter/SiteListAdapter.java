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

import environment.th.com.thenvi.bean.WaterSiteBean;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SiteListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context con;
    private ArrayList<String> dataList;
    private int itemHeight=0;

    public SiteListAdapter(Context context, ArrayList<String> dataList){
        this.mInflater = LayoutInflater.from(context);
        this.dataList=dataList;
        con=context;
        this.itemHeight= ScreenUtil.dip2px(context, 40);
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

        AbsListView.LayoutParams alp=new  AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        TextView title = new TextView(con);
        title.setGravity(Gravity.CENTER);
        title.setText(dataList.get(position));
        title.setLayoutParams(alp);
        title.setTextSize(14);
        title.setTextColor(Color.BLACK);
//        title.setTag(dataList.get(position));
        return title;
    }
}
