package com.codingdojoangola.ui.members;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.models.BeltConverter;
import com.codingdojoangola.models.DisponibilityConverter;
import com.codingdojoangola.models.Member;

import org.parceler.Parcels;

public class MemberDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        }
    }
}
