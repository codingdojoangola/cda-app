package com.codingdojoangola.ui.main;

//:::::::::::::::: Android imports
import android.content.Context;
import android.os.AsyncTask;
import android.content.DialogInterface;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;


//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.server.network.NetworkServer;

public class TransactionFragment extends AsyncTask<Void, Void, Void> {

    private Context context;
    private ProgressBar progressBar;

    //Auxiliary
    private FragmentTransaction Transaction;
    private String choiceType;

    private boolean update;
    private final AlertDialog.Builder builder;

    //*************************************** CONSTRUCTOR ******************************************
    public TransactionFragment (final Context context,
                                final FragmentTransaction Transaction,
                                final String choiceType,
                                boolean update){
        this.context = context;
        this.Transaction = Transaction;
        this.choiceType = choiceType;
        this.update = update;


        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do something
                    }
                });

        progressBar = (ProgressBar) ((Activity) context).findViewById(R.id.progressBar);
        //progressBar.getIndeterminateDrawable().setColorFilter(colorLoading, PorterDuff.Mode.MULTIPLY);

    }

    //***************************************************************************************
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        progressBar.setIndeterminate(true);
    }

    @Override
    protected Void doInBackground(Void... params) {

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        if(NetworkServer.getInstance().isNetworkAvailable()){

        }else{
            // sleep for 1 seconds
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (!NetworkServer.getInstance().isNetworkAvailable() && update){
            builder.setTitle("Internet");
            builder.setMessage(context.getResources().getString(R.string.network_download));
            Dialog dialog = builder.create();
            dialog.show();

        }else {

            Transaction.replace(R.id.content_frame, MainFrameFragment.newInstance(choiceType));
            Transaction.commit();
            //Transaction.commitAllowingStateLoss();
        }
        progressBar.setVisibility(View.GONE);
    }

    //***********************************************************************************************
}