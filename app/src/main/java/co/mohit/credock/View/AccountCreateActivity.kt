package co.mohit.credock.View

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.se.omapi.Session
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.mohit.credock.CD_Global_enums
import co.mohit.credock.Controller.EmailVerificationService
import co.mohit.credock.R
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*

class AccountCreateActivity : AppCompatActivity() {

    val userProfileFrag = UserProfileDetails()
    val userPinFrag = UserPinSetup()
    val fragmentMgr = supportFragmentManager

    private var str_newPin: String? = null
    private var str_reEnteredPin: String? = null
    private var str_otpVerify: String? = null
    private lateinit var emailVerifyService:EmailVerificationService;

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

    }

    override fun onResume() {
        super.onResume()

        btn_next.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.GONE
            btn_previous.visibility = View.VISIBLE
            btn_userDetialSubmit.visibility = View.VISIBLE
            initializeFragments(userPinFrag)


            emailVerifyService = EmailVerificationService(GetSendGridApiKey().toString(),"kvnl.mohitchanchlani@gmail.com","Mohit Kumar")
            var value = emailVerifyService.sendOTPForEmailVerification()
        })

        btn_previous.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.VISIBLE
            btn_previous.visibility = View.GONE
            btn_userDetialSubmit.visibility = View.GONE
            initializeFragments(userProfileFrag)
        })

        btn_userDetialSubmit.setOnClickListener(View.OnClickListener {
            if (validateUserPinAndOtp() == CD_Global_enums.XX_OK.value.toInt()) {
                Toast.makeText(this@AccountCreateActivity,"Verification Successfully Done!!!",Toast.LENGTH_LONG).show()
            }
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
}