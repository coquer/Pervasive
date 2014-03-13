package dk.jycr753.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class JsonParser {

	public static String createJsonParserObject(String stringUrlToJson) {

		DefaultHttpClient httpClient = new DefaultHttpClient(
				new BasicHttpParams());

		HttpPost httppost = new HttpPost(stringUrlToJson);
		
		InputStream inputStreamer = null;
		String finalResult = null;
		String finalLine = null;

		try {
			
			HttpResponse httpRespo = httpClient.execute(httppost);
			HttpEntity entity = httpRespo.getEntity();
			if (entity != null) {
				Log.i("GOOD", EntityUtils.toString(entity));
			} else {
				Log.i("NULL", EntityUtils.toString(entity));
			}
			inputStreamer = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStreamer, "UTF-8"), 8);
			StringBuilder stringBul = new StringBuilder();

			while ((finalLine = reader.readLine()) != null) {
				stringBul.append(finalLine + "\n");
			}
			finalResult = stringBul.toString();

			//return finalResult;

		} catch (Exception e) {
			Log.i("Sorry, Some error in here dude" + e, finalResult);
			System.out.println("FUCKING ERROR, --- " + e);

		} finally {
			try {
				if (inputStreamer != null) {
					inputStreamer.close();
				}
			} catch (Exception e) {

			}
		}
		return finalResult;

		
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
