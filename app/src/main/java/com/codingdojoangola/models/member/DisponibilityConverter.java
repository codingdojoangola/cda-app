package com.codingdojoangola.models.member;

import android.content.Context;

import com.codingdojoangola.R;

import java.util.ArrayList;

/**
 * Created by henrick on 12/17/17.
 */

public class DisponibilityConverter {

    public static String[] toReadableStringArray(ArrayList<Member.Disponibility> disponibilities, Context context) {
        ArrayList<String> disponibilitiesStringArray = new ArrayList<String>();

        for (Member.Disponibility disponibility : disponibilities) {
            switch (disponibility) {
                case FULL_TIME:
                    disponibilitiesStringArray.add(context.getString(R.string.full_time_text));
                    break;
                case PART_TIME:
                    disponibilitiesStringArray.add(context.getString(R.string.part_time_text));
                    break;
                case FREELANCER:
                    disponibilitiesStringArray.add(context.getString(R.string.freelancer_text));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown disponibility: " + disponibility.toString());
            }
        }

        return disponibilitiesStringArray.toArray(new String[disponibilitiesStringArray.size()]);
    }
}
