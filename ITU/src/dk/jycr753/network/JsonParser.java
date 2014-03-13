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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {
	
	static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static int GET = '1';
    static int POST ='2';

    public JsonParser() {
    	
    }

    public static JSONObject getJSONFromUrl(String url, List<NameValuePair> params, int method) throws URISyntaxException  {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        HttpEntity httpentity = null;
        HttpPost postrequest = null;

        try {
            switch (method) {
            case 1: 
                if(params!=null){
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;   
                }

                Log.e("URL", url);
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
                httpentity = httpResponse.getEntity();          

                break;
            case 2: 
                postrequest = new HttpPost(url);
                postrequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                httpResponse = httpClient.execute(postrequest);
                httpentity = httpResponse.getEntity();
                if(httpentity !=null)
                {
                    Log.i("RESPONSE", EntityUtils.toString(httpentity));
                }
                else{
                    Log.i("NULL", EntityUtils.toString(httpentity));
                }
                break;
            }
            if(httpentity != null)
            {
                is = httpentity.getContent();
                Log.i("RESPONSE", EntityUtils.toString(httpentity));
            } else {
                Log.i("NULL", EntityUtils.toString(httpentity));
            }
        } 
        catch (UnsupportedEncodingException e) {
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
        return jObj;
    }
}
