package environment.th.com.thenvi.frg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.io.File;
import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.BookContentAct;
import environment.th.com.thenvi.adapter.AdapterCallBack;
import environment.th.com.thenvi.adapter.BookAdapter;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.FileUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BookPage  extends BaseFragment implements AdapterCallBack, AdapterView.OnItemClickListener{

    private HttpHandler handler;
    private ArrayList<BookBean> types=new ArrayList<>();
    private ArrayList<BookBean> books=new ArrayList<>();
    private ListView areaList, areaList2;
    private AreaListAdapter adapter, adapter2;
    private int selected=-1;
    private int page=1, totalPage=1, lastVisibleIndex=0;
    private TextView txt;
    private String currentType="";

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                if(method.equals(ConstantUtil.method_PDFTypes)) {
                    types = JsonUtil.getPDFTypes(jsonData);
                    adapter = new AreaListAdapter(getActivity(), types, 0);
                    areaList.setAdapter(adapter);
                }else if(method.equals(ConstantUtil.method_PDF)) {
                    String pageInfo=JsonUtil.getString(jsonData, "page");
                    page=JsonUtil.getJsonInt(pageInfo, "page");
                    totalPage=JsonUtil.getJsonInt(pageInfo, "totalPage");
                    books.addAll(JsonUtil.getPDFInfo(jsonData));
                    adapter2.notifyDataSetChanged();
                    if(page==totalPage)
                        txt.setText("已全部加载");
                    areaList2.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                                    && lastVisibleIndex == adapter2.getCount()) {
                                if (page < totalPage) {
                                    handler.getPDFlist(currentType, page+1);
                                }
                            }
                        }

                        @Override
                        public void onScroll(AbsListView absListView, int firstVisibleItem,
                                             int visibleItemCount, int totalItemCount) {
                            lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
                            if (totalItemCount == totalPage + 1) {
                                txt.setText("已全部加载");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.pdf_menulist_layout, null);
        initView(mView);
        initHandler();
        handler.getPDFType();
        return mView;
    }

    private void initView(View v) {
        areaList=(ListView)v.findViewById(R.id.areaList);
        areaList.setOnItemClickListener(this);
        areaList2=(ListView)v.findViewById(R.id.areaList2);
        areaList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), BookContentAct.class);
                intent.putExtra("URL", books.get(i).getBookUrl());
                intent.putExtra("NAME", books.get(i).getBookName());
                startActivity(intent);
            }
        });
        AbsListView.LayoutParams llp=new AbsListView.LayoutParams(-1, ScreenUtil.dip2px(getActivity(), 40));
        txt=new TextView(getActivity());
        txt.setText("加载中");
        txt.setTextSize(14);
        txt.setGravity(Gravity.CENTER);
        txt.setLayoutParams(llp);
        areaList2.addFooterView(txt);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        page=1;
        currentType=types.get(i).getType();
        books.clear();
        adapter2 = new AreaListAdapter(getActivity(), books, 1);
        areaList2.setAdapter(adapter2);
        handler.getPDFlist(currentType, page);
        selected=i;
        adapter.notifyDataSetChanged();

    }

    public class AreaListAdapter extends BaseAdapter {

        private static final String TAG = "SampleAdapter";
        ArrayList<BookBean> datalist=new ArrayList<>();

        private final LayoutInflater mLayoutInflater;
        int edge=0;
        private Activity act;
        private String picImg="";
        private int type=0;

        public AreaListAdapter(Activity context, ArrayList<BookBean> arrayList, int type) {
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
            if(type==1)
                arrowRight.setVisibility(View.GONE);

            nameTxt.setText(datalist.get(position).getBookName());

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
