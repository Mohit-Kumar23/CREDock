package co.mohit.credock

enum class CD_UserTable_Column(val colName: String, val indexPosition: Int)
{
    ColAccNo("AccountNo", 0),
    ColName("Name", 1),
    ColEmail("EmailId", 2),
    ColAge("Age", 3),
    ColGender("Gender", 4),
    ColDesignation("Designation", 5),
    ColSecQueId("SecurityQueId", 6),
    ColSecAns("SecurityAns", 7),
    ColSecPin("SecurityPin", 8),
    ColLastModOn("LastModifiedOn", 9),
    ColCreatedOn("CreatedOn", 10)
}

enum class CD_UserCredentialTable_Column(val colName:String, val indexPositon:Int)
{
    ColDataId("DataID",0),
    ColAccNo("AccountNo",1),
    ColCredAccNo("CredAccNo",2),
    ColPlatform("Platform",3),
    ColData("Data",4),
    ColNoOfTags("NumberOfTags",5),
    ColLastModOn("LastModifiedOn",6),
    ColCreatedOn("CreatedOn",7)
}

enum class CD_CredentialTypeTable_Column(val colName:String,val indexPosition:Int)
{
    ColCredAccNo("CredAccNo",0),
    ColDesc("Description",1)
}
