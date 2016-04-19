package environment.th.com.thenvi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.TongliangBean;
import environment.th.com.thenvi.bean.TongliangItem;
import environment.th.com.thenvi.bean.TongliangYujingBean;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/18.
 */
public class TongliangYujingAdapter   extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context con;
    private ArrayList<TongliangYujingBean> dataList;
    private int itemHeight = 0;

    public TongliangYujingAdapter(Context context, ArrayList<TongliangYujingBean> dataList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.yujing_listitem, null);
        TextView nameTxt1 = (TextView)v.findViewById(R.id.nameTxt1);
        TextView dataTxt1 = (TextView)v.findViewById(R.id.dataTxt1);
        TextView dataTxt2 = (TextView)v.findViewById(R.id.dataTxt2);
        TextView dataTxt3 = (TextView)v.findViewById(R.id.dataTxt3);
        TextView dataTxt4 = (TextView)v.findViewById(R.id.dataTxt4);

        TongliangYujingBean bean=dataList.get(position);
        nameTxt1.setText(bean.getProvinceName());
        dataTxt1.setText(bean.getDuanMianName());
        dataTxt2.setText(bean.getTongLiangYuZhi());
        dataTxt3.setText(bean.getChaoBiaoTongLiang());
        dataTxt4.setText(bean.getChaoBiaoBeiShu());

        return v;
    }

}
