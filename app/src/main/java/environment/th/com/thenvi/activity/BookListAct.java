package environment.th.com.thenvi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.bean.BookCate;
import environment.th.com.thenvi.bean.JsonMessage;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/27.
 */
public class BookListAct extends AppCompatActivity {

    private TextView subjectTxt, txt;
    private ListView bookList;
    private HttpHandler handler;
    private int pdfType=0; //0化学品， 1法律法规
    private int listType=0; //0目录， 1是pdf信息
    private int page=1, totalPage=1;
    private String currentId;
    private AreaListAdapter adapter;
    private boolean onloading=false;
    private ArrayList<BookBean> books=new ArrayList<>();
    private ArrayList<BookCate> cates=new ArrayList<>();

    private void initHandler() {
        handler=new HttpHandler(this, new CallBack(this){
            @Override
            public void doSuccess(String method, String jsonData) {
                onloading=false;
                if(method.equals(ConstantUtil.method_PDFEv)) {
                    books=JsonUtil.getPDFListEv(jsonData);
                    bookList.setAdapter(new AreaListAdapter(BookListAct.this, books));
                }else if(method.equals(ConstantUtil.method_PDF)) {
                    String pageInfo=JsonUtil.getString(jsonData, "page");
                    page=JsonUtil.getJsonInt(pageInfo, "page");
                    totalPage=JsonUtil.getJsonInt(pageInfo, "totalPage");
                    ArrayList<BookBean> temps=JsonUtil.getPDFInfo(jsonData);
                    books.addAll(temps);
                    adapter.notifyDataSetChanged();
                    if(page==totalPage||temps.size()==0)
                        txt.setText("已全部加载");
                    bookList.setOnScrollListener(new AbsListView.OnScrollListener() {
                        int lastVisibleIndex=0;
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                                    && lastVisibleIndex == adapter.getCount()) {
                                if (page < totalPage) {
                                    if(!onloading) {
                                        onloading=true;
                                        handler.getPDFlist(currentId, page + 1);
                                    }
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

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                onloading=false;
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                onloading=false;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_act);

        pdfType=getIntent().getIntExtra("pdfType", 0);
        listType=getIntent().getIntExtra("listType", 0);
        currentId=getIntent().getStringExtra("id");
        String name=getIntent().getStringExtra("name");
        initHandler();
        initView();
        subjectTxt.setText(name);
        if(pdfType==0) {
            AbsListView.LayoutParams llp=new AbsListView.LayoutParams(-1, ScreenUtil.dip2px(this, 40));
            txt=new TextView(this);
            txt.setText("加载中");
            txt.setTextSize(14);
            txt.setGravity(Gravity.CENTER);
            txt.setLayoutParams(llp);
            bookList.addFooterView(txt);
            adapter = new AreaListAdapter(this, books);
            bookList.setAdapter(adapter);
            handler.getPDFlist(currentId, page);
        }else if(pdfType==1) {
            if(listType==0){
                cates= ((BookCate) getIntent().getSerializableExtra(BookCate.Name)).getCates();
                CateListAdapter cateListAdapter = new CateListAdapter(this, cates);
                bookList.setAdapter(cateListAdapter);
            }else {
                handler.getPDFlistEv(name, 1);
            }

        }
    }

    private void initView() {
        subjectTxt=(TextView)findViewById(R.id.subjectTxt);
        bookList= (ListView) findViewById(R.id.bookList);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(pdfType==0) {
                    Intent intent = new Intent(BookListAct.this, BookContentAct.class);
                    intent.putExtra("URL", books.get(i).getBookUrl());
                    intent.putExtra("NAME", books.get(i).getBookName());
                    startActivity(intent);
                }else if(pdfType==1) {
                    if(listType==0){
                        String subject=cates.get(i).getName();
                        Intent intent=new Intent(BookListAct.this, BookListAct.class);
                        intent.putExtra("pdfType", 1);
                        intent.putExtra("listType", 1);
                        intent.putExtra("id", subject);
                        intent.putExtra("name", subject);
                        startActivity(intent);
                    }else if(listType==1){
                        Intent intent = new Intent(BookListAct.this, BookContentAct.class);
                        intent.putExtra("URL", books.get(i).getBookUrl());
                        intent.putExtra("NAME", books.get(i).getBookName());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public class AreaListAdapter extends BaseAdapter {

        private static final String TAG = "SampleAdapter";
        ArrayList<BookBean> datalist=new ArrayList<>();

        private final LayoutInflater mLayoutInflater;
        int edge=0;
        private Activity act;
        private String picImg="";

        public AreaListAdapter(Activity context, ArrayList<BookBean> arrayList) {
            mLayoutInflater = LayoutInflater.from(context);
            edge= ScreenUtil.dip2px(context, 45);
            this.act=context;
            this.datalist=arrayList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            convertView = mLayoutInflater.inflate(R.layout.area_select_listitem, parent, false);
            TextView addrTxt=(TextView) convertView.findViewById(R.id.addrTxt);
            TextView nameTxt=(TextView) convertView.findViewById(R.id.nameTxt);
            View arrowRight=convertView.findViewById(R.id.arrowRight);

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

    public class CateListAdapter extends BaseAdapter {

        private static final String TAG = "SampleAdapter";
        ArrayList<BookCate> datalist=new ArrayList<>();

        private final LayoutInflater mLayoutInflater;
        int edge=0;
        private Activity act;
        private String picImg="";

        public CateListAdapter(Activity context, ArrayList<BookCate> arrayList) {
            mLayoutInflater = LayoutInflater.from(context);
            edge= ScreenUtil.dip2px(context, 45);
            this.act=context;
            this.datalist=arrayList;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            convertView = mLayoutInflater.inflate(R.layout.area_select_listitem, parent, false);
            TextView addrTxt=(TextView) convertView.findViewById(R.id.addrTxt);
            TextView nameTxt=(TextView) convertView.findViewById(R.id.nameTxt);
            View arrowRight=convertView.findViewById(R.id.arrowRight);
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
}
