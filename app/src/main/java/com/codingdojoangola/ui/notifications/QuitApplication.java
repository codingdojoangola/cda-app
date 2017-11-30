package com.codingdojoangola.ui.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.codingdojoangola.R;

public class QuitApplication extends DialogFragment {

	private Context context;

	//***************************** Construct ********************************************
	public static QuitApplication newInstance(Context context){
		QuitApplication quitApplication = new QuitApplication();
		quitApplication.context = context;

		return quitApplication;
	}

	public QuitApplication(){

	}
	
	//****************************** OnCreate Dialof Fragment ***************************************************
	@Override
	public Dialog onCreateDialog (Bundle saveInstanceState ){

		String titleDialog = context.getResources().getString(R.string.dialog_title);
		String question = context.getResources().getString(R.string.dialog_message);

		String btnNo = context.getResources().getString(R.string.button_no);
		String btnYes = context.getResources().getString(R.string.button_yes);

		OnClickListener positiveClick = new OnClickListener() {
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				getActivity().finish();		//CASO CONTRARIO NA GRAVA writeCategory...
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);

			}
		};
		
		OnClickListener negativeClick = new OnClickListener() {
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				//Toast.makeText(getActivity().getBaseContext(), "Returning to main Activity", Toast.LENGTH_SHORT ).show();
			}
		};
		
		AlertDialog.Builder b = new AlertDialog.Builder ( getActivity() );
		b.setTitle( titleDialog );
		b.setMessage( question );
		b.setNegativeButton( btnNo, negativeClick );
		b.setPositiveButton( btnYes, positiveClick );

		return b.create();
	}
	//************************************************************************************************************
}