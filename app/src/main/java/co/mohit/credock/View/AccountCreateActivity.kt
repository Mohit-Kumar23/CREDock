package co.mohit.credock.View

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.mohit.credock.CD_Global_enums
import co.mohit.credock.R
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*

class AccountCreateActivity : AppCompatActivity() {

    val userProfileFrag = UserProfileDetails()
    val userPinFrag = UserPinSetup()
    val fragmentMgr = supportFragmentManager

    private var str_newPin:String? = null
    private var str_reEnteredPin:String? = null
    private var str_otpVerify: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_create)

        initializeFragments(userProfileFrag)

    }

    private fun initializeFragments(fragment:Fragment) {
        val fragmentTransaction = fragmentMgr.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_userDetials,fragment)
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()

        btn_next.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.GONE
            btn_previous.visibility = View.VISIBLE
            btn_userDetialSubmit.visibility = View.VISIBLE
            initializeFragments(userPinFrag)
        })

        btn_previous.setOnClickListener(View.OnClickListener {
            btn_next.visibility = View.VISIBLE
            btn_previous.visibility = View.GONE
            btn_userDetialSubmit.visibility = View.GONE
            initializeFragments(userProfileFrag)
        })

        btn_userDetialSubmit.setOnClickListener(View.OnClickListener {
            var value = checkAndMatchUserPin()
            if(value.equals(CD_Global_enums.XX_OK.value.toInt()))
            {
                value = verifyOtpEntered()
                if(value.equals(CD_Global_enums.XX_OK.value.toInt()))
                {
                    value = CD_Global_enums.XX_OK.value.toInt()
                }
                else if(value.equals(CD_Global_enums.XX_ERROR.value.toInt()))
                {
                    value = CD_Global_enums.XX_ERROR.value.toInt()
                    Toast.makeText(this@AccountCreateActivity,"Invalid OTP entered",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this@AccountCreateActivity,"Please Enter Received OTP!",Toast.LENGTH_SHORT).show()
                }
            }
            else if(value.equals(CD_Global_enums.XX_ERROR.value.toInt()))
            {
                Toast.makeText(this@AccountCreateActivity,"Two Entered Pins Mismatch!",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this@AccountCreateActivity,"Please Enter Your New Pin",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun checkAndMatchUserPin():Int
    {
        if(str_newPin != null)
            str_newPin = null
        if(str_reEnteredPin != null)
            str_reEnteredPin = null

        str_newPin = et_newPinDigit1.text?.toString()
        str_newPin += et_newPinDigit2.text
        str_newPin += et_newPinDigit3.text
        str_newPin += et_newPinDigit4.text

        str_reEnteredPin = et_reEnterPinDigit1.text?.toString()
        str_reEnteredPin += et_reEnterPinDigit2.text
        str_reEnteredPin += et_reEnterPinDigit3.text
        str_reEnteredPin += et_reEnterPinDigit4.text

        if(str_newPin.isNullOrEmpty() || str_reEnteredPin.isNullOrEmpty())
            return CD_Global_enums.XX_NOTFOUND.value.toInt()
        else if(str_newPin.equals(str_reEnteredPin))
            return CD_Global_enums.XX_OK.value.toInt()
        else
            return CD_Global_enums.XX_ERROR.value.toInt()
    }

    fun verifyOtpEntered():Int
    {
        if(str_otpVerify != null)
            str_otpVerify = null

        str_otpVerify = et_otpDigit1.text?.toString()
        str_otpVerify += et_otpDigit2.text
        str_otpVerify += et_otpDigit3.text
        str_otpVerify += et_otpDigit4.text

        if(str_otpVerify.isNullOrEmpty())
            return CD_Global_enums.XX_NOTFOUND.value.toInt()
        else
            return CD_Global_enums.XX_OK .value.toInt()
    }
}