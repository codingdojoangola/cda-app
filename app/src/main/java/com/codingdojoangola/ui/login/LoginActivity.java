package com.codingdojoangola.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codingdojoangola.data.sharedpreferences.UserSharedPreferences;
import com.codingdojoangola.ui.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.codingdojoangola.R;

/**
 * The activity responsible for login in the user
 */
public class LoginActivity extends AppCompatActivity
        implements OnCompleteListener<AuthResult>, OnFailureListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private UserSharedPreferences mUserSharedPreferences;
    private FirebaseAuth mFirebaseAuth;

    private AutoCompleteTextView mEmailAutoComplete;
    private EditText mPasswordEditText;
    private ProgressDialog mLoginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mEmailAutoComplete = findViewById(R.id.autoComplete_login_name);
        mPasswordEditText = findViewById(R.id.editText_login_password);
        Button loginButton = findViewById(R.id.button_login_sign_in);

        loginButton.setOnClickListener((View v) -> loginUser());

        mLoginProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Called when the authentication process is completed (succeeded or failed)
     * @param task a Task object containing the result of the authentication process
     */
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) {
            Toast.makeText(this, "Succesfull loginUser", Toast.LENGTH_SHORT).show();
            saveUserData(mFirebaseAuth.getCurrentUser());

            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            showLoginFailedMessage();
        }

        mLoginProgressDialog.dismiss();
    }

    /**
     * Called when the authentication process goes wrong
     * @param e the exception object containing details about the failure
     */
    @Override
    public void onFailure(@NonNull Exception e) {
        Log.e(TAG, e.getMessage());
        mLoginProgressDialog.dismiss();
    }

    /**
     * Tries to login the user using Firebase
     */
    private void loginUser() {

        if (!validLoginFields())
            return;

        String email = mEmailAutoComplete.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this)
                .addOnFailureListener(this);

        mLoginProgressDialog.setIndeterminate(true);
        mLoginProgressDialog.setCancelable(false);
        mLoginProgressDialog.setMessage(getString(R.string.login_wait_text));
        mLoginProgressDialog.show();
    }

    /**
     * Save the data of the current logged in user in shared preferences
     * @param firebaseUser an object containing the details about the user
     */
    private void saveUserData(FirebaseUser firebaseUser) {

        if (firebaseUser != null) {

            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            String userId = firebaseUser.getUid();
            String uriString;
            String token = firebaseUser.getIdToken(true).toString();

            if (firebaseUser.getPhotoUrl() == null) {
                uriString = "";
            } else {
                uriString = firebaseUser.getPhotoUrl().toString();
            }

            UserSharedPreferences userSharedPreferences =
                    new UserSharedPreferences(this);
            userSharedPreferences.setUsername(name);
            userSharedPreferences.setUserId(userId);
            userSharedPreferences.setEmail(email);
            userSharedPreferences.setPhotoUrl(uriString);
            userSharedPreferences.setDeviceToken(token);
        }
    }

    /**
     * Check if the email and password are valid before login in
     * @return a boolean indicating if the login/password are in valid state
     */
    private boolean validLoginFields() {

        String email = mEmailAutoComplete.getText().toString();
        String password = mPasswordEditText.getText().toString();
        Boolean validFields = true;

        if (TextUtils.isEmpty(email) ||
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validFields = false;
            mEmailAutoComplete.setError(getString(R.string.invalid_email_error));
        }

        if (TextUtils.isEmpty(password)) {
            validFields = false;
            mPasswordEditText.setError(getString(R.string.empty_password_error));
        }

        return validFields;
    }

    /**
     * Show a text indicating that the login was unsuccessful
     */
    private void showLoginFailedMessage() {
        //mNetworkFailureTextView.setVisibility(View.INVISIBLE);
        //mLoginFailureTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Show a text indicating that a failure related to network has occurred
     */
    private void showNetworkFailureMessage() {
        //mLoginFailureTextView.setVisibility(View.INVISIBLE);
        //mNetworkFailureTextView.setVisibility(View.VISIBLE);
    }
}