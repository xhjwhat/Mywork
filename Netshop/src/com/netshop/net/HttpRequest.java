package com.netshop.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.json.xml.XMLSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.l99gson.Gson;
import com.netshop.app.NetShopApp;
import com.netshop.entity.BaseEntity;
import com.netshop.util.DESCrypto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class HttpRequest {
	public static final int REQUEST_POST = 1;
	public static final int REQUEST_GET = 0;

	public int requestType = REQUEST_GET;

	public String vi = Constants.VI;
	public String si = "";
	public String cd = "";
	public String ap = "";
	public String ps = "";
	public String pg = "";
	public String pc = "";
	public String userId = "";
	public String password = "";
	public String code="";
	public SharedPreferences preferences;
	//String tempstr = "<response description=\"获取成功\" error=\"0\"><list currentpage=\"1\" totalpage=\"1\" totalnum=\"4\"> <product pid=\"7\" pname=\"化肥7\" price=\"456\" pimg=\"http://localhost:8080/wxnhProject/upload/proImg/1430549033557.jpg\" weight=\"70\"/> <product pid=\"6\" pname=\"化肥6\" price=\"456\" pimg=\"http://localhost:8080/wxnhProject/upload/proImg/1430549033557.jpg\" weight=\"70\"/></list></response>";

	public interface HttpCallBack {
		public void success(String json);

		public void fail(String failReason);
	}

	public HttpRequest(String si, String cd) {
		this.si = si;
		this.cd = cd;
		userId = NetShopApp.getInstance().getUserId();
		password = NetShopApp.getInstance().getPassword();
		// String phoneNum = NetShopApp.getInstance().getPhoneNum();

	}

	/**
	 * 添加公共参数
	 */
	public String combineUrl() {
		try {
			ap = userId + "," + password;
			StringBuffer buffer = new StringBuffer();
			buffer.append("si=" + si + "&cd=" + cd);
			buffer.append("&ap=" + ap);
			if (!ps.equals("")) {
				buffer.append("&ps=" + ps);
			}
			if (!pg.equals("")) {
				buffer.append("&pg=" + pg);
			}
			if(!code.equals("")){
				buffer.append("&code="+ code);
			}
			if (!pc.equals("")) {
				buffer.append("&pc=" + pc);
			}
			Log.e("params", buffer.toString());
			DESCrypto des = new DESCrypto();
			String desString = des.encrypt(buffer.toString());

			return Constants.TEST_NETSHOP_URL + desString + "&vi="
					+ Constants.VI;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void request(int rquestype, HttpCallBack callback) {
		RequestTask task = new RequestTask(combineUrl(), callback);
		task.executeOnExecutor(NetShopApp.getInstance().threadPool, "");
	}

	public void request(HttpCallBack callback) {
		RequestTask task = new RequestTask(combineUrl(), callback);
		task.executeOnExecutor(NetShopApp.getInstance().threadPool, "");
	}

	public class RequestTask extends AsyncTask<String, Integer, String> {
		private String url;
		private HttpCallBack callback;

		public RequestTask(String url, HttpCallBack callback) {
			this.url = url;
			this.callback = callback;
		}

		@Override
		protected String doInBackground(String... params) {
			InputStream is = null;
			String result = "";
			try {
				HttpParams httpParams = new BasicHttpParams();
				//HttpConnectionParams.setSoTimeout(httpParams, 10000);
				HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
				HttpClient httpclient = new DefaultHttpClient(httpParams);
				Log.e("url", url);
				HttpResponse response;
				if (requestType == REQUEST_GET) {
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);

				} else {
					HttpPost httppost = new HttpPost(url);
					StringEntity httpbody = new StringEntity(params[0],
							HTTP.UTF_8);
					httppost.setEntity(httpbody);
					response = httpclient.execute(httppost);

				}
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();
				result = sb.toString();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				XMLSerializer xmls = new XMLSerializer();
				result="<response description=\"获取成功\" error=\"0\"><list><ptype name=\"粮食类作物\" id=\"1\"><clist><ctype name=\"大麦\" id=\"16\"/><ctype name=\"水稻\" id=\"17\"/></clist></ptype><ptype name=\"蔬菜类作物\" id=\"11\"><clist><ctype name=\"南瓜\" id=\"18\"/></clist></ptype><ptype name=\"水果类作物\" id=\"7\"><clist/></ptype><ptype name=\"花卉类作物\" id=\"12\"><clist/></ptype><ptype name=\"药用类作物\" id=\"13\"><clist/></ptype><ptype name=\"瓜类作物\" id=\"6\"><clist/></ptype><ptype name=\"豆类作物\" id=\"2\"><clist/></ptype><ptype name=\"干果类作物\" id=\"8\"><clist/></ptype><ptype name=\"嗜好类作物\" id=\"9\"><clist/></ptype><ptype name=\"根茎类作物\" id=\"10\"><clist/></ptype><ptype name=\"油料类作物\" id=\"3\"><clist/></ptype><ptype name=\"纤维类作物\" id=\"4\"><clist/></ptype><ptype name=\"糖料类作物\" id=\"5\"><clist/></ptype><ptype name=\"原料类作物\" id=\"14\"><clist/></ptype><ptype name=\"绿肥牧草作物\" id=\"15\"><clist/></ptype></list></response>";
				String json = xmls.read(result).toString().replace("@", "");
				Log.e("What", json);
				JSONObject jsonObject = new JSONObject(json);
				if (!jsonObject.isNull("error")) {
					if (jsonObject.optString("error").equals("0")) {
						callback.success(json);
					} else {
						callback.fail(jsonObject.optString("description"));
					}
				}
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			callback.fail("网络异常，请稍后再试");
		}

	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public String getSi() {
		return si;
	}

	public void setSi(String si) {
		this.si = si;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
