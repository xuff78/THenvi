/**
 *  Author :  hmg25
 *  Description :
 */
package environment.th.com.thenvi.http;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

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

	public void getSiteList() {
		HashMap<String, String> params=new HashMap<>();
		request(ConstantUtil.method_SiteList, params, true);
	}

	public void getSiteDetail(String hsname, String rsname) {
		HashMap<String, String> params=new HashMap<>();
		params.put("hsname", hsname);
		params.put("rsname", rsname);
		params.put("date", "2014-03-31");
		request(ConstantUtil.method_SiteDetail, params, true);
	}

	public void getSiteChart(String hsname, String rsname, String beginDate, String endDate) {
		HashMap<String, String> params=new HashMap<>();
		params.put("hsname", hsname);
		params.put("rsname", rsname);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		request(ConstantUtil.method_SiteChart, params, true);
	}


	public void getRainSiteList() {
		HashMap<String, String> params=new HashMap<>();
		request(ConstantUtil.method_RainSiteList, params, true);
	}

	public void getRainSiteDetail(String hsname, String rsname) {
		HashMap<String, String> params=new HashMap<>();
		params.put("rfname", hsname);
		params.put("rsname", rsname);
		params.put("date", "2014-03-31");
		request(ConstantUtil.method_RainSiteDetail, params, true);
	}

	public void getRainSiteChart(String hsname, String rsname, String beginDate, String endDate) {
		HashMap<String, String> params=new HashMap<>();
		params.put("rfname", hsname);
		params.put("rsname", rsname);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		request(ConstantUtil.method_RainSiteChart, params, true);
	}


	public void getGateDamSiteList() {
		HashMap<String, String> params=new HashMap<>();
		request(ConstantUtil.method_GateDamSiteList, params, true);
	}

	public void getGateDamDetail(String hsname, String rsname) {
		HashMap<String, String> params=new HashMap<>();
		params.put("dname", hsname);
		params.put("rsname", rsname);
		params.put("date", "2014-03-31");
		request(ConstantUtil.method_GateDamDetail, params, true);
	}

	public void getGateDamChart(String hsname, String rsname, String beginDate, String endDate) {
		HashMap<String, String> params=new HashMap<>();
		params.put("dname", hsname);
		params.put("rsname", rsname);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		request(ConstantUtil.method_GateDamChart, params, true);
	}



	public void getKuajieSiteList() {
		HashMap<String, String> params=new HashMap<>();
		request(ConstantUtil.method_KuajieSiteList, params, true);
	}

	public void getKuajieDetail(String hsname, String rsname) {
		HashMap<String, String> params=new HashMap<>();
		params.put("river", hsname);
		params.put("provices", rsname);
		params.put("date", "2014-03-31");
		request(ConstantUtil.method_KuajieSiteDetail, params, true);
	}

	public void getKuajieChart(String hsname, String rsname, String beginDate, String endDate) {
		HashMap<String, String> params=new HashMap<>();
		params.put("river", hsname);
		params.put("provices", rsname);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		request(ConstantUtil.method_KuajieSiteChart, params, true);
	}



	public void getGuokongSiteList() {
		HashMap<String, String> params=new HashMap<>();
		request(ConstantUtil.method_GuokongSiteList, params, true);
	}

	public void getGuokongSiteDetail(String hsname, String rsname) {
		HashMap<String, String> params=new HashMap<>();
		params.put("pname", hsname);
		params.put("city", rsname);
		params.put("date", "2014-03-31");
		request(ConstantUtil.method_GuokongSiteDetail, params, true);
	}

	public void getGuokongSiteChart(String hsname, String rsname, String beginDate, String endDate) {
		HashMap<String, String> params=new HashMap<>();
		params.put("pname", hsname);
		params.put("city", rsname);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		request(ConstantUtil.method_GuokongSiteChart, params, true);
	}

	protected void request(String method, HashMap<String, String> params, boolean showDialog) {
		String progressInfo = "";
		String url= ConstantUtil.Api_Url+method;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			LogUtil.i("HttpAsyncTask", entry.getKey() + "--->" + entry.getValue());
		}
		LogUtil.i("HttpAsyncTask","url: "+url);
		new HttpAsyncTask(mContext, this, showDialog)
				.execute(url, method, params, progressInfo, false);
	}
}
