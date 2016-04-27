package environment.th.com.thenvi.frg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.BookContentAct;
import environment.th.com.thenvi.activity.BookListAct;
import environment.th.com.thenvi.adapter.AdapterCallBack;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.bean.BookCate;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by Administrator on 2016/4/27.
 */
public class BookPage2   extends BaseFragment implements AdapterCallBack, AdapterView.OnItemClickListener{

    private HttpHandler handler;
    private ArrayList<BookCate> types=new ArrayList<>();
    private ListView areaList, areaList2;
    private AreaListAdapter adapter, adapter2;
    private int selected=-1;
    private int page=1, totalPage=1, lastVisibleIndex=0;
    private TextView txt;
    private String currentType="";
    private String subject="";

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                if(method.equals(ConstantUtil.method_PDFCateEv)) {
                    types = JsonUtil.getPDFTypesEv(jsonData);
                    adapter = new AreaListAdapter(getActivity(), types, 0);
                    areaList.setAdapter(adapter);
                }else if(method.equals(ConstantUtil.method_PDFEv)) {
                    Intent intent=new Intent(getActivity(), BookContentAct.class);
                    intent.putExtra("jsonData", jsonData);
                    intent.putExtra("subject", subject);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.pdf_menulist_layout, null);
        initView(mView);
        initHandler();
        handler.getPDFTypeEv();
        return mView;
    }

    private void initView(View v) {
        areaList=(ListView)v.findViewById(R.id.areaList);
        areaList.setOnItemClickListener(this);
        areaList2=(ListView)v.findViewById(R.id.areaList2);
        areaList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subject=types.get(i).getName();
                Intent intent=new Intent(getActivity(), BookListAct.class);
                intent.putExtra("name", types.get(selected).getCates().get(i).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        page=1;
        currentType=types.get(i).getNextType();
        if(currentType.equals("cate")) {
            adapter2 = new AreaListAdapter(getActivity(), types.get(i).getCates(), 2);
            areaList2.setAdapter(adapter2);
        }else{
            subject=types.get(i).getName();
            Intent intent=new Intent(getActivity(), BookListAct.class);
            intent.putExtra("name", subject);
            startActivity(intent);
        }
//        handler.getPDFlist(currentType, page);
        selected=i;

    }

    public class AreaListAdapter extends BaseAdapter {

        private static final String TAG = "SampleAdapter";
        ArrayList<BookCate> datalist=new ArrayList<>();

        private final LayoutInflater mLayoutInflater;
        int edge=0;
        private Activity act;
        private String picImg="";
        private int type=0;

        public AreaListAdapter(Activity context, ArrayList<BookCate> arrayList, int type) {
            mLayoutInflater = LayoutInflater.from(context);
            edge= ScreenUtil.dip2px(context, 45);
            this.act=context;
            this.datalist=arrayList;
            this.type=type;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            convertView = mLayoutInflater.inflate(R.layout.area_select_listitem, parent, false);
            TextView addrTxt=(TextView) convertView.findViewById(R.id.addrTxt);
            TextView nameTxt=(TextView) convertView.findViewById(R.id.nameTxt);
            View arrowRight=convertView.findViewById(R.id.arrowRight);
            if(selected==position)
                convertView.setBackgroundColor(Color.WHITE);
            else
                convertView.setBackgroundColor(Color.TRANSPARENT);
            if(datalist.get(position).getNextType().equals("list"))
                arrowRight.setVisibility(View.GONE);

            nameTxt.setText(datalist.get(position).getName());

            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }




    @Override
    public void onClick(int pos, View v) {

    }
}
