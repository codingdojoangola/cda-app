package com.codingdojoangola.ui.chat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codingdojoangola.R;
import com.codingdojoangola.models.chat.ChatData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DadosChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<ChatData, ShowDataViewHolder> mFirebaseAdapter;

    public DadosChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_chat);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("detalhes_utilizador");

        recyclerView = (RecyclerView)findViewById(R.id.rv_mostar_dados);
        recyclerView.setLayoutManager(new LinearLayoutManager(DadosChatActivity.this));
        Toast.makeText(DadosChatActivity.this, "Aguarde! A carregar a lista...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatData, ShowDataViewHolder>
                (ChatData.class, R.layout.dados_chat_item, DadosChatActivity.ShowDataViewHolder.class, myRef)
        {

            public void populateViewHolder(final DadosChatActivity.ShowDataViewHolder viewHolder, ChatData model, final int position) {
                viewHolder.Image_URL(model.getImagemUrl());
                viewHolder.Image_Title(model.getImagemTitulo());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DadosChatActivity.this);
                        builder.setMessage("Deseja apagar a imagem?").setCancelable(false)
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int selectedItems = position;
                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                        recyclerView.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirmar");
                        dialog.show();
                    }
                });


            }
        };

        recyclerView.setAdapter(mFirebaseAdapter);
    }

    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView image_title;
        private final ImageView image_url;



        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.fetch_image);
            image_title = (TextView) itemView.findViewById(R.id.fetch_image_title);


        }

        private void Image_Title(String title) {
            image_title.setText(title);
        }

        private void Image_URL(String title) {
            // image_url.setImageResource(R.drawable.loading);
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .placeholder(R.drawable.loading)
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
        }



    }

}
