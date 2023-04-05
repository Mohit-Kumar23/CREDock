package co.mohit.credock.View

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.mohit.credock.*
import co.mohit.credock.Controller.*
import kotlinx.android.synthetic.main.activity_account_create.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AccountCreateActivity : AppCompatActivity(),IUserDetailFragmentToActivity
{
    val userProfileFrag = UserProfileInputDetailsFragment()
    val userPinFrag = UserPinSetupFragment()
    val fragmentMgr = supportFragmentManager

    //User Information Parameters
    var str_userName:String? = null
    var str_userEmail:String? = null
    var str_userAge:String? = null
    var userSelectedGenderId:Int? = null
    var userSelectDesignationId:Int? = null
    var userSelectedSecurityQueId:Int? = null
    var str_userSecurityAnswer:String? = null

    private var str_newPin: String? = null
    private var str_reEnteredPin: String? = null
    private var str_otpVerify: String? = null
    lateinit var emailVerifyService:EmailVerificationService;
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
            userProfileFrag.performOperationOnNextBtnClick()
        })

        btn_previous.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.VISIBLE
            btn_previous.visibility = View.GONE
            btn_userDetialSubmit.visibility = View.GONE
            tv_resendOtpText.visibility = View.GONE
            initializeFragments(userProfileFrag)
        })

        btn_userDetialSubmit.setOnClickListener(View.OnClickListener {
                userPinFrag.validateUserPinAndOtp()
        })

        tv_resendOtpText.setOnClickListener(View.OnClickListener {
            if(emailVerifyService == null)
            {
                emailVerifyService = EmailVerificationService(GetSendGridApiKey(),str_userName!!,str_userEmail!!)
            }
            emailVerifyService.sendOTPForEmailVerification()
        })
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
        cUserDetial.userSecurityAnswer = str_userSecurityAnswer?.trim()
        cUserDetial.userLoginPin = str_newPin?.toInt()
        val currentDateTime = LocalDateTime.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy/HH:mm:ss")
        cUserDetial.lastModifiedOnTimeStamp = currentDateTime.format(dateTimeFormatter)
        cUserDetial.createdOnTimeStamp = currentDateTime.format(dateTimeFormatter)

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
                Log.e("Record Added","Successfully Added")
            }
            else
            {
                Log.e("Record Failed to add","Fail to Added")
            }
        }

    }

    override fun getUserPinSetupEvents(contentValues: ContentValues?) {
        if(contentValues != null)
        {
            Toast.makeText(this@AccountCreateActivity,"Verification Successfully Done!!!",Toast.LENGTH_LONG).show()
            Thread(Runnable
            {    /*Running the Code in Background*/
                str_newPin = contentValues.get("userPin")?.toString()
                str_otpVerify = contentValues.get("otpReceived")?.toString()
                //Prepare UserDetails class and store in DB
                prepareAndStoreUserDetialsToDB()
                runOnUiThread{
                    //may be some animation
                }
            }).start()
        }
    }

    override fun getUserProfileDetails(contentValues: ContentValues?) {

        if(contentValues != null)
        {
            str_userName = contentValues.get("userName")?.toString()
            str_userEmail = contentValues.get("userEmail")?.toString()
            str_userAge = contentValues.get("userAge")?.toString()
            userSelectedGenderId = contentValues.getAsInteger("userSelectedGenderId")
            userSelectDesignationId = contentValues.getAsInteger("userSelectDesignationId")
            userSelectedSecurityQueId = contentValues.getAsInteger("userSelectedSecurityQueId")
            str_userSecurityAnswer = contentValues.get("userSecurityAnswer")?.toString()

            emailVerifyService = EmailVerificationService(
                GetSendGridApiKey().toString(),
                str_userName!!,
                str_userEmail!!)

            emailVerifyService.sendOTPForEmailVerification()
            moveToUserPinSetupFragment()
        }
    }

    private fun moveToUserPinSetupFragment()
    {
        btn_next.visibility = View.GONE
        btn_previous.visibility = View.VISIBLE
        btn_userDetialSubmit.visibility = View.VISIBLE
        tv_resendOtpText.visibility = View.VISIBLE
        userPinFrag.initializeParamsForEmailService(emailVerifyService)
        initializeFragments(userPinFrag)
    }
}