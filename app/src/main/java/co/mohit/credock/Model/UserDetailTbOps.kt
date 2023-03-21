package co.mohit.credock.Model

import android.content.ContentValues
import android.content.Context
import co.mohit.credock.CD_Database_enums
import java.util.*

class UserDetailTbOps:ISQLOperation {

    override val tableToWorkOn: Int = CD_Database_enums.TB_USER_DETAILS.value.toInt()
    override var dbHelper: DatabaseHelper? = null

    constructor(context: Context)
    {
        dbHelper = DatabaseHelper(context)
    }

    override fun addRecord(obj: ContentValues): Int?
    {
        return dbHelper?.addRecord(tableToWorkOn, obj)
    }

    fun addRecordToDB(obj: UserDetailModel): Int? {

        var userObj = obj as UserDetailModel
        var contentValues = ContentValues()

        contentValues.put("AccountNo", userObj.userAccNo)
        contentValues.put("Name", userObj.userName)
        contentValues.put("EmailId", userObj.userEmail)
        contentValues.put("Age", userObj.userAge)
        contentValues.put("Gender", userObj.userGender)
        contentValues.put("Designation", userObj.userDesignation)
        contentValues.put("SecurityQueId", userObj.userSecurityQue)
        contentValues.put("SecurityAns", userObj.userSecurityAnswer)
        contentValues.put("SecurityPin", userObj.userLoginPin)
        contentValues.put("LastModifiedOn", userObj.lastModifiedOnTimeStamp)
        contentValues.put("CreatedOn", userObj.createdOnTimeStamp)

        var result = addRecord(contentValues)

        return result
    }

    override fun updateRecord(strVal: String, obj: Objects): Int {
        TODO("Not yet implemented")
    }

    override fun deleteRecord(strVal: String): Int {
        TODO("Not yet implemented")
    }
}