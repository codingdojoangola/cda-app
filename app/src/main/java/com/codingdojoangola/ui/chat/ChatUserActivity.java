package com.codingdojoangola.ui.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codingdojoangola.R;
import com.codingdojoangola.data.sharedpreferences.UserSharedPreferences;
import com.codingdojoangola.models.chat.User;
import com.codingdojoangola.utils.CircleTransform;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatUserActivity extends AppCompatActivity {

    TextView person_name,person_email;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<User, ShowChatViewHolder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference("members");
        //myRef.keepSynced(true);

        progressBar = (ProgressBar) findViewById(R.id.chat_user_progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.show_chat_recyclerView);

        mLinearLayoutManager = new LinearLayoutManager(ChatUserActivity.this);
        //mLinearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(ProgressBar.VISIBLE);


        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, ChatUserActivity.ShowChatViewHolder>(User.class, R.layout.show_chat_single_item, ChatUserActivity.ShowChatViewHolder.class, myRef) {

            public void populateViewHolder(final ChatUserActivity.ShowChatViewHolder viewHolder, User model, final int position) {
                //Log.d("LOGGED", "populateViewHolder Called: ");
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                if (!model.getName().equals("Null")) {
                    viewHolder.NomePessoa(model.getName());
                    //viewHolder.ImagemPessoa(model.getImage());
                    viewHolder.EmailPessoa(model.getEmail());
                    if(model.getId().equals(new UserSharedPreferences(ChatUserActivity.this).getUserId()))
                    {
                        //viewHolder.itemView.setVisibility(View.GONE);
                        viewHolder.Layout_hide();

                        //recyclerView.getChildAdapterPosition(viewHolder.itemView.getRootView());
                        // viewHolder.itemView.set;

                    }
                    else
                        viewHolder.EmailPessoa(model.getEmail());
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String retrieve_name = dataSnapshot.child("name").getValue(String.class);
                                String retrieve_Email = dataSnapshot.child("email").getValue(String.class);
                                String retrieve_id = dataSnapshot.child("id").getValue(String.class);
                                //String retrieve_url = dataSnapshot.child("image").getValue(String.class);



                                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                //intent.putExtra("image_id", retrieve_url);
                                intent.putExtra("id", retrieve_id);
                                intent.putExtra("name", retrieve_name);

                                startActivity(intent);
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





    }

    public static class ShowChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView person_name, person_email;
        private final ImageView person_image;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public ShowChatViewHolder(final View itemView) {
            super(itemView);
            person_name = (TextView) itemView.findViewById(R.id.chat_persion_name);
            person_email = (TextView) itemView.findViewById(R.id.chat_persion_email);
            person_image = (ImageView) itemView.findViewById(R.id.chat_persion_image);
            layout = (LinearLayout)itemView.findViewById(R.id.show_chat_single_item_layout);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        private void NomePessoa(String title) {
            person_name.setText(title);
        }
        private void Layout_hide() {
            params.height = 0;
            layout.setLayoutParams(params);
        }


        private void EmailPessoa(String title) {
            person_email.setText(title);
        }


        private void ImagemPessoa(String url) {

            if (!url.equals("Null")) {
                Glide.with(itemView.getContext())
                        .load(url)
                        .crossFade()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.loading)
                        .bitmapTransform(new CircleTransform(itemView.getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(person_image);
            }

        }


    }
}
