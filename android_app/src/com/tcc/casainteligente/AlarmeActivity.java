package com.tcc.casainteligente;

import java.net.URI;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AlarmeActivity extends Activity {
	
	private Switch swPortaEntrada;
	private String responseBody;
	private HttpResponse response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarme);
		
		swPortaEntrada = (Switch) findViewById(R.id.swPortaEntrada);
		
		//statusAnterior();
		
		swPortaEntrada.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					httpArduino("http://192.168.0.102:8090/?AON");
				}else{
					httpArduino("http://192.168.0.102:8090/?AOFF");
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
		httpArduino("http://192.168.0.102:8090");		
		String status[] = responseBody.split(Pattern.quote(","));
		if(status[3].equals("0")){
			swPortaEntrada.setChecked(false);
		}
		if(status[3].equals("1")){
			swPortaEntrada.setChecked(true);
		}
	}
}