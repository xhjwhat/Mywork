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
import com.netshop.entity.BaseEntity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public abstract class HttpRequest<T extends BaseEntity> {
	public String cd;
	public static final int REQUEST_POST = 1;
	public static final int REQUEST_GET = 0;
	private static int MAX_HTTP_THREAD_COUNT = 5;
	public int requestType = 0;
	String tempstr = "<response description=\"获取成功\" error=\"0\"><list currentpage=\"1\" totalpage=\"1\" totalnum=\"4\"> <product pid=\"7\" pname=\"化肥7\" price=\"456\" pimg=\"http://localhost:8080/wxnhProject/upload/proImg/1430549033557.jpg\" weight=\"70\"/> <product pid=\"6\" pname=\"化肥6\" price=\"456\" pimg=\"http://localhost:8080/wxnhProject/upload/proImg/1430549033557.jpg\" weight=\"70\"/></list></response>";
//	private static ExecutorService ThreadPool = Executors.newFixedThreadPool(
//			MAX_HTTP_THREAD_COUNT, new ThreadFactory() {
//				@Override
//				public Thread newThread(Runnable r) {
//					AtomicInteger ints = new AtomicInteger(0);
//					return new Thread(r, "httpThreadPool#"
//							+ ints.getAndIncrement());
//				}
//			});

	public interface HttpCallBack{
		public void success(Object obj);
		public void fail(String failReason);
	}
	/**
	 * 添加公共参数
	 */
	public abstract String combineUrl();
	public abstract Object pasreRespone(String json); 
	
	public void request(int rquestype,HttpCallBack callback){
		RequestTask task = new RequestTask(combineUrl(),callback);
		//task.executeOnExecutor(ThreadPool, "");
		task.execute("");
	}
	
	
	
	public class RequestTask extends AsyncTask<String, Integer, String> {
		private String url;
		private HttpCallBack callback;
		public RequestTask(String url,HttpCallBack callback){
			this.url = url;
			this.callback = callback;
		}
		@Override
		protected String doInBackground(String... params) {
			InputStream is = null;
			String result = tempstr;
//			try {
//				HttpParams httpParams = new BasicHttpParams();
//				HttpConnectionParams.setSoTimeout(httpParams, 8000);
//				HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
//				HttpClient httpclient = new DefaultHttpClient(httpParams);
//				HttpResponse response;
//				if (requestType == REQUEST_GET) {
//					HttpGet httpget = new HttpGet(url);
//					response = httpclient.execute(httpget);
//
//				} else {
//					HttpPost httppost = new HttpPost(url);
////					StringEntity httpbody = new StringEntity(params[0],HTTP.UTF_8);
////					httppost.setEntity(httpbody);
//					response = httpclient.execute(httppost);
//
//				}
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(is, "UTF-8"));
//				StringBuilder sb = new StringBuilder();
//				String line = null;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//				}
//				is.close();
//				result = sb.toString();
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			XMLSerializer xmls = new XMLSerializer();
			String json = xmls.read(result).toString().replace("@", "");
			Log.e("What", json);
			try {
				JSONObject jsonObject = new JSONObject(json);
				if (!jsonObject.isNull("error")) {
					if(jsonObject.optString("error").equals("0")){
						callback.success(pasreRespone(json));
					}else{
						callback.fail(jsonObject.optString("description"));
					}
				}
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			callback.fail("网络异常，请稍后再试");
		}

	}

}
