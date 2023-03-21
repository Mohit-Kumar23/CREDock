package co.mohit.credock.Controller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import co.mohit.credock.Model.DatabaseHelper
import co.mohit.credock.Model.UserDetailModel
import co.mohit.credock.Model.UserDetailTbOps
import java.util.Objects

class DatabaseService:java.io.Serializable{

    private lateinit var dbHelper:DatabaseHelper;
    public var isDBCreated = false
   fun createDBService(context:Context)
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

    fun updateUserInfoToCDUserTableService()
    {

    }

    fun deleteUserFromCDUserTableService()
    {

    }

    fun closeConnectionFromDB()
    {
        dbHelper.close()
    }
}