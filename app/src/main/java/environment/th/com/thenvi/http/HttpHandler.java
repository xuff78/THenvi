/**
 *  Author :  hmg25
 *  Description :
 */
package environment.th.com.thenvi.http;

import android.app.Activity;

import java.util.HashMap;

import environment.th.com.thenvi.utils.ConstantUtil;
import environment.th.com.thenvi.utils.LogUtil;

/**
 * hmg25's android Type
 * 
 * @author Administrator
 * 
 */
public class HttpHandler extends Handle {

	private Activity mContext;

	public HttpHandler(Activity mContext, CallBack mCallBack) {
		super(mContext, mCallBack);
		this.mContext = mContext;
	}

	/**
	 * 获取所有列表
	 * 
	 */
	public void userLogin(String name, String password) {
		HashMap<String, String> params=new HashMap<>();
		params.put("name", name);
		params.put("password", password);
		request(ConstantUtil.method_Login, params, true);
	}


	protected void request(String method, HashMap<String, String> params, boolean showDialog) {
		String progressInfo = "";
		String url= ConstantUtil.Api_Url+method;
		LogUtil.i("HttpAsyncTask","url: "+url);
		new HttpAsyncTask(mContext, this, showDialog)
				.execute(url, method, params, progressInfo, false);
	}
}
