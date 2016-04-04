package environment.th.com.thenvi.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/3/13.
 */
public class MenuPopup extends PopupWindow {

    private View conentView;
    private int width=0;

    public MenuPopup(final Activity context, ArrayList<String> stringList, AdapterView.OnItemClickListener itemClickListener) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_view, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        width=ScreenUtil.dip2px(context,100);
        this.setWidth(width);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setAnimationStyle(android.R.style.Animation_Dialog);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_blackline_graybg));
//        this.setAnimationStyle(R.style.AnimationPreview);
        ListView listview = (ListView)conentView.findViewById(R.id.popList);
        listview.setAdapter(new TitleAdapter(context, stringList));
        listview.setOnItemClickListener(itemClickListener);
    }

    public void showPopupWindow(View v) {
        if (!this.isShowing()) {
            this.showAsDropDown(v, v.getWidth()/2-width/2, 12);
        } else {
            this.dismiss();
        }
    }

    public class TitleAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context con;
        private ArrayList<String> dataList;
        private int itemHeight=0;

        public TitleAdapter(Context context, ArrayList<String> dataList){
            this.mInflater = LayoutInflater.from(context);
            this.dataList=dataList;
            con=context;
            this.itemHeight= ScreenUtil.dip2px(context, 38);
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
            title.setTextSize(13);
            title.setTextColor(Color.WHITE);
            return title;
        }

    }
}
