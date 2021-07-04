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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PrincipalActivity extends Activity implements OnClickListener{

	private Button btnTemperatura;
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
		setContentView(R.layout.activity_principal);
				
		ctx = this;
		
		btnTemperatura = (Button) findViewById(R.id.btnTemperatura);
		btnTemperatura.setOnClickListener(this);
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
		
	public void onClick(View thisView) {
		switch(thisView.getId()){					
		case R.id.btnTemperatura:			
			//sendCommand("3");
			
			try {
				this.status = Sender.getStringResponseFromGetRequest("http://192.168.0.100:8888/3");
			} catch (IOException e) {
				status = e.getMessage();
			};
			String a = status.substring(2, 4);
			//String status[] = responseBody.split(Pattern.quote(","));
			Toast.makeText(getApplicationContext(), a + " °C", Toast.LENGTH_SHORT).show();			
			break;	
		}
	}
		
	public void luzesOnClick(View v) {
		Intent intent = new Intent(this, LuzesActivity.class);
		startActivity(intent);
	}
	
	public void AlarmeOnClick(View v) {
		Intent intent = new Intent(this, AlarmeActivity.class);
		startActivity(intent);
	}
	
	public void irrigarOnClick(View v) {
		Intent intent = new Intent(this, IrrigarActivity.class);
		startActivity(intent);
	}
	
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
				if(!resp.equals("OK")){
					Toast.makeText(ctx, resp, Toast.LENGTH_LONG).show();
				}
				super.onPostExecute(result);
			}
		};
		
		task.execute();
	}

}
