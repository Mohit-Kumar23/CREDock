package co.mohit.credock.Model

import android.content.ContentValues
import java.util.Objects

interface ISQLOperation {

    val tableToWorkOn:Int
    var dbHelper:DatabaseHelper?

    fun addRecord(obj: ContentValues):Int?
    fun updateRecord(strVal:String,obj: ContentValues):Int?
    fun deleteRecord(strVal:String):Int?
}