package com.example.pulltorefreshsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {
	private PullToRefreshListView listView;
	private OnItemClickListener clickListener;
	private List<Cars> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carList = new ArrayList<Cars>();
        
        listView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new CarsAsyncTask().execute();
			}
		});
    }
    
    final OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Object object = arg0.getItemAtPosition(arg2);
			Cars cars = (Cars) object;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(cars.getCarUrl()));
			startActivity(intent);
		}
	};
    
    private class CarsAsyncTask extends AsyncTask<Void, Integer, ArrayList<Cars>>{

		@Override
		protected void onPreExecute() {
			carList = new ArrayList<Cars>();
		}

		@Override
		protected ArrayList<Cars> doInBackground(Void... params) {
			ArrayList<Cars> cars = new ArrayList<Cars>();
			try {
				String jsonString = parseJSON();
				try {
					JSONArray entries = new JSONArray(jsonString.substring(jsonString.indexOf("[")));
					Map<Integer,Cars> tree = new TreeMap<Integer,Cars>();
					for (int i = 0; i < entries.length(); ++i){
						JSONObject object = entries.getJSONObject(i);
						String urlIcon = object.getString("make_icon");
						Bitmap bmp = downloadBitmap(urlIcon);
						tree.put(object.getInt("id"), 
								new Cars(object.getString("id"),object.getString("name"),object.getString("url"),bmp));
						
					}
					
					for(Entry<Integer, Cars> entry : tree.entrySet()){
						Integer key = entry.getKey();
						Cars value = entry.getValue();
						cars.add(value);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			return cars;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Cars> result) {
			listView.onRefreshComplete();
			CarsAdapter adapter = new CarsAdapter(getBaseContext(), R.layout.cars_row_item, result);
			adapter.notifyDataSetChanged();
			listView.setAdapter(adapter);
			super.onPostExecute(result);
		}

		private String parseJSON() throws ClientProtocolException, IOException{
			String json = "";
			final HttpClient client = new DefaultHttpClient();
			final HttpGet get = new HttpGet("http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make");
			final HttpResponse httpResponse = client.execute(get);
			json = parseJSON(httpResponse);
			
			return json;
		}
		
		private String parseJSON(HttpResponse response) throws IllegalStateException, IOException{
			String jsonResponse = "";
			final InputStream inputStream = response.getEntity().getContent();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			final StringBuilder sb = new StringBuilder();
			String line = "";
			
			while((line = reader.readLine()) != null){
				 sb.append(line + "\n");
			}
			
			inputStream.close();
			jsonResponse = sb.toString();
			return jsonResponse;
		}
		
		@SuppressWarnings("deprecation")
		private Bitmap downloadBitmap(String bitmapUrl) throws IOException{
			Bitmap bitmap = null;
			URL uri = null;
			Drawable drawable = null;
			uri = new URL(bitmapUrl);
			HttpURLConnection con = (HttpURLConnection)uri.openConnection();
			con.setDoInput(true);
			con.setUseCaches(true);
			con.connect();
			final InputStream inputStream = con.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
			drawable = new BitmapDrawable(bitmap);
			
			return bitmap;
		}
    }
}
