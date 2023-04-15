package co.mohit.credock.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.mohit.credock.CD_Database_enums
import co.mohit.credock.CD_Table_Operation
import co.mohit.credock.CD_UserTable_Column
import java.io.File
import java.util.Objects
import kotlin.properties.Delegates

private const val CREDOCK_DB = "CredockDB.db"
private const val DB_VERSION = 1;
private const val USER_DETAIL_TB = "UserDetailTable"
private const val USER_CREDENTIAL_TB = "UserCredentialTable"
private const val CREDENTIAL_TYPE_TB = "CredentialTypeTable"

class DatabaseHelper:SQLiteOpenHelper {

    private lateinit var tbName:String

    constructor(context: Context?) : super(context, CREDOCK_DB, null, DB_VERSION)
    {
        //Do Nothing
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val userDetialQuery = """ CREATE TABLE ${USER_DETAIL_TB} (
            AccountNo NVARCHAR(10) PRIMARY KEY NOT NULL,
            Name NVARCHAR(100) NOT NULL,
            EmailId NVARCHAR(100) NOT NULL,
            Age SMALLINT NOT NULL,
            Gender SMALLINT NOT NULL,
            Designation SMALLINT NOT NULL,
            SecurityQueId SMALLINT NOT NULL,
            SecurityAns NVARCHAR(250) NOT NULL,
            SecurityPin INTEGER UNIQUE NOT NULL,
            LastModifiedOn DATETIME NOT NULL,
            CreatedOn DATETIME NOT NULL
         )""".trimIndent()

        db?.execSQL(userDetialQuery)

        val credentialTypeQuery = """ CREATE TABLE ${CREDENTIAL_TYPE_TB} (
            CredAccNo NVARCHAR(5) PRIMARY KEY NOT NULL,
            Description NVARCHAR(20) NOT NULL            
        )""".trimIndent()

        db?.execSQL(credentialTypeQuery)

        val userCredentialQuery = """CREATE TABLE ${USER_CREDENTIAL_TB}(
            DataID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            AccountNo NVARCHAR(10) NOT NULL,
            CredAccNo NVARCHAR(5) NOT NULL,
            Platform NVARCHAR(20) NOT NULL,
            Data BLOB NOT NULL,
            NumberOfTags SMALLINT NOT NULL,
            LastModifiedOn DATETIME NOT NULL,
            CreatedOn DATETIME NOT NULL,
            FOREIGN KEY(AccountNo) REFERENCES ${USER_DETAIL_TB} (AccountNo),
            FOREIGN KEY(CredAccNo) REFERENCES ${CREDENTIAL_TYPE_TB} (CredAccNo)
        )""".trimIndent()

        db?.execSQL(userCredentialQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addRecord(token:Int,obj:ContentValues):Int
    {
        determineTableName(token)
        val db = this.writableDatabase

        val result = db.insert(tbName,null, obj as ContentValues)

        return result.toInt()
    }

    fun updateRecord(token:Int,obj: ContentValues,whereClause:String,whereClauseValues:Array<String>):Int
    {
        determineTableName(token)
        val db = this.writableDatabase
        return db.update(tbName,obj,whereClause,whereClauseValues)

    }

    fun deleteRecord(token: Int):Int
    {
        var tb_name = determineTableName(token)
        return CD_Table_Operation.TB_OPERATION_SUCCESS.value.toInt()
    }

    fun determineTableName(token:Int):Unit
    {
        tbName = when(token)
        {
            CD_Database_enums.TB_USER_DETAILS.value.toInt() -> USER_DETAIL_TB;
            CD_Database_enums.TB_USER_CREDENTIALS.value.toInt() ->USER_CREDENTIAL_TB;
            CD_Database_enums.TB_CREDENTIAL_TYPE.value.toInt() -> CREDENTIAL_TYPE_TB;
            else -> {"Not Applicable"}
        }
    }

    fun isDatabaseAlreadyExist():Boolean
    {
        var dbFilePath = "/data/data/co.mohit.credock/databases/${CREDOCK_DB}"
        var file = File(dbFilePath)
        return file.exists()
    }

    fun fetchUserId(userPin:Int):String?
    {
        var retVal:String? = null
        val db = this.readableDatabase
        val cursor:Cursor = db.rawQuery("SELECT ${CD_UserTable_Column.ColAccNo.colName} FROM ${USER_DETAIL_TB} WHERE ${CD_UserTable_Column.ColSecPin.colName} = '${userPin.toString()}'",null)
        while(cursor.moveToNext())
        {
            retVal = cursor.getString(0)
        }
        return retVal
    }
}