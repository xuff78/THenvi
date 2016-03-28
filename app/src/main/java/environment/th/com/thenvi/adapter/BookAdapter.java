package environment.th.com.thenvi.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import environment.th.com.thenvi.R;
import environment.th.com.thenvi.activity.BookContentAct;
import environment.th.com.thenvi.bean.BookBean;
import environment.th.com.thenvi.utils.FileUtil;
import environment.th.com.thenvi.utils.LogUtil;
import environment.th.com.thenvi.utils.ScreenUtil;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BookAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    LayoutInflater listInflater;
    Activity act;
    ArrayList<BookBean> datalist;
    AdapterCallBack cb;
    ProgressDialog progress;
    File downloadingMediaFile;
    TextView currentDownloadTxt=null;
    int downloadPos=-1;

    public BookAdapter(Activity act, ArrayList<BookBean> datalist, AdapterCallBack cb)
    {
        this.act=act;
        this.datalist=datalist;
        listInflater=LayoutInflater.from(act);
        this.cb=cb;
    }

    public int getItemCount() {
        return datalist.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LogUtil.i("totp", "create: " + position);
        RecyclerView.ViewHolder holder = null;
        View v = listInflater.inflate(R.layout.book_list_item, null);
        RecyclerView.LayoutParams rlp=new RecyclerView.LayoutParams(-1, ScreenUtil.dip2px(act, 50));
        v.setLayoutParams(rlp);
        holder = new BookViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final BookViewHolder tvh = (BookViewHolder) viewHolder;
        final BookBean bean = datalist.get(position);
        tvh.titleTxt.setText(bean.getBookName());
//        if(bean.isDownload())
            tvh.timeTxt.setText("打开");
//        else
//            tvh.timeTxt.setText("下载");
        tvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cb.onClick(position, view);
                Intent intent=new Intent(act, BookContentAct.class);
                intent.putExtra("URL", bean.getBookUrl());
                intent.putExtra("NAME", bean.getBookName());
                act.startActivity(intent);
                /*if(bean.isDownload()){
                    File file = new File(FileUtil.savePath, "book"+bean.getId()+".pdf");
                    if(file.exists()) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromFile(file);
                        intent.setDataAndType(uri, "application/pdf");
                        act.startActivity(intent);
                    }
                }else {
                    progress = ProgressDialog.show(act, bean.getBookName(), "正在连接");
                    currentDownloadTxt = tvh.timeTxt;
                    downloadPos = position;
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                downloadAudioIncrement(bean.getBookUrl(), "book" + bean.getId());
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                if(downloadingMediaFile!=null&&downloadingMediaFile.exists())
                                    downloadingMediaFile.delete();
                                e.printStackTrace();
                            }
                        }

                    }.start();
                    progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            downloadingMediaFile.delete();
                            currentDownloadTxt.setText("下载");
                        }
                    });
                }*/
            }
        });
    }

    public class BookViewHolder extends RecyclerView.ViewHolder
    {
        TextView timeTxt, titleTxt;

        public BookViewHolder(View v)
        {
            super(v);
            titleTxt=(TextView)v.findViewById(R.id.bookTitle);
            timeTxt=(TextView) v.findViewById(R.id.bookInfo);
        }
    }

    // 根据获得的URL地址下载数据
    public void downloadAudioIncrement(String mediaUrl, final String filename) throws IOException {

        URLConnection cn = new URL(mediaUrl).openConnection();
        cn.connect();

        totallenth = cn.getContentLength() / 1000;// 歌曲文件长度
        totalKbRead = 0;// 已读文件长度

        InputStream stream = cn.getInputStream();
        if (stream == null) {
            LogUtil.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + mediaUrl);
        }

        downloadingMediaFile = FileUtil.getFile(filename+".pdf", act);
        int incrementalBytesRead = 0;
        FileOutputStream out = new FileOutputStream(downloadingMediaFile);
        byte buf[] = new byte[10240];
        do {
            int numread = stream.read(buf);
            if (numread <= 0) {
                progressHandler.sendEmptyMessage(0);
                break;
            }
            out.write(buf, 0, numread);
            incrementalBytesRead += numread;
            totalKbRead = incrementalBytesRead / 1000; // totalKbRead表示已经下载的文件大小
            progressHandler.sendEmptyMessage(1);
        } while (totallenth > totalKbRead);
        progressHandler.sendEmptyMessage(2);
    }

    boolean stop = false; // 是否停止
    int totallenth = 0;// 歌曲文件长度
    int totalKbRead = 0;// 已读文件长度
    int stopingPos = -1;// 正在停止的下载位置
    Handler progressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    progress.dismiss();
                    currentDownloadTxt.setText("下载");
                    break;
                case 1:
                    progress.setMax(totallenth);
                    progress.setProgress(totalKbRead);
                    progress.setMessage("下载中："+totalKbRead+"/"+totallenth+" KB");
                    break;
                case 2:
                    progress.dismiss();
                    if(currentDownloadTxt!=null)
                        currentDownloadTxt.setText("打开");
                    datalist.get(downloadPos).setDownload(true);
                    break;
            }
        }

    };
}
