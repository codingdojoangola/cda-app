package com.codingdojoangola.ui.launch;

//:::::::::::::::: Android imports
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//:::::::::::::::: Import from third parties (com, junit, net, org)
import com.alorma.github.sdk.bean.dto.response.Token;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.services.login.RequestTokenClient;
import com.alorma.github.sdk.services.user.GetAuthUserClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.data.sharedpreferences.UserSharedPreferences;
import com.codingdojoangola.ui.main.MainActivity;
import com.codingdojoangola.R;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rx.Observer;

/**
 * The activity responsible for login in the user
 */
public class LoginActivity extends AppCompatActivity
        implements OnCompleteListener<AuthResult>, OnFailureListener {

    //:::::::::::: Constants
    private static final String TAG = MainActivity.class.getSimpleName();

    //::::::::::::: Fields
    //private UserSharedPreferences mUserSharedPreferences;
    private FirebaseAuth mFirebaseAuth;

    private AutoCompleteTextView mEmailAutoComplete;
    private EditText mPasswordEditText;
    private ProgressDialog mLoginProgressDialog;
    public static int deviceWidth;


    private DatabaseReference mDatabaseReference;

    //*********************************** ON CREATE *********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        mEmailAutoComplete = findViewById(R.id.auto_complete_login_name);
        mPasswordEditText = findViewById(R.id.edit_text_login_password);
        Button loginButton = findViewById(R.id.button_login_sign_in);

        loginButton.setOnClickListener((View v) -> loginUser());

        mLoginProgressDialog = new ProgressDialog(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
    }

    //************************************** PUBLIC METHODS ****************************************
    /**
     * Called when the authentication process is completed (succeeded or failed)
     * @param task a Task object containing the result of the authentication process
     */
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) {
            Toast.makeText(this, "Succesfull loginUser", Toast.LENGTH_SHORT).show();
            saveUserData(mFirebaseAuth.getCurrentUser());
            callMainActivity();
        } else {
            showSnackbar("Login with email & password failed");
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
     * Starter of GitHub authentication
     * @param view
     */
    public void sendLoginGithubData( View view ){
        //Create github url to with to make the OAuth authentication
        FirebaseCrash.log("LoginActivity:clickListener:button:sendLoginGithubData()");
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", getString(R.string.github_app_id))
                .appendQueryParameter("&scope", "user,user:email")
                .build();


        //This is the dialog used for displaying the web view below
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_github_webview);
        dialog.setTitle("Login with Github");
        dialog.setCancelable(true);
        dialog.show();

        //web view which displays all the github auth process
        WebView webView = dialog.findViewById(R.id.github_webview);
        webView.loadUrl( uri.toString() );
        webView.requestFocus(View.FOCUS_DOWN);
        webView.getSettings().setNeedInitialFocus(true);
        webView.requestFocus();
        webView.setFocusable(true);
        webView.findFocus();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Uri uri = Uri.parse(url);

                if( uri.getQueryParameter("code") != null
                        && uri.getScheme() != null
                        && uri.getScheme().equalsIgnoreCase("https") ){

                    requestGitHubUserAccessToken( uri.getQueryParameter("code") );
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    //************************************** PRIVATE METHODS ***************************************

    private void accessGithubLoginData(String accessToken, User githubUser){
        String provider = "github";
            AuthCredential credential = GithubAuthProvider.getCredential(accessToken);
            credential = provider.equalsIgnoreCase("github") ? GithubAuthProvider.getCredential( accessToken ) : credential;
            //user.saveProviderSP( LoginActivity.this, provider );
            mFirebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        saveUserGithubData("name", githubUser.name);
                        saveUserGithubData("email", githubUser.email);
                        saveUserGithubData("company", githubUser.company);
                        saveUserGithubData("createdAt", String.valueOf(githubUser.created_at));
                        saveUserGithubData("bio", githubUser.bio);
                        saveUserGithubData("location", githubUser.location);
                        saveUserGithubData("numOfRepos", String.valueOf(githubUser.public_repos));
                        saveUserGithubData("followers", String.valueOf(githubUser.followers));
                        callMainActivity();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    /**
     * Method used to make the connection of the github account and the firebase OAuth
     **/
    private void requestGitHubUserAccessToken( String code ){
        RequestTokenClient requestTokenClient = new RequestTokenClient(
                code,
                getString(R.string.github_app_id),
                getString(R.string.github_app_secret),
                getString(R.string.github_app_url)
        );

        requestTokenClient
                .observable()
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        //showSnackbar( e.getMessage() );
                    }

                    @Override
                    public void onNext(Token token) {
                        if( token.access_token != null ){
                            requestGitHubUserData( token.access_token );
                        }
                    }
                });
    }

    /**
     * Getting accessible data from the user's github account with the use of 'com.github.alorma:github-sdk:3.2.5'
     **/
    private void requestGitHubUserData( final String accessToken ){
        GetAuthUserClient getAuthUserClient = new GetAuthUserClient( accessToken );
        getAuthUserClient
                .observable()
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(com.alorma.github.sdk.bean.dto.response.User user) {
                        accessGithubLoginData( accessToken, user );
                    }
                });

    }

    /**
     * This method saves the github user's account to firebase realtime database
     * @param child means the name of the node on the users database
     * @param value is the data that child carries
     */
    private void saveUserGithubData(String child, String value) {
        if (mFirebaseAuth.getCurrentUser() != null)
            mDatabaseReference.child("users").child(mFirebaseAuth.getCurrentUser().getUid()).child(child).setValue(value);
    }

    /**
     * This method starts the main activity and finishes
     * and finishes this current activity
     */
    private void callMainActivity(){
        Intent intent = new Intent( this, MainActivity.class );
        startActivity(intent);
        finish();
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

    private void showSnackbar (String message) {
        Snackbar.make(findViewById(R.id.activity_login_layout), message, Snackbar.LENGTH_SHORT ).show();
    }

    public void startRegistration(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    //**********************************************************************************************
}