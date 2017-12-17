package com.codingdojoangola.data.sharedpreferences

import android.content.Context
import android.preference.PreferenceManager

/**
* Created by Pedro Massango on 25/11/2017.
*/
class UserSharedPreferences(context: Context) {

    companion object {
        val DEVELOP_MODE = false
    }

    private val PREF_IS_FIRST_TIME_KEY = "IS_FIRST_TIME"
    private val PREF_LOGGED_USER_NAME_KEY = "PREF_LOGGED_USER_NAME"
    private val PREF_USER_EMAIL_KEY = "PREF_USER_EMAIL"
    private val PREF_LOGGED_USER_ID_KEY = "LOGGED_USER_ID"
    private val PREF_IS_LOGGED_IN_KEY = "IS_LOGGED_IN"
    private val PREF_PHOTO_URL_KEY = "PREF_PHOTO_URL"
    private val PREF_TOKEN_KEY = "PREF_TOKEN"

    private val mPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // save First time that the app is opened state
    var isFirstTime = mPreferences.getBoolean(PREF_IS_FIRST_TIME_KEY, true)
        set(value) = mPreferences.edit().putBoolean(PREF_IS_FIRST_TIME_KEY, false).apply()

    // save device token for notifications status
    var deviceToken = mPreferences.getString(PREF_TOKEN_KEY, "")
        set(value) = mPreferences.edit().putString(PREF_TOKEN_KEY, value).apply()

    // save login status
    var isLoggedIn = mPreferences.getBoolean(PREF_IS_LOGGED_IN_KEY, false)
        set(value) = mPreferences.edit().putBoolean(PREF_IS_LOGGED_IN_KEY, value).apply()

    // save login user id
    var userId = mPreferences.getString(PREF_LOGGED_USER_ID_KEY, "")
        set(value) = mPreferences.edit().putString(PREF_LOGGED_USER_ID_KEY, value).apply()

    // save login user name
    var username = mPreferences.getString(PREF_LOGGED_USER_NAME_KEY, "")
        set(value) = mPreferences.edit().putString(PREF_LOGGED_USER_NAME_KEY, value).apply()

    var email = mPreferences.getString(PREF_USER_EMAIL_KEY, "")
        set(value) = mPreferences.edit().putString(PREF_USER_EMAIL_KEY, value).apply()

    // save login user photo
    var photoUrl = mPreferences.getString(PREF_PHOTO_URL_KEY, "")
        set(value) = mPreferences.edit().putString(PREF_PHOTO_URL_KEY, value).apply()

    //**********************************************************************************************
}

