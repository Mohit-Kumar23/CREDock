package co.mohit.credock.Controller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import co.mohit.credock.Model.DatabaseHelper

class DatabaseService {

    private lateinit var dbHelper:DatabaseHelper;
   fun createDBService(context:Context)
    {
        Thread(Runnable{
            try {
                dbHelper = DatabaseHelper(context)
                if(!(dbHelper.isDatabaseAlreadyExist()))
                {
                    dbHelper.onCreate(dbHelper.writableDatabase)
                }
            }
            catch (ex:Exception)
            {
                Log.e("Create DB Exception",ex.message.toString())
            }
        }).start()
   }

    fun insertUserToCDUserTableWrap()
    {

    }

    fun updateUserInfoToCDUserTableWrap()
    {

    }

    fun deleteUserFromCDUserTableWrap()
    {

    }
}