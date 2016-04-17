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
import environment.th.com.thenvi.bean.PopupInfoItem;
import environment.th.com.thenvi.bean.TongliangBean;
import environment.th.com.thenvi.bean.TongliangItem;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/17.
 */
public class TongliangAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context con;
    private ArrayList<TongliangBean> dataList;
    private int itemHeight = 0;
    private OnAreaItemClick listener;

    public TongliangAdapter(Context context, ArrayList<TongliangBean> dataList, OnAreaItemClick listener) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        con = context;
        this.itemHeight = ScreenUtil.dip2px(context, 40);
        this.listener=listener;
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

        View v = mInflater.inflate(R.layout.tongliang_listitem, null);
        TextView nameTxt1 = (TextView)v.findViewById(R.id.nameTxt1);
        TextView nameTxt2 = (TextView)v.findViewById(R.id.nameTxt2);
        LinearLayout itemLayout1 = (LinearLayout)v.findViewById(R.id.itemLayout1);
        LinearLayout itemLayout2 = (LinearLayout)v.findViewById(R.id.itemLayout2);
        LinearLayout itemLayout3 = (LinearLayout)v.findViewById(R.id.itemLayout3);
        TongliangBean bean=dataList.get(position);
        if(position==0||!bean.getFirstName().equals(dataList.get(position-1).getFirstName())){
            nameTxt1.setText(bean.getFirstName());
        }else
            nameTxt1.setText("");
        nameTxt2.setText(bean.getSecondName());
        setInfoData(itemLayout1, bean.getPositiveList().get(0));
        if(bean.getNegativeList().size()>0)
            setInfoData(itemLayout2, bean.getNegativeList().get(0));
        else
            setInfoData(itemLayout2, new TongliangItem());
        setInfoData(itemLayout3, bean.getNetList().get(0));
        nameTxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFirstNameClick(position);
            }
        });
        nameTxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSeccondNameClick(position);
            }
        });
        return v;
    }

    private void setInfoData(LinearLayout layout, TongliangItem data) {
        layout.addView(getTextView(data.getAMMONIA()));
        layout.addView(getTextView(data.getCOD()));
        layout.addView(getTextView(data.getTP()));
        layout.addView(getTextView(data.getTN()));
    }

    private TextView getTextView(String content){
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(-1, -1);
        llp.weight=1;
        TextView txt=new TextView(con);
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(Color.BLACK);
        txt.setTextSize(10);
        txt.setText(content);
        txt.setLayoutParams(llp);
        return txt;
    }

    public interface OnAreaItemClick{
        public void onFirstNameClick(int pos);
        public void onSeccondNameClick(int pos);
    }
}
