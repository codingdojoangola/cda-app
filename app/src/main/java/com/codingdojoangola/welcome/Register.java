package com.codingdojoangola.welcome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.server.ConfigureFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.joooonho.SelectableRoundedImageView;

public class Register extends AppCompatActivity {

    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;

    String name;
    String email;
    String password;

    private CheckBox accept_term;
    private Button create_account;

    private FirebaseAuth firebaseAuth;
    private Intent intent;

    //************************************* onCreate *******************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        SelectableRoundedImageView selectableRoundedImageView = (SelectableRoundedImageView)findViewById(R.id.register_logo);
        selectableRoundedImageView.bringToFront();

        nameText = (EditText)findViewById(R.id.input_name);
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);

        accept_term = (CheckBox)findViewById(R.id.accept_term);

        create_account = (Button)findViewById(R.id.create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

        View loginLink = findViewById(R.id.loginLink);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //********************************** Create Account ********************************************
    public void CreateAccount() {

        //:::::::::::::: Method Validate ::::::::::::::
        if (validate() && accept_term.isChecked()) {

            firebaseAuth = ConfigureFirebase.getFireBaseAuth();
            firebaseAuth.createUserWithEmailAndPassword(
                    email,
                    name
            ).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Register Success", Toast.LENGTH_LONG).show();
                        ((CDA)getApplication()).setUsername(email);
                        onSignupSuccess();
                    }else{
                        onSignupFailed();
                    }
                }
            });

        }else{
            onSignupFailed();
            return;
        }
        //::::::::::::::::::::::::::::::::::::::::::::
    }
    //**************************************** VALIDATE ***************************************
    public boolean validate() {
        boolean valid = true;

        name = nameText.getText().toString();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        //::::::::::::::: Test if name is Empty or less than three words ::::::::::::
        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        //::::::::::::::::::::: Test if email is Empty or valid :::::::::::::
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        //:::::::::::::::::::: Test if password is Empty or .... ::::::::::::::::::
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    //*************************************
    private void Progress(){
        create_account.setEnabled(false);

        //final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
        //        R.style.AppTheme_Dark_Dialog);
        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.MyMaterialTheme);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    //********************************* ON SIGN UP SUCCESS ************************************
    public void onSignupSuccess() {

        intent = new Intent(Register.this, welcome.class);
        intent.putExtra("_boolean", 1);
        startActivity(intent);

        //showProgress(false);
    }

    //********************************* ON SIGN UP FAILED ************************************
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_LONG).show();

        //create_account.setEnabled(true);
    }


    //**************** RETURN (NOT)
    @Override
    public void onBackPressed() {
        intent = new Intent(Register.this, Login.class);
        //setResult(RESULT_OK, intent);
        //moveTaskToBack(true);
        startActivity(intent);
        finish();
    }

    //*******************************************************************************************
}
