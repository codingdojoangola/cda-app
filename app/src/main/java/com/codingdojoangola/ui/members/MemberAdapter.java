package com.codingdojoangola.ui.members;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.models.member.Member;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by henrick on 12/16/17.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private final Context mContext;
    private final MemberListItemClickListener mClickListener;
    private ArrayList<Member> mMembers;

    public MemberAdapter(Context context, MemberListItemClickListener clickListener) {
        mMembers = new ArrayList<>();
        mContext = context;
        mClickListener = clickListener;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflatedView = inflater.inflate(R.layout.members_list_item, parent, false);
        return new MemberViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {

        Member currentMember = mMembers.get(position);

        String name = currentMember.getName();
        int concludedProjects = currentMember.getConcludedProjects();
        ArrayList<String> programmingLangs = currentMember.getProgrammingLangs();

        holder.mNameTextView.setText(name);
        holder.mConcludedProjectsNumber.setText(String.valueOf(concludedProjects));

        String langs = TextUtils.join(", ", programmingLangs.toArray());
        holder.mProgrammingLanguagesTextView.setText(langs);

        String photoUri = currentMember.getPhotoUri();
        if (photoUri != null && !TextUtils.isEmpty(photoUri)) {
            Picasso.with(mContext).load(photoUri).into(holder.mProfileImageView);
        } else {
            holder.mProfileImageView.setImageResource(R.drawable.ic_default_profile_image_100dp);
        }
    }

    @Override
    public int getItemCount() {
        return (mMembers == null) ? 0 : mMembers.size();
    }

    public void swapData(ArrayList<Member> members) {
        mMembers = members;
        notifyDataSetChanged();
    }

    public ArrayList<Member> getData() {
        return mMembers;
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mProfileImageView;
        private TextView mNameTextView;
        private TextView mProgrammingLanguagesTextView;
        private TextView mConcludedProjectsNumber;

        public MemberViewHolder(View itemView) {
            super(itemView);

            mProfileImageView = itemView.findViewById(R.id.image_profile);
            mNameTextView = itemView.findViewById(R.id.text_name);
            mProgrammingLanguagesTextView = itemView.findViewById(R.id.text_programming_languages);
            mConcludedProjectsNumber = itemView.findViewById(R.id.text_concluded_projects_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            long clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                mClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface MemberListItemClickListener {
        void onListItemClick(long position);
    }
}
