package co.mohit.credock.Model

import android.content.ContentValues
import android.content.Context
import co.mohit.credock.CD_Database_enums
import co.mohit.credock.CD_UserTable_Column
import java.util.*

class UserDetailTbOps:ISQLOperation {



    override val tableToWorkOn: Int = CD_Database_enums.TB_USER_DETAILS.value.toInt()
    override var dbHelper: DatabaseHelper? = null
    private val whereClause:String = CD_UserTable_Column.ColAccNo.toString() + " = ?"

    constructor(context: Context)
    {
        dbHelper = DatabaseHelper(context)
    }

    override fun addRecord(obj: ContentValues): Int?
    {
        return dbHelper?.addRecord(tableToWorkOn, obj)
    }


    fun addRecordToDB(userObj: UserDetailModel): Int? {

        var contentValues = ContentValues()

        contentValues.put(CD_UserTable_Column.ColAccNo.colName, userObj.userAccNo)
        contentValues.put(CD_UserTable_Column.ColName.colName, userObj.userName)
        contentValues.put(CD_UserTable_Column.ColEmail.colName, userObj.userEmail)
        contentValues.put(CD_UserTable_Column.ColAge.colName, userObj.userAge)
        contentValues.put(CD_UserTable_Column.ColGender.colName, userObj.userGender)
        contentValues.put(CD_UserTable_Column.ColDesignation.colName, userObj.userDesignation)
        contentValues.put(CD_UserTable_Column.ColSecQueId.colName, userObj.userSecurityQue)
        contentValues.put(CD_UserTable_Column.ColSecAns.colName, userObj.userSecurityAnswer)
        contentValues.put(CD_UserTable_Column.ColSecPin.colName, userObj.userLoginPin)
        contentValues.put(CD_UserTable_Column.ColLastModOn.colName, userObj.lastModifiedOnTimeStamp)
        contentValues.put(CD_UserTable_Column.ColCreatedOn.colName, userObj.createdOnTimeStamp)

        var result = addRecord(contentValues)

        return result
    }

    override fun updateRecord(strVal: String, obj: ContentValues): Int? {
        val whereClauseValue = arrayOf(strVal)
        return dbHelper?.updateRecord(tableToWorkOn,obj,whereClause,whereClauseValue)
    }

    fun updateRecordToDB(strVal: String, obj: UserDetailModel): Int?
    {
        val cv = ContentValues()
        cv.put(CD_UserTable_Column.ColAge.colName,obj.userAge)
        cv.put(CD_UserTable_Column.ColEmail.colName,obj.userEmail)
        cv.put(CD_UserTable_Column.ColDesignation.colName,obj.userDesignation)
        cv.put(CD_UserTable_Column.ColSecQueId.colName,obj.userSecurityQue)
        cv.put(CD_UserTable_Column.ColSecAns.colName,obj.userSecurityAnswer)
        cv.put(CD_UserTable_Column.ColSecPin.colName,obj.userLoginPin)
        cv.put(CD_UserTable_Column.ColLastModOn.colName,obj.lastModifiedOnTimeStamp)

        return updateRecord(strVal,cv)
    }

    override fun deleteRecord(strVal: String): Int? {
        TODO("Not yet implemented")
    }
}