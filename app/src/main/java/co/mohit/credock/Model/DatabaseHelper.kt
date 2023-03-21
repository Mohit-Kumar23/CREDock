package co.mohit.credock.Model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.mohit.credock.CD_Database_enums
import co.mohit.credock.CD_Table_Operation
import java.io.File
import java.util.Objects
import kotlin.properties.Delegates

private const val CREDOCK_DB = "CredockDB.db"
private const val DB_VERSION = 1;
private const val USER_DETAIL_TB = "UserDetailTable"
private const val USER_CREDENTIAL_TB = "UserCredentialTable"
private const val CREDENTIAL_TYPE_TB = "CredentialTypeTable"

class DatabaseHelper:SQLiteOpenHelper {

    constructor(context: Context?) : super(context, CREDOCK_DB, null, DB_VERSION)
    {
        //Do Nothing
    }

    override fun onCreate(db: SQLiteDatabase?) {

        var userDetialQuery = """ CREATE TABLE ${USER_DETAIL_TB} (
            UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            AccountNo NVARCHAR(10) UNIQUE NOT NULL,
            Name NVARCHAR(100) NOT NULL,
            EmailId NVARCHAR(100) NOT NULL,
            Age SMALLINT NOT NULL,
            Gender NVARCHAR(6) NOT NULL,
            Designation NVARCHAR(50) NOT NULL,
            SecurityQueId SMALLINT NOT NULL,
            SecurityAns NVARCHAR(250) NOT NULL,
            SecurityPin INTEGER UNIQUE  NOT NULL,
            LastModifiedOn TEXT NOT NULL,
            CreatedOn TEXT NOT NULL
        )""".trimIndent()

        db?.execSQL(userDetialQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addRecord(token:Int,obj:ContentValues):Int
    {
        var tb_name = determineTableName(token)
        var db = this.writableDatabase

        var result = db.insert(tb_name,null, obj as ContentValues)

        return result.toInt()
    }

    fun updateRecord(token:Int):Int
    {
        var tb_name = determineTableName(token)
        return CD_Table_Operation.TB_OPERATION_SUCCESS.value.toInt()
    }

    fun deleteRecord(token: Int):Int
    {
        var tb_name = determineTableName(token)
        return CD_Table_Operation.TB_OPERATION_SUCCESS.value.toInt()
    }

    fun determineTableName(token:Int):String
    {
        var tb_name:String = "";
        when(token)
        {
            CD_Database_enums.TB_USER_DETAILS.value.toInt() -> tb_name = USER_DETAIL_TB;
            CD_Database_enums.TB_USER_CREDENTIALS.value.toInt() -> tb_name = USER_CREDENTIAL_TB;
            CD_Database_enums.TB_CREDENTIAL_TYPE.value.toInt() -> tb_name = CREDENTIAL_TYPE_TB;
        }
        return tb_name
    }

    fun isDatabaseAlreadyExist():Boolean
    {
        var dbFilePath = "/data/data/co.mohit.credock/databases/${CREDOCK_DB}"
        var file = File(dbFilePath)
        return file.exists()
    }
}