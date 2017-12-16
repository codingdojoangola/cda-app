package com.codingdojoangola.ui.members;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingdojoangola.R;
import com.codingdojoangola.models.Member;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MembersFragment {

    private final String TAG = MembersFragment.class.getName();
    private final Context mContext;

    private RecyclerView mMembersRecyclerView;
    private FirebaseDatabase mFirebaseDatabase;
    private MemberAdapter mMemberAdapter;

    public MembersFragment(View membersFragment, Context context) {
        mContext = context;
        mMembersRecyclerView = membersFragment.findViewById(R.id.recyler_view_members);

        mMemberAdapter = new MemberAdapter(mContext);
        mMembersRecyclerView.setAdapter(mMemberAdapter);
        mMembersRecyclerView.setLayoutManager(
                new LinearLayoutManager(membersFragment.getContext(),
                        LinearLayoutManager.VERTICAL, false));

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        getMembersFromDatabase();
    }

    private void getMembersFromDatabase() {
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
        DatabaseReference membersDir = mDatabaseReference.child("members");

        ArrayList<Member> members = new ArrayList<>();

        membersDir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                membersDir.removeEventListener(this);
                for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                    Member member = memberSnapshot.getValue(Member.class);
                    members.add(member);
                }

                mMemberAdapter.swapData(members);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // NOT IMPLEMENTED
            }
        });


    }

    private void insertFakeData() {
        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
        DatabaseReference membersDir = mDatabaseReference.child("members");

        ArrayList<Member.Disponibility> disponibilities = new ArrayList<Member.Disponibility>();
        disponibilities.add(Member.Disponibility.FULL_TIME);
        disponibilities.add(Member.Disponibility.FREELANCER);

        ArrayList<String> programmingLangs = new ArrayList<>();
        programmingLangs.add("C#");
        programmingLangs.add("C++");
        programmingLangs.add("Java");

        ArrayList<String> technologies = new ArrayList<>();
        technologies.add("ASP.NET");
        technologies.add("WPF");

        String id = membersDir.push().getKey();
        Member member = new Member(id, "Henrick Kakutalua", Member.BeltColor.WHITE, disponibilities,
                12, programmingLangs, technologies, "");

        membersDir.child(id).setValue(member);
    }
}
