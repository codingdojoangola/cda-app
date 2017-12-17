package com.codingdojoangola.ui.notifications;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.content.DialogInterface;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import


import com.codingdojoangola.R;

public class RegisterDialog extends DialogFragment{

    //:::::::::::: Constants
    final boolean select = false;

    //::::::::::::: Fields


    //************************************* ON CREATE **********************************************
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Set the dialog title
        builder.setTitle("Código");

        //Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        builder.setSingleChoiceItems(R.array.code, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:

                        break;
                    case 1:

                        break;

                    default:
                        Toast.makeText(getActivity(),
                                "Seleciona uma das opções !!!!",
                                Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set the action buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK, so save the mSelectedItems results somewhere
                // or return them to the component that opened the dialog

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    //************************************ PUBLIC METHODS ******************************************



    //************************************ PRIVATE METHODS *****************************************




    //**********************************************************************************************
}
