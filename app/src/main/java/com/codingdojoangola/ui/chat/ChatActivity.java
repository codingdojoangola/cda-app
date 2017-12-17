package com.codingdojoangola.ui.chat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codingdojoangola.R;
import com.codingdojoangola.data.sharedpreferences.UserSharedPreferences;
import com.codingdojoangola.models.chat.Message;
import com.codingdojoangola.utils.DataActual;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef, dbRefDois;
    private FirebaseRecyclerAdapter<Message, ChatConversationViewHolder> mFirebaseAdapter;
    public LinearLayoutManager mLinearLayoutManager;
    static String nomeEmissor;
    private static String userID;

    ImageView icone_anexo, image_enviar, image_nao_disponivel;
    EditText message_area;
    TextView sem_chat;

    private static final int GALERIA_INTENT = 2;
    private ProgressDialog mProgressDialog;
    ProgressBar progressBar;
    public static final int READ_EXTERNAL_STORAGE = 0,MULTIPLE_PERMISSIONS = 10;
    Uri mImageUri = Uri.EMPTY;
    FirebaseUser fbUtilizador;
    private FirebaseAuth mAuth;
    private String pictureImagePath = "";
    final CharSequence[] options = {"Camera", "Galeria"};
    String[] permissions= new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        UserSharedPreferences sharedPreferences = new UserSharedPreferences(this);

        mAuth = FirebaseAuth.getInstance();
        fbUtilizador = mAuth.getCurrentUser();

        userID = fbUtilizador.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("chat").child(userID).child(getIntent().getStringExtra("id").replace("@","").replace(".",""));
        dbRef.keepSynced(true);


        dbRefDois = FirebaseDatabase.getInstance().getReference().child("chat").child(getIntent().getStringExtra("id").replace("@","").replace(".","")).child(userID);
        dbRefDois.keepSynced(true);

        //verifyPresence();


        //Toolbar toolbar = (Toolbar) findViewById(R.id.fragment_chat_appBarLayout);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(Html.fromHtml("<font color=#FFFFFF>" + getIntent().getStringExtra("name") + "</font>"));
            //actionBar.setSubtitle(Html.fromHtml("<font color=#FFFFFF>" + getIntent().getStringExtra("name") + "</font>"));
        }
        nomeEmissor = getIntent().getStringExtra("name");
        recyclerView = (RecyclerView)findViewById(R.id.fragment_chat_recycler_view);
        icone_anexo = (ImageView)findViewById(R.id.attachButton);
        image_enviar = (ImageView)findViewById(R.id.sendButton);
        image_nao_disponivel = (ImageView)findViewById(R.id.no_data_available_image);
        message_area = (EditText)findViewById(R.id.messageArea);
        mProgressDialog = new ProgressDialog(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        sem_chat = (TextView)findViewById(R.id.no_chat_text);
        mLinearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setStackFromEnd(true);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        image_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = message_area.getText().toString().trim();

                if(!messageText.equals("")){
                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("message", messageText);
                    map.put("sender", userID);
                    map.put("date_time", new DataActual().DiaMesAnoHora());
                    dbRef.push().setValue(map);
                    dbRefDois.push().setValue(map);
                    message_area.setText("");
                    recyclerView.postDelayed(new Runnable() {
                        @Override public void run()
                        {
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);

                        }
                    }, 500);
                }
            }
        });

        icone_anexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Escolha ");
                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Camera"))
                        {
                            if (checkPermissions())
                            {
                                callCamera();
                            }
                        }
                        if(options[item].equals("Galeria"))
                        {
                            if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions(ChatActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                            }
                            else
                            {
                                callgalary();
                            }
                        }
                    }
                });
                builder.show();
            }
        });
    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callgalary();
                return;

            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    callCamera();
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, ChatConversationViewHolder>(Message.class, R.layout.chat_mensagem_item_sender, ChatConversationViewHolder.class, dbRef) {


            public void populateViewHolder(final ChatConversationViewHolder viewHolder, Message model, final int position) {

                viewHolder.getEmissor(model.getSender());
                viewHolder.getMensagem(model.getMessage(), model.getDate_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        final DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.keepSynced(true);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String retrieve_image_url = dataSnapshot.child("message").getValue(String.class);
                                if(retrieve_image_url.startsWith("https"))
                                {
                                    //Toast.makeText(ChatActivity.this, "URL : " + retrieve_image_url, Toast.LENGTH_SHORT).show();
                                    Intent intent = (new Intent(ChatActivity.this,ImagemActivity.class));
                                    intent.putExtra("url",retrieve_image_url);
                                    startActivity(intent);
                                }else{

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                                    //builder.setTitle("Escolha");
                                    //builder.setMessage(R.string.info_description);

                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChatActivity.this, android.R.layout.simple_list_item_1);
                                    arrayAdapter.add("Copiar texto");
                                    //arrayAdapter.add("Apagar mensagem");

                                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });

                                    builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(which == 0){
                                                setClipboard(ChatActivity.this, retrieve_image_url);
                                            }
                                        }
                                    });
                                    builder.show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);


        dbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    progressBar.setVisibility(ProgressBar.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    image_nao_disponivel.setVisibility(View.GONE);
                    sem_chat.setVisibility(View.GONE);
                    recyclerView.postDelayed(new Runnable() {
                        @Override public void run()
                        {
                            recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                        }
                    }, 500);
                    recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v,
                                                   int left, int top, int right, int bottom,
                                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            if (bottom < oldBottom) {
                                recyclerView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                                    }
                                }, 100);
                            }
                        }
                    });
                }
                else {
                    progressBar.setVisibility(ProgressBar.GONE);
                    recyclerView.setVisibility(View.GONE);
                    image_nao_disponivel.setVisibility(View.VISIBLE);
                    sem_chat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
             case R.id.action_settings:
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    //View Holder For Recycler View
    public static class ChatConversationViewHolder extends RecyclerView.ViewHolder {
        private final TextView message, sender, tempo;
        private final ImageView chat_image_incoming,chat_image_outgoing;
        View mView;
        final LinearLayout.LayoutParams params,text_params;
        LinearLayout layout, layout_principal;


        public ChatConversationViewHolder(final View itemView) {
            super(itemView);

            mView = itemView;
            message = (TextView) mView.findViewById(R.id.fetch_chat_messgae);
            sender = (TextView) mView.findViewById(R.id.fetch_chat_sender);
            tempo = (TextView) mView.findViewById(R.id.tv_time);
            chat_image_incoming = (ImageView) mView.findViewById(R.id.chat_uploaded_image_incoming);
            chat_image_outgoing = (ImageView) mView.findViewById(R.id.chat_uploaded_image_outgoing);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout = (LinearLayout) mView.findViewById(R.id.chat_linear_layout);
            layout_principal = (LinearLayout) mView.findViewById(R.id.llprincial);


        }

        private void getEmissor(String title) {


            if(title.equals(userID))
            {

                //params.setMargins((LoginActivity.deviceWidth/3),5,10,10);
                //text_params.setMargins(15,10,0,5);
                //sender.setLayoutParams(text_params);
                sender.setGravity(Gravity.END);
                tempo.setGravity(Gravity.END);
                //mView.setLayoutParams(params);
                layout.setBackgroundResource(R.drawable.sent_message);
                layout_principal.setGravity(Gravity.END);
                sender.setText("Eu");
                chat_image_outgoing.setVisibility(View.VISIBLE);
                chat_image_incoming.setVisibility(View.GONE);

            }
            else
            {
                //params.setMargins(10,0,(LoginActivity.deviceWidth/3),10);
                sender.setGravity(Gravity.START);
                tempo.setGravity(Gravity.START);
                //text_params.setMargins(60,10,0,5);
                //sender.setLayoutParams(text_params);
                //mView.setLayoutParams(params);
                layout.setBackgroundResource(R.drawable.received_message);
                layout_principal.setGravity(Gravity.START);
                sender.setText(nomeEmissor);
                chat_image_outgoing.setVisibility(View.GONE);
                chat_image_incoming.setVisibility(View.VISIBLE);
            }
        }

        private void getMensagem(String title, String data) {

            if(!title.startsWith("https"))
            {

                /*
                if(!sender.getText().equals(nomeEmissor))
                {
                    text_params.setMargins(15,10,22,15);
                }
                else
                {
                    text_params.setMargins(65,10,22,15);
                }
                */

                //message.setLayoutParams(text_params);
                message.setText(title);
                tempo.setText(data);
                message.setTextColor(Color.parseColor("#FFFFFF"));
                message.setVisibility(View.VISIBLE);
                chat_image_incoming.setVisibility(View.GONE);
                chat_image_outgoing.setVisibility(View.GONE);
            }
            else
            {
                if (chat_image_outgoing.getVisibility()==View.VISIBLE && chat_image_incoming.getVisibility()==View.GONE)
                {
                    chat_image_outgoing.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    Glide.with(itemView.getContext())
                            .load(title)
                            .crossFade()
                            .fitCenter()
                            .placeholder(R.drawable.loading)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(chat_image_outgoing);
                }
                else
                {
                    chat_image_incoming.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    Glide.with(itemView.getContext())
                            .load(title)
                            .crossFade()
                            .fitCenter()
                            .placeholder(R.drawable.loading)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(chat_image_incoming);
                }
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == GALERIA_INTENT && resultCode == RESULT_OK) {

            mImageUri = data.getData();
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("imagens_chat").child(mImageUri.getLastPathSegment());

            mProgressDialog.setMessage("A carregar...");
            mProgressDialog.show();

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests")
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    ArrayMap<String, String> map = new ArrayMap<>();
                    map.put("message", downloadUri.toString());
                    map.put("sender", userID);
                    map.put("date_time", new DataActual().DiaMesAnoHora());
                    dbRef.push().setValue(map);
                    dbRefDois.push().setValue(map);
                    mProgressDialog.dismiss();
                }
            });
        }

        else if (requestCode == 5 && resultCode == RESULT_OK ) {

            File imgFile = new  File(pictureImagePath);
            if(imgFile.exists()) {

                Uri fileUri =Uri.fromFile(imgFile);

                StorageReference filePath = FirebaseStorage.getInstance().getReference().child("imagens_chat").child(fileUri.getLastPathSegment());

                mProgressDialog.setMessage("A carregar...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        ArrayMap<String, String> map = new ArrayMap<>();
                        map.put("message", downloadUri.toString());
                        map.put("sender", userID);
                        map.put("date_time", new DataActual().DiaMesAnoHora());
                        dbRef.push().setValue(map);
                        dbRefDois.push().setValue(map);

                        mProgressDialog.dismiss();
                    }
                });
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Glide.clear(imageView);
        Glide.get(getApplicationContext()).clearMemory();
    }

    private void callCamera() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;

        File file = new File(pictureImagePath);

        Uri outputFileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);

        Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        CameraIntent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        CameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        
        startActivityForResult(CameraIntent, 5);
    }


    private void callgalary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALERIA_INTENT);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    public void verifyPresence(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference().child("user").child(userID).child("status");
        final DatabaseReference lastOnlineRef = database.getReference().child("user").child(userID).child("lastOnline");
        final DatabaseReference connectedRef = database.getReference(".info/connected");

        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    DatabaseReference con = myConnectionsRef.push();

                    // when this device disconnects, remove it
                    con.onDisconnect().removeValue();

                    // when I disconnect, update the last time I was seen online
                    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);

                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    //con.setValue(Boolean.TRUE);
                    con.setValue(Boolean.TRUE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });
    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(context, "Texto copiado", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Texto copiado", Toast.LENGTH_SHORT).show();
        }
    }
}