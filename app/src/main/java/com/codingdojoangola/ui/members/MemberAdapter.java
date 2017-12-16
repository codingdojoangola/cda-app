package com.codingdojoangola.ui.members;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.models.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henrick on 12/16/17.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private final Context mContext;
    private ArrayList<Member> mMembers;

    public MemberAdapter(Context context) {
        mMembers = new ArrayList<>();
        mContext = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflatedView = inflater.inflate(R.layout.members_list_item, parent, false);
        return new MemberViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        String name = mMembers.get(position).getName();
        ArrayList<String> programmingLangs = mMembers.get(position).getProgrammingLangs();
        String beltColor = convertBeltToReadableString(
                mMembers.get(position).getBeltColor(), mContext);

        holder.mNameTextView.setText(name);
        holder.mBeltColorTextView.setText(beltColor);

        String langsString = "";
        for (String lang : programmingLangs) {
            langsString = langsString.concat(lang + " ");
        }

        holder.mProgrammingLanguagesTextView.setText(langsString);
    }

    @Override
    public int getItemCount() {
        return (mMembers == null) ? 0 : mMembers.size();
    }

    public void swapData(ArrayList<Member> members) {
        mMembers = members;
        notifyDataSetChanged();
    }

    private String convertBeltToReadableString(Member.BeltColor beltColor, Context context) {
        switch (beltColor) {
            case WHITE:
                return context.getString(R.string.white_belt_text);
            case YELLOW:
                return context.getString(R.string.yellow_belt_text);
            case ORANGE:
                return context.getString(R.string.orange_belt_text);
            case GREEN:
                return context.getString(R.string.green_belt_text);
            case BLUE:
                return context.getString(R.string.blue_belt_text);
            case BROWN:
                return context.getString(R.string.brown_belt_text);
            case BLACK:
                return context.getString(R.string.black_belt_text);
            default:
                throw new IllegalArgumentException("Unknown belt color: " + beltColor.toString());
        }
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mBeltColorTextView;
        private TextView mProgrammingLanguagesTextView;

        public MemberViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.text_name);
            mBeltColorTextView = itemView.findViewById(R.id.text_belt_color);
            mProgrammingLanguagesTextView = itemView.findViewById(R.id.text_programming_languages);
        }
    }
}
