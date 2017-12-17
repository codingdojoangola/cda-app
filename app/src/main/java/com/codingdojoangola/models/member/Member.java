package com.codingdojoangola.models.member;

import com.google.firebase.database.IgnoreExtraProperties;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;

/**
 * Created by henrick on 12/16/17.
 */

@IgnoreExtraProperties
@Parcel(Parcel.Serialization.BEAN)
public class Member {

    public enum Disponibility {
        FULL_TIME,
        PART_TIME,
        FREELANCER
    };

    public enum BeltColor {
        WHITE,
        YELLOW,
        ORANGE,
        GREEN,
        BLUE,
        BROWN,
        BLACK
    };

    private String mId;
    private String mName;
    private BeltColor mBeltColor;
    private ArrayList<Disponibility> mDisponibilities;
    private int mConcludedProjects;
    private ArrayList<String> mProgrammingLangs;
    private ArrayList<String> mTechnologies;
    private String mPhotoUri;

    public Member() {}

    @ParcelConstructor
    public Member(String id, String name, BeltColor beltColor, ArrayList<Disponibility> disponibilities,
                  int concludedProjects, ArrayList<String> programmingLangs,
                  ArrayList<String> technologies, String photoUri) {
        mId = id;
        mName = name;
        mBeltColor = beltColor;
        mDisponibilities = disponibilities;
        mConcludedProjects = concludedProjects;
        mProgrammingLangs = programmingLangs;
        mTechnologies = technologies;
        mPhotoUri = photoUri;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public BeltColor getBeltColor() {
        return mBeltColor;
    }

    public void setBeltColor(BeltColor beltColor) {
        mBeltColor = beltColor;
    }

    public ArrayList<Disponibility> getDisponibilities() {
        return mDisponibilities;
    }

    public void setDisponibilities(ArrayList<Disponibility> disponibilities) {
        mDisponibilities = disponibilities;
    }

    public int getConcludedProjects() {
        return mConcludedProjects;
    }

    public void setConcludedProjects(int concludedProjects) {
        mConcludedProjects = concludedProjects;
    }

    public ArrayList<String> getProgrammingLangs() {
        return mProgrammingLangs;
    }

    public void setProgrammingLangs(ArrayList<String> programmingLangs) {
        mProgrammingLangs = programmingLangs;
    }

    public ArrayList<String> getTechnologies() {
        return mTechnologies;
    }

    public void setTechnologies(ArrayList<String> technologies) {
        mTechnologies = technologies;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }
}
