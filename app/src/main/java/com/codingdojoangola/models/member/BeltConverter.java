package com.codingdojoangola.models.member;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.codingdojoangola.R;

/**
 * Created by henrick on 12/17/17.
 */

public final class BeltConverter {

    public static String toReadableString(Member.BeltColor beltColor, Context context) {
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

    public static int toColorIdentifier(Member.BeltColor beltColor, Context context) {
        switch (beltColor) {
            case WHITE:
                return ContextCompat.getColor(context, R.color.white);
            case YELLOW:
                return ContextCompat.getColor(context, R.color.yellow);
            case ORANGE:
                return ContextCompat.getColor(context, R.color.orange);
            case GREEN:
                return ContextCompat.getColor(context, R.color.green);
            case BLUE:
                return ContextCompat.getColor(context, R.color.blue);
            case BROWN:
                return ContextCompat.getColor(context, R.color.brown);
            case BLACK:
                return ContextCompat.getColor(context, R.color.black);
            default:
                throw new IllegalArgumentException("Unknown belt color: " + beltColor.toString());
        }
    }
}
