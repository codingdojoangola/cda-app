package com.codingdojoangola.ui.members;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.models.member.BeltConverter;
import com.codingdojoangola.models.member.DisponibilityConverter;
import com.codingdojoangola.models.member.Member;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MemberDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView profileImageView = findViewById(R.id.image_profile);
        TextView nameTextView = findViewById(R.id.text_name);
        TextView beltTextView = findViewById(R.id.text_belt);
        View beltColorView = findViewById(R.id.view_belt_color);
        TextView availabilityTextView = findViewById(R.id.text_availability);
        TextView programmingLangsTextView = findViewById(R.id.text_programming_languages);
        TextView technologiesTextView = findViewById(R.id.text_technologies);

        Intent intent = getIntent();
        if (intent.hasExtra(MembersFragment.EXTRA_MEMBER_DETAILS)) {
            Member member = Parcels.unwrap(intent.getParcelableExtra(MembersFragment.EXTRA_MEMBER_DETAILS));

            int colorIdentifier = BeltConverter.toColorIdentifier(member.getBeltColor(), this);
            String beltColorDescription = BeltConverter.toReadableString(member.getBeltColor(), this);
            String availabilities = TextUtils.join(
                    ", ",
                    DisponibilityConverter.toReadableStringArray(member.getDisponibilities(), this));

            String programmingLangs = TextUtils.join(", ", member.getProgrammingLangs());
            String technologies = TextUtils.join(", ", member.getTechnologies());

            nameTextView.setText(member.getName());
            beltColorView.setBackgroundColor(colorIdentifier);
            beltTextView.setText(beltColorDescription);
            availabilityTextView.setText(availabilities);
            programmingLangsTextView.setText(programmingLangs);
            technologiesTextView.setText(technologies);

            String photoUri = member.getPhotoUri();
            if (photoUri != null && !TextUtils.isEmpty(photoUri)) {
                Picasso.with(this).load(photoUri).into(profileImageView);
            } else {
                profileImageView.setImageResource(R.drawable.ic_default_profile_image_100dp);
            }
        }
    }
}
