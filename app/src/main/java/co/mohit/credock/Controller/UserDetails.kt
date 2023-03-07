package co.mohit.credock.Controller

import co.mohit.credock.CD_UserDesignation_enum
import co.mohit.credock.CD_UserGender_enum
import co.mohit.credock.CD_UserSecurityQue_enum

public class UserDetails {

    var userID:String? = null
        get() = field
        set(value) {field = value}

    var userName:String? = null
        get() = field
        set(value) {field = value}

    var userEmail:String? = null
        get() = field
        set(value) {field = value}

    var userAge:Int? = null
        get() = field
        set(value) {field = value}

    var userGender:Int? = null
        get() = field
        set(value) {field = value}

    var userDesignation:Int? = null
        get() = field
        set(value) {field = value}

    var userSecurityQues:Int? = null
        get() = field
        set(value) {field = value}

    var userSecurityAnswer:String? = null
        get() = field
        set(value) {field = value}

    var userLoginPin:Int? = null
        get() = field
        set(value) {field = value}

    private val genderList:List<String> = listOf("Male","Female","Other")
    private val designationList:List<String> = listOf("Student","Professional","Defence","Business","Home-maker","Freelancer","Govt. Employee")
    private val securityQueList:List<String> = listOf("What is your childhood friend name?"
                                            ,"What is your favourite hobby?"
                                            ,"What is your favourite subject to study?"
                                            ,"What is name of your high school?")

    public fun getGenderText(genderEnum:Int):String
    {
        var str_genderValue:String = ""
        when(genderEnum)
        {
            CD_UserGender_enum.MALE.value.toInt() -> str_genderValue = genderList[0]
            CD_UserGender_enum.FEMALE.value.toInt() -> str_genderValue = genderList[1]
            CD_UserGender_enum.OTHER.value.toInt() -> str_genderValue = genderList[2]
        }
        return str_genderValue
    }

    public fun getDesignationText(designationEnum:Int):String
    {
        var str_designationValue:String = ""
        when(designationEnum)
        {
            CD_UserDesignation_enum.STUDENT.value.toInt() -> str_designationValue = designationList[0]
            CD_UserDesignation_enum.PROFESSIONAL.value.toInt() -> str_designationValue = designationList[1]
            CD_UserDesignation_enum.DEFENCE.value.toInt() -> str_designationValue = designationList[2]
            CD_UserDesignation_enum.BUSINESS.value.toInt() -> str_designationValue = designationList[3]
            CD_UserDesignation_enum.HOME_MAKER.value.toInt() -> str_designationValue = designationList[4]
            CD_UserDesignation_enum.FREELANCER.value.toInt() -> str_designationValue = designationList[5]
            CD_UserDesignation_enum.GOVT_EMPOLYEE.value.toInt() -> str_designationValue = designationList[6]
        }
        return str_designationValue
    }

    public fun getSecurityQueText(securityQueEnum:Int):String
    {
        var str_securityQueValue:String = ""
        when(securityQueEnum)
        {
            CD_UserSecurityQue_enum.CHILDHOOD_FRIEND_NAME.value.toInt() -> str_securityQueValue = securityQueList[0]
            CD_UserSecurityQue_enum.FAVOURITE_HOBBY.value.toInt() -> str_securityQueValue = securityQueList[1]
            CD_UserSecurityQue_enum.FAVOURITE_SUBJECT.value.toInt() -> str_securityQueValue = securityQueList[2]
        }
        return str_securityQueValue
    }

    public fun prepareUserID(otpReceived:Int)
    {
        var nameArray = this.userName?.split(" ")
        for(str in nameArray!!.iterator())
        {
            this.userID += str[0].toUpperCase()
        }
        this.userID += "_"
        this.userID += otpReceived.toString()
    }

}