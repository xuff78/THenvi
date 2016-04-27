package environment.th.com.thenvi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.bean.BookCate;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.JsonUtil;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by 可爱的蘑菇 on 2016/4/27.
 */
public class BookListAct extends AppCompatActivity {

    private TextView subjectTxt;
    private ListView bookList;
    private HttpHandler handler;
    private ArrayList<BookBean> books=new ArrayList<>();

    private void initHandler() {
        handler=new HttpHandler(this, new CallBack(this){
            @Override
            public void doSuccess(String method, String jsonData) {
                if(method.equals(ConstantUtil.method_PDFEv)) {
                    books=JsonUtil.getPDFListEv(jsonData);
                    bookList.setAdapter(new AreaListAdapter(BookListAct.this, books));
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list_act);

        String name=getIntent().getStringExtra("name");
        initHandler();
        initView();
        handler.getPDFlistEv(name, 1);
        subjectTxt.setText(name);
    }

    private void initView() {
        subjectTxt=(TextView)findViewById(R.id.subjectTxt);
        bookList= (ListView) findViewById(R.id.bookList);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(BookListAct.this, BookContentAct.class);
                intent.putExtra("URL", books.get(i).getBookUrl());
                intent.putExtra("NAME", books.get(i).getBookName());
                startActivity(intent);
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
}
