package co.mohit.credock.Controller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import co.mohit.credock.Model.DatabaseHelper
import co.mohit.credock.Model.UserDetailModel
import co.mohit.credock.Model.UserDetailTbOps
import java.util.Objects

object DatabaseService:java.io.Serializable{

    private lateinit var dbHelper:DatabaseHelper;
    public var isDBCreated = false
    var loggedInUserID:String? = null

   fun createDBService(context: Context)
    {
        dbHelper = DatabaseHelper(context)
        if(!(dbHelper.isDatabaseAlreadyExist()))
        {
            dbHelper.onCreate(dbHelper.writableDatabase)
            isDBCreated = true
            //closeConnectionFromDB()
        }
        else
        {
            isDBCreated = true
        }
   }

    fun insertUserToCDUserTableService(context: Context,userDetails: UserDetailsController):Int?
    {
        return UserDetailTbOps(context).addRecordToDB(userDetails.getUserDetailModelInstance())
    }

    fun updateUserInfoToCDUserTableService(context:Context,userID:String,userDetails:UserDetailsController):Int?
    {
        return UserDetailTbOps(context).updateRecordToDB(userID,userDetails.getUserDetailModelInstance())
    }

    fun deleteUserFromCDUserTableService(context:Context,userID:String):Int?
    {
        return UserDetailTbOps(context).deleteRecord(userID)
    }

    fun fetchUserIDFromDB(userPin: Int):Int
    {
        loggedInUserID = dbHelper.fetchUserId(userPin);
        if(!loggedInUserID.isNullOrEmpty())
        {
            return 1
        }
        else
        {
            return 0
        }
    }

    fun closeConnectionFromDB()
    {
        dbHelper.close()
    }
}