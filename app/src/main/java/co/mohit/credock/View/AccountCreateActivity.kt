package co.mohit.credock.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.Data
import android.se.omapi.Session
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.mohit.credock.*
import co.mohit.credock.Controller.*
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*
import kotlinx.android.synthetic.main.fragment_user_profile_details.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.logging.SimpleFormatter

class AccountCreateActivity : AppCompatActivity() {

    val userProfileFrag = UserProfileDetails()
    val userPinFrag = UserPinSetup()
    val fragmentMgr = supportFragmentManager

    //User Information Parameters
    var str_userName:String? = null
    var str_userEmail:String? = null
    var str_userAge:String? = null
    var userSelectedGenderId:Int? = null
    var userSelectDesignationId:Int? = null
    var userSelectedSecurityQueId:Int? = null
    var str_userSecurityAnser:String? = null

    private var str_newPin: String? = null
    private var str_reEnteredPin: String? = null
    private var str_otpVerify: String? = null
    public lateinit var emailVerifyService:EmailVerificationService;
    private lateinit var dbService:DatabaseService;

    init {
        System.loadLibrary("api_keys")
    }

    external fun GetSendGridApiKey(): String

    private fun initializeFragments(fragment: Fragment) {
        val fragmentTransaction = fragmentMgr.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_userDetials, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_create)

