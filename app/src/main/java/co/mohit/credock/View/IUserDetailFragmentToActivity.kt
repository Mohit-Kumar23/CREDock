package co.mohit.credock.View

import android.content.ContentValues

interface IUserDetailFragmentToActivity {

    fun getUserProfileDetails(contentValues: ContentValues?):Unit
    fun getUserPinSetupEvents(contentValues: ContentValues?):Unit
}