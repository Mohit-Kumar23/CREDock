package co.mohit.credock

enum class CD_Database_enums(val value:Int) {
    TB_USER_DETAILS(1),
    TB_USER_CREDENTIALS(2),
    TB_CREDENTIAL_TYPE(3)
}

enum class CD_Database_Operation(val value:Int)
{
    DB_OPERATION_SUCCESS(1),
    DB_OPERATION_FAILED(-1)
}

enum class CD_Table_Operation(val value:Int)
{
    TB_OPERATION_SUCCESS(1),
    TB_OPERATION_FAILED(-1)
}