package com.codingdojoangola.server.network;

//:::::::::::::::: Android imports
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


//:::::::::::::::: Import from third parties (com, junit, net, org)


//:::::::::::::::: Java and javax


//:::::::::::::::: Same project import

public class NetworkServer {

    //:::::::::::: Constants
    private static NetworkServer instance;

    //::::::::::::: Fields
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

    //************************************* CONSTRUCT **********************************************
    private NetworkServer(Context context){
        this.context = context;
    }

    //*************************** Override Methods and Callbacks (public and Private) **************


    //************************************** PUBLIC METHODS ****************************************
    //:::::::::::::::::::: TEST IF EXIT NETWORK
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

    //************************************** PRIVATE METHOD ****************************************


    //**************************** Inner Classes or Interfaces ***************



    //**********************************************************************************************
}


