package environment.th.com.thenvi.http;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import java.util.HashMap;

import environment.th.com.thenvi.utils.ActUtil;
import environment.th.com.thenvi.utils.LogUtil;

/**
 * 要使用AsyncTask工作我们要提供三个泛型参数，并重载四个方法(至少重载一个)。<br>
 * 
 * @author hurray
 * 
 */
public class HttpAsyncTask extends AsyncTask<Object, String, String> {

	/**
	 * debug tag.
	 */
	public static final String TAG = "HttpAsyncTask";

	/**
	 * http回调对象，本类的联网结果数据需要通过HttpCallback来进行回调
	 */
	private HttpCallback mHttpCb;

	/**
	 * 请求的URL，同时也作为不同请求的唯一标识
	 */
	private String mReqMethod;
	

	/**
	 * 进度条提示语
	 */
	private String mPrgInfo = "数据努力加载中...";

	/**
	 * 用于展现UI使用，在本类中用于显示进度条
	 */
	private Activity mContext;
	private boolean isCancel=false;
	
	public boolean isShowDlg = true;
	private ProgressDialog progressDialog;

	/**
	 * 唯一构造函数
	 * 
	 * @param context Context
	 * @param httpCb HttpCallback
	 */
	public HttpAsyncTask(Activity context, HttpCallback httpCb) {
		super();
		mHttpCb = httpCb;
		mContext = context;
	}
	
	public HttpAsyncTask(Activity context, HttpCallback httpCb, boolean isShowDlg) {
		super();
		mHttpCb = httpCb;
		mContext = context;
		this. isShowDlg= isShowDlg;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ActUtil.closeSoftPan(mContext);
		if(isShowDlg) {
			progressDialog = ProgressDialog.show(mContext, "", "加载中..", true, true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setOnCancelListener(dialogCancel);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(Object... params) {
		mReqMethod = (String) params[1];
		
		if((Boolean)params[4]){
			return "";
//			return GlbsNet.doGet((String)params[0]);
		}else{
			return GlbsNet.doPostNew((String)params[0],(HashMap<String, String>)params[2]);
		}
		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		LogUtil.i(TAG, mReqMethod + "---->" + result);

		if(progressDialog!=null&&progressDialog.isShowing())
			progressDialog.dismiss();

		if(mContext!=null)
		if(!isCancel&&result!=null) {
			mHttpCb.httpCallback(mReqMethod, result.trim());
		}
		
	}
	
	OnCancelListener dialogCancel=new OnCancelListener() {
		
		@Override
		public void onCancel(DialogInterface arg0) {
			// TODO Auto-generated method stub
			isCancel=true;
		}
	};
}