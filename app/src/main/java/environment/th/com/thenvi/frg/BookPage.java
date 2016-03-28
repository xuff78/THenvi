package environment.th.com.thenvi.frg;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.io.File;
import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.adapter.AdapterCallBack;
import environment.th.com.thenvi.adapter.BookAdapter;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.http.CallBack;
import environment.th.com.thenvi.http.HttpHandler;
import environment.th.com.thenvi.utils.FileUtil;
import environment.th.com.thenvi.utils.JsonUtil;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BookPage  extends BaseFragment implements AdapterCallBack {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout refreshLayout;

    private HttpHandler handler;
    private ArrayList<BookBean> books=new ArrayList<>();

    private void initHandler() {
        handler=new HttpHandler(getActivity(), new CallBack(getActivity()){
            @Override
            public void doSuccess(String method, String jsonData) {
                books= JsonUtil.getPDFInfo(jsonData);
                BookAdapter adapter=new BookAdapter(getActivity(), books, BookPage.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.list_layout, null);
        initView(mView);
        initHandler();
        handler.getPDFlist();
        return mView;
    }

    private void initView(View v) {
        recyclerView=(RecyclerView)v.findViewById(R.id.actList);
        refreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.pullrefreshLayout);
        refreshLayout.setEnabled(false);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        datalist=new ArrayList<>();
//        for(int i=0;i<6;i++) {
//            BookBean book = new BookBean();
//            book.setBookName("测试书籍");
//            book.setBookUrl("http://www.chinapdf.com/PDF/Acrobat%208%20family.pdf");
//            File file = new File(FileUtil.savePath, "book"+book.getId()+".pdf");
//            if(file.exists()) {
//                book.setDownload(true);
//            }
//            datalist.add(book);
//        }
//        BookAdapter adapter=new BookAdapter(getActivity(), datalist,this);
//        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onClick(int pos, View v) {

    }
}
