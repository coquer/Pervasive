package dk.jycr753.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.util.Log;

public class JsonParser  extends Activity{
	
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	static int GET = '1';
	static int POST = '2';

	public JsonParser() {

	}

	public static JSONObject getJSONFromUrl(String url,
			List<NameValuePair> params, int method) throws URISyntaxException {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		HttpEntity httpentity = null;
		HttpPost postrequest = null;
		
		
		try {
			switch (method) {
			case 1:
				if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url += "?" + paramString;
				}

				Log.e("URL", url);
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
				httpentity = httpResponse.getEntity();

				break;
			case 2:
				postrequest = new HttpPost(url);
				postrequest.setEntity(new UrlEncodedFormEntity(params,
						HTTP.UTF_8));
				httpResponse = httpClient.execute(postrequest);
				httpentity = httpResponse.getEntity();
				if (httpentity != null) {
					Log.i("RESPONSE", EntityUtils.toString(httpentity));
				} else {
					Log.i("NULL", EntityUtils.toString(httpentity));
				}
				break;
			}
			if (httpentity != null) {
				is = httpentity.getContent();
				Log.i("RESPONSE", EntityUtils.toString(httpentity));
			} else {
				Log.i("NULL", EntityUtils.toString(httpentity));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		String printit = jObj.toString();
		System.out.println("cool this is the shit "+printit);
		return jObj;
	}

	public static String createJsonParserObject(String url) throws IllegalStateException, IOException {
		System.out.println("WE ARE HERE  >>>> Parser");
		DefaultHttpClient httpClient = new DefaultHttpClient(
				new BasicHttpParams());
		HttpPost httppost = new HttpPost(url);
		HttpResponse httpRespo = httpClient.execute(httppost);
		HttpEntity entity = httpRespo.getEntity();
		InputStream inputStreamer = entity.getContent();
		String finalResult = null;
		System.out.println("OBJECT URI >>>> " + url);
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStreamer, "UTF-8"), 8);
			StringBuilder stringBul = new StringBuilder();

			String finalLine = null;
			while ((finalLine = reader.readLine()) != null) {
				stringBul.append(finalLine + "\n");
			}
			finalResult = stringBul.toString();

			return finalResult;

		} catch (Exception e) {
			Log.i("Sorry Some error in here dude" + e, finalResult);
		} finally {
			try {
				if (inputStreamer != null) {
					inputStreamer.close();
				}
			} catch (Exception e) {

			}
		}

		return null;
	}

	public static JSONObject jsonObject(String finalString)
			throws JSONException {
		if (!finalString.isEmpty()) {
			JSONObject finalJsonObject = new JSONObject(finalString);
			return finalJsonObject;
		}

		return null;
	}

	public static JSONArray jsonArray(String finalString) throws JSONException {
		if (!finalString.isEmpty()) {
			JSONArray finalJsonArray = new JSONArray(finalString);
			return finalJsonArray;
		}

		return null;
	}

	public static boolean isThisJSONAnObject(String jsonResult)
			throws JSONException {
		String jsonData = null;
		if (jsonResult.isEmpty()) {
			return false;
		} else {
			jsonData = jsonResult;
		}
		Object jsonGenericObject = new JSONTokener(jsonData).nextValue();
		if (jsonGenericObject instanceof JSONObject) {
			return true;
		} else if (jsonGenericObject instanceof JSONArray) {
			return false;
		} else {
			return false;
		}
	}

}
