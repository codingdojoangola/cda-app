package com.codingdojoangola.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkServer {

	private static NetworkServer instance;
	private Context context;

	//*************************** CREATE THE INSTANCE ********************************
	public static void initInstance(Context context){

		if(instance == null){
			instance = new NetworkServer(context);
		}
	}

	//**************************** RETURN THE INSTANCE *******************************
	public static NetworkServer getInstance(){
		return instance;
	}


	//****************************************** CONSTRUCT *****************************************
	private NetworkServer(Context context){
		//Log.d("************** CREATE ","CONNECTION SERVER QEVENT ************************");
		this.context = context;
	}

	//********************************** Custom method *********************************************
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager;
		//NetworkInfo wifiInfo, mobileInfo;
		boolean connected = false;

		try {
			connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable() &&
					networkInfo.isConnected();

		} catch (Exception e) {
			System.out.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}
		return connected;
	}

	//**********************************************************************************************
}