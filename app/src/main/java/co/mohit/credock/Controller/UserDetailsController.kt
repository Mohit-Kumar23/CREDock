package co.mohit.credock.Controller

import co.mohit.credock.CD_UserDesignation_enum
import co.mohit.credock.CD_UserGender_enum
import co.mohit.credock.CD_UserSecurityQue_enum
import co.mohit.credock.Model.UserDetailModel

public class UserDetailsController {

    private var userDetailModel:UserDetailModel
    constructor()
    {
        userDetailModel = UserDetailModel()
    }

    var userID:Long? = null
        get() = userDetailModel.userID

    var userAccNo:String?
        get() = userDetailModel.userAccNo
        set(value) { userDetailModel.userAccNo = value}

    var userName:String?
        get() = userDetailModel.userName
        set(value) { userDetailModel.userName = value}

    var userEmail:String?
        get() = userDetailModel.userEmail
        set(value) {userDetailModel.userEmail = value}

    var userAge:Int?
        get() = userDetailModel.userAge
        set(value) {userDetailModel.userAge = value}

    var userGender:Int?
        get() = userDetailModel.userGender
        set(value) {userDetailModel.userGender = value}

    var userDesignation:Int?
        get() = userDetailModel.userDesignation
        set(value) {userDetailModel.userDesignation = value}

    var userSecurityQues:Int?
        get() = userDetailModel.userSecurityQue
        set(value) {userDetailModel.userSecurityQue = value}

    var userSecurityAnswer:String?
        get() = userDetailModel.userSecurityAnswer
        set(value) {userDetailModel.userSecurityAnswer = value}

    var userLoginPin:Int?
        get() = userDetailModel.userLoginPin
        set(value) {userDetailModel.userLoginPin = value}

    var lastModifiedOnTimeStamp:String?
        get() = userDetailModel.lastModifiedOnTimeStamp
        set(value) {userDetailModel.lastModifiedOnTimeStamp = value}

    var createdOnTimeStamp:String?
        get() = userDetailModel.createdOnTimeStamp
        set(value) {userDetailModel.createdOnTimeStamp = value}

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
        var accNo:String? = null

        var nameArray = this.userName?.split(" ")
        for(str in nameArray!!.iterator())
        {
            if(accNo.isNullOrEmpty())
                accNo = str[0].toUpperCase().toString()
            else
                accNo += str[0].toUpperCase()
        }
        accNo += "_"
        accNo += otpReceived.toString()

        this.userAccNo = accNo
    }

    public fun getUserDetailModelInstance():UserDetailModel
    {
        return userDetailModel
    }
}