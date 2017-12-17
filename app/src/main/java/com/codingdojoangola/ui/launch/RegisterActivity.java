package com.codingdojoangola.ui.launch;

//:::::::::::::::: Android imports
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity{

    //:::::::::::: Constants



    //::::::::::::: Fields
    private EditText userName, inputPassword, inputEmail;
    private Button btnSignUp, btnLogin, btnGithubLogin;
    private CheckBox Terms;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;



    //*********************************** ON CREATE *********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp =  findViewById(R.id.button_register_create_account);
        userName =  findViewById(R.id.editText_register_input_name);
        inputEmail =  findViewById(R.id.editText_register_input_email);
        inputPassword =  findViewById(R.id.editText_register_input_password);
        progressBar =  findViewById(R.id.progressBar);
        Terms = findViewById(R.id.checkbox_register_accept_term);
//        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String name = userName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "Escreve o nome de usuário!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Escreva o seu email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Escreva a sua password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                progressBar.setVisibility(View.VISIBLE);
                //create user
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }




    //*********************** Override Methods and Callbacks (public and Private) ******************


    //************************************* PUBLIC METHODS ****************************************



    //************************************* PRIVATE METHODS ***************************************




    //**********************************************************************************************
}