        initializeFragments(userProfileFrag)
        //dbService = intent.extras?.get("DBHelperInstance") as DatabaseService
        dbService = DatabaseService()
        dbService.createDBService(this@AccountCreateActivity)

    }

    override fun onResume() {
        super.onResume()

        btn_next.setOnClickListener(View.OnClickListener {
            performOperationOnNextBtnClick()
        })

        btn_previous.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.VISIBLE
            btn_previous.visibility = View.GONE
            btn_userDetialSubmit.visibility = View.GONE
            tv_resendOtpText.visibility = View.GONE
            initializeFragments(userProfileFrag)
        })

        btn_userDetialSubmit.setOnClickListener(View.OnClickListener {
            if (validateUserPinAndOtp() == CD_Global_enums.XX_OK.value.toInt()) {
                Toast.makeText(this@AccountCreateActivity,"Verification Successfully Done!!!",Toast.LENGTH_LONG).show()

               Thread(Runnable
               {    /*Running the Code in Background*/
                    //Prepare UserDetails class and store in DB
                   prepareAndStoreUserDetialsToDB()
                    runOnUiThread{

                    }
               }).start()
            }
        })

        tv_resendOtpText.setOnClickListener(View.OnClickListener {
            if(emailVerifyService == null)
            {
                emailVerifyService = EmailVerificationService(GetSendGridApiKey(),str_userName!!,str_userEmail!!)
            }
            emailVerifyService.sendOTPForEmailVerification()
        })
    }

    fun checkAndMatchUserPin(): Int {
        if (str_newPin != null)
            str_newPin = null
        if (str_reEnteredPin != null)
            str_reEnteredPin = null

        str_newPin = et_newPinDigit1.text?.toString()
        str_newPin += et_newPinDigit2.text
        str_newPin += et_newPinDigit3.text
        str_newPin += et_newPinDigit4.text

        str_reEnteredPin = et_reEnterPinDigit1.text?.toString()
        str_reEnteredPin += et_reEnterPinDigit2.text
        str_reEnteredPin += et_reEnterPinDigit3.text
        str_reEnteredPin += et_reEnterPinDigit4.text

        if (str_newPin.isNullOrEmpty() || str_reEnteredPin.isNullOrEmpty())
            return CD_Global_enums.XX_NOTFOUND.value.toInt()
        else if (str_newPin.equals(str_reEnteredPin))
            return CD_Global_enums.XX_OK.value.toInt()
        else
            return CD_Global_enums.XX_ERROR.value.toInt()
    }

    fun verifyOtpEntered(): Int {
        if (!str_otpVerify.isNullOrEmpty())
            str_otpVerify = null

        var otpDigit1Et = findViewById<TextView>(R.id.et_otpDigit1)
        var otpDigit2Et = findViewById<TextView>(R.id.et_otpDigit2)
        var otpDigit3Et = findViewById<TextView>(R.id.et_otpDigit3)
        var otpDigit4Et = findViewById<TextView>(R.id.et_otpDigit4)

        str_otpVerify = otpDigit1Et.text?.toString()
        str_otpVerify += otpDigit2Et.text
        str_otpVerify += otpDigit3Et.text
        str_otpVerify += otpDigit4Et.text

        if (!str_otpVerify.isNullOrEmpty())
            return emailVerifyService.verifyInputOtp(str_otpVerify!!.toInt())
        else
            return CD_Global_enums.XX_NOTFOUND.value.toInt()
    }

    fun validateUserPinAndOtp(): Int {
        var value = checkAndMatchUserPin()
        if (value == CD_Global_enums.XX_OK.value.toInt())
        {
            value = verifyOtpEntered()
            if (value == CD_Global_enums.XX_OK.value.toInt())
            {
                //do nothing
            }
            else if (value == CD_Global_enums.XX_ERROR.value.toInt())
            {
                Toast.makeText(
                    this@AccountCreateActivity,
                    "Invalid OTP entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                Toast.makeText(
                    this@AccountCreateActivity,
                    "Please Enter Received OTP!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else if (value == CD_Global_enums.XX_ERROR.value.toInt())
        {
            Toast.makeText(
                this@AccountCreateActivity,
                "Two Entered Pins Mismatch!",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            Toast.makeText(
                this@AccountCreateActivity,
                "Please Enter Your New Pin",
                Toast.LENGTH_SHORT
            ).show()
        }
        return value;
    }

    fun performOperationOnNextBtnClick()
    {
        if(!et_userName.text.isNullOrEmpty() and !et_userEmail.text.isNullOrEmpty() and !et_userAge.text.isNullOrEmpty()
        and (sp_userGender.selectedItemPosition != 0) and (sp_userDesignation.selectedItemPosition != 0)
        and (sp_userSecurityQuestion.selectedItemPosition != 0) and !et_userSecurityAnswer.text.isNullOrEmpty())
        {
            str_userName = et_userName.text.toString()
            str_userEmail = et_userEmail.text.toString()
            str_userAge = et_userAge.text.toString()
            when(sp_userGender.selectedItemPosition)
            {
                0 -> userSelectedGenderId = CD_UserGender_enum.MALE.value.toInt();
                1 -> userSelectedGenderId = CD_UserGender_enum.FEMALE.value.toInt()
                2 -> userSelectedGenderId = CD_UserGender_enum.OTHER.value.toInt()
            }
            when(sp_userDesignation.selectedItemPosition)
            {
                0 -> userSelectDesignationId = CD_UserDesignation_enum.STUDENT.value.toInt()
                1 -> userSelectDesignationId = CD_UserDesignation_enum.PROFESSIONAL.value.toInt()
                2 -> userSelectDesignationId = CD_UserDesignation_enum.DEFENCE.value.toInt()
                3 -> userSelectDesignationId = CD_UserDesignation_enum.BUSINESS.value.toInt()
                4 -> userSelectDesignationId = CD_UserDesignation_enum.HOME_MAKER.value.toInt()
                5 -> userSelectDesignationId = CD_UserDesignation_enum.FREELANCER.value.toInt()
                6 -> userSelectDesignationId = CD_UserDesignation_enum.GOVT_EMPOLYEE.value.toInt()
            }
            when(sp_userSecurityQuestion.selectedItemPosition)
            {
                0 -> userSelectedSecurityQueId = CD_UserSecurityQue_enum.CHILDHOOD_FRIEND_NAME.value.toInt()
                1 -> userSelectedSecurityQueId = CD_UserSecurityQue_enum.FAVOURITE_HOBBY.value.toInt()
                2 -> userSelectedSecurityQueId = CD_UserSecurityQue_enum.FAVOURITE_SUBJECT.value.toInt()
                3 -> userSelectedSecurityQueId = CD_UserSecurityQue_enum.HIGH_SCHOOL_NAME.value.toInt()
            }
            str_userSecurityAnser = et_userSecurityAnswer.text.toString()


            btn_next.visibility = View.GONE
            btn_previous.visibility = View.VISIBLE
            btn_userDetialSubmit.visibility = View.VISIBLE
            tv_resendOtpText.visibility = View.VISIBLE
            initializeFragments(userPinFrag)

            emailVerifyService = EmailVerificationService(
                GetSendGridApiKey().toString(),
                str_userName!!,
                str_userEmail!!
            )
            var value = emailVerifyService.sendOTPForEmailVerification()
        }
        else
        {
            Toast.makeText(this@AccountCreateActivity,"Please Fill all Details",Toast.LENGTH_SHORT).show()
        }
    }

    fun prepareAndStoreUserDetialsToDB()
    {
        var cUserDetial = UserDetailsController()

        cUserDetial.userName = str_userName?.trim()
        cUserDetial.userEmail = str_userEmail?.trim()
        cUserDetial.userAge = str_userAge?.trim()?.toInt()
        cUserDetial.userGender = userSelectedGenderId?.toInt()
        cUserDetial.userDesignation = userSelectDesignationId?.toInt()
        cUserDetial.userSecurityQues = userSelectedSecurityQueId?.toInt()
        cUserDetial.userSecurityAnswer = str_userSecurityAnser?.trim()
        cUserDetial.userLoginPin = str_newPin?.toInt()
        cUserDetial.lastModifiedOnTimeStamp = SimpleDateFormat.getDateTimeInstance().toString()
        cUserDetial.createdOnTimeStamp = SimpleDateFormat.getDateTimeInstance().toString()

        cUserDetial.prepareUserID(str_otpVerify!!.toInt())
        if(dbService == null)
        {
            dbService = DatabaseService()
            if(!dbService.isDBCreated)
            {
                dbService.createDBService(this@AccountCreateActivity)
            }
        }
        var result = dbService.insertUserToCDUserTableService(this,cUserDetial)
        if(result != null)
        {
            if(result != -1)
            {
                Toast.makeText(this@AccountCreateActivity,"Successfully Added",Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this@AccountCreateActivity,"Failed",Toast.LENGTH_LONG).show()
            }
        }

    }
}