package environment.th.com.thenvi.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import environment.th.com.thenvi.utils.LogUtil;
import environment.th.com.thenvi.utils.URLUtil;


/**
 * 网络连接管理类：判断网络状态、管理网络连接、发起GET和POST请求。
 * 
 * @author heyongbo@infohold.com.cn
 */

public final class GlbsNet {
	
	public static final String HTTP_ERROR_MESSAGE = "抱歉，您的网络无法连通，请您检查网络设置后重试。";

	private static ConnectivityManager sConnManager;

	private static long CONNECTION_TIMEOUT = 40;// 设置超时时间，秒单位
	
	/**
	 * 向指定URI，发起一个POST请求，并返回服务器响应的json串。
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数集
	 * @return 返回服务器响应的json串。网络异常时，返回{@code null}。
	 */
	public static String doPostNew(String uri, HashMap<String, String> params) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection urlConn = null;
		String result="";
		try {
			urlConn = getURLConnection(uri);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.connect();
			//DataOutputStream流
			DataOutputStream out=new DataOutputStream(urlConn.getOutputStream());
			//要上传的参数
			String content= URLUtil.map2string(params);
			out.writeBytes(content);
			//刷新、关闭
			out.flush();
			out.close();
			
			//得到读取的内容（流）
			isr=new InputStreamReader(urlConn.getInputStream());
			//为输出创建BufferedReader
			br=new BufferedReader(isr);
			String inputLine=null;
			while ((inputLine=br.readLine())!=null) {
				result+=inputLine;
			}
			LogUtil.d("TestDemo", result);
		} catch (Exception e) {
			result=HTTP_ERROR_MESSAGE;
			LogUtil.e("NetError", e.getMessage());
		} finally {
			try {
				if (isr != null)
					isr.close();
				if (br != null)
					br.close();
				if(urlConn!=null)
					urlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static HttpURLConnection getURLConnection(String url) throws Exception {
		String proxyHost = Proxy.getDefaultHost();
		if (proxyHost != null) {
			java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
					new InetSocketAddress(Proxy.getDefaultHost(),
							Proxy.getDefaultPort()));

			return (HttpURLConnection) new URL(url).openConnection(p);

		} else {
			return (HttpURLConnection) new URL(url).openConnection();
		}
	}

	private static boolean isWapNetwork() {
		final String proxyHost = Proxy.getDefaultHost();
		return !TextUtils.isEmpty(proxyHost);
	}
	
	public static boolean isUsedWifi(Context con) {
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        boolean isUsedWifi = false;// wifiManager.isWifiEnabled();
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            isUsedWifi = true;
        }
        if (isUsedWifi && wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * 网络中断监听。
	 */
	public interface OnNetDisconnListener {
		/**
		 * 当网络中断时，执行此方法。
		 */
		void onNetDisconnected();
	}

}