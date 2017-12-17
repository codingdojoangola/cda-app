package com.codingdojoangola.ui.members;


import com.codingdojoangola.models.member.Member;
import com.codingdojoangola.models.member.Member.BeltColor;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by henrick on 12/16/17.
 */

public class MembersFakeData {

    private static ArrayList<Member> mMembers = new ArrayList<Member>();

    public static ArrayList<Member> getFakeMembers(DatabaseReference databaseReference) {

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

        String id = databaseReference.getKey();

        for (int i = 0; i < 50; i++) {
            Member member = new Member(id, "Henrick Kakutalua", BeltColor.WHITE, disponibilities,
                    12, programmingLangs, technologies, "");
            mMembers.add(member);
        }

        return mMembers;
    }
}
