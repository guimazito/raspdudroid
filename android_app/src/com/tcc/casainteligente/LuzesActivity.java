package com.tcc.casainteligente;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class LuzesActivity extends Activity {

	private Switch swLamp1;
	private Switch swLamp2;
	private String responseBody;
	private HttpResponse response;
	private Context ctx;
	private String resp;
	private String status;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_luzes);
			
		swLamp1 = (Switch) findViewById(R.id.swLamp1);
		swLamp2 = (Switch) findViewById(R.id.swLamp2);
		
		ctx = this;
		
		//getActionBar().hide();
		
		statusAnterior();
								
		swLamp1.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					sendCommand("1");
				}else{
					sendCommand("2");
				}				
			}
		});		
		
		swLamp2.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					sendCommand("4");
				}else{
					sendCommand("5");
				}				
			}
		});		
	}
		
	public void httpArduino(String url){
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpMethod = new HttpGet();
			httpMethod.setURI(new URI(url));
			this.response = httpclient.execute(httpMethod);
			this.responseBody = EntityUtils.toString(response.getEntity());			
		}catch (Exception e){
			Toast.makeText(getApplicationContext(), "SEM CONEXÃO COM INTRANET!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void statusAnterior() {
		//httpArduino("http://192.168.0.100:8888/s");		
		//String status[] = responseBody.split(Pattern.quote(","));
		//sendCommand("s");
		try {
			this.status = Sender.getStringResponseFromGetRequest("http://192.168.0.100:8888/s");
		} catch (IOException e) {
			status = e.getMessage();
		};
		String a = status.substring(0, 2);
		//String status = resp;
		if(a.charAt(0) == '0'){
			swLamp1.setChecked(false);
		}
		if(a.charAt(0) == '1'){
			swLamp1.setChecked(true);
		}
		if(a.charAt(1) == '0'){
			swLamp2.setChecked(false);
		}
		if(a.charAt(1) == '1'){
			swLamp2.setChecked(true);
		}		
	}
	
//	public void statusAnterior() {
//		//httpArduino("http://192.168.0.102:8090");		
//		//String status[] = responseBody.split(Pattern.quote(","));
//		sendCommand("s");
//		String status = responseBody;
//		if(status.charAt(0) == 0){
//			swLamp1.setChecked(false);
//		}
//		if(status[0].equals("1")){
//			swLamp1.setChecked(true);
//		}
//		if(status[1].equals("0")){
//			swLamp2.setChecked(false);
//		}
//		if(status[1].equals("1")){
//			swLamp2.setChecked(true);
//		}
//	}
	
	
	
	private void sendCommand(String command){
		final String cmd = command;
		
		AsyncTask task = new AsyncTask() {
			
			//private String resp;

			@Override
			protected Object doInBackground(Object... params) {
				try {
					resp = Sender.getStringResponseFromGetRequest("http://192.168.0.100:8888/" + cmd);
				} catch (IOException e) {
					resp = e.getMessage();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Object result) {
				if(!resp.equals("OK1")){
					Toast.makeText(ctx, resp, Toast.LENGTH_LONG).show();
				}
				super.onPostExecute(result);
			}
		};
		
		task.execute();
	}
	
}