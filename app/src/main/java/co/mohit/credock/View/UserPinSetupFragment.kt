package co.mohit.credock.View

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.mohit.credock.CD_Global_enums
import co.mohit.credock.Controller.EmailVerificationService
import co.mohit.credock.databinding.FragmentUserPinSetupBinding
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserPinSetupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var userPinSetupCallback:IUserDetailFragmentToActivity
    private lateinit var userPinDetailBinder:FragmentUserPinSetupBinding
    private var userPinSetupContentValues: ContentValues?= null
    private var str_newPin:String? = null
    private var str_reEnteredPin:String? = null
    private var str_otpVerify:String? = null
    private lateinit var emailService:EmailVerificationService

   override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //Implementing concept of Dynamic Polymorphism
            userPinSetupCallback = context as IUserDetailFragmentToActivity
        }
        catch (ex:Exception)
        {
            throw java.lang.ClassCastException("${context.toString()} must implements IUserDetailFragmentToActivity")
        }
    }
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPinDetailBinder = FragmentUserPinSetupBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return userPinDetailBinder.root
    }

    override fun onResume() {
        super.onResume()
        et_newPinDigit1.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit1.text.isNullOrEmpty())
                    et_newPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit2.addTextChangedListener(object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit2.text.isNullOrEmpty())
                    et_newPinDigit3.requestFocus()
                else
                    et_newPinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit3.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_newPinDigit3.text.isNullOrEmpty())
                    et_newPinDigit4.requestFocus()
                else
                    et_newPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_newPinDigit4.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_newPinDigit4.text.isNullOrEmpty())
                    et_newPinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_reEnterPinDigit1.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit1.text.isNullOrEmpty())
                    et_reEnterPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit2.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit2.text.isNullOrEmpty())
                    et_reEnterPinDigit3.requestFocus()
                else
                    et_reEnterPinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit3.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_reEnterPinDigit3.text.isNullOrEmpty())
                    et_reEnterPinDigit4.requestFocus()
                else
                    et_reEnterPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_reEnterPinDigit4.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_reEnterPinDigit4.text.isNullOrEmpty())
                    et_reEnterPinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_otpDigit1.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit1.text.isNullOrEmpty())
                    et_otpDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit2.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit2.text.isNullOrEmpty())
                    et_otpDigit3.requestFocus()
                else
                    et_otpDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit3.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_otpDigit3.text.isNullOrEmpty())
                     et_otpDigit4.requestFocus()
                else
                    et_otpDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        et_otpDigit4.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_otpDigit4.text.isNullOrEmpty())
                    et_otpDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })


    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserPinSetupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun validateUserPinAndOtp() {
        var value = checkAndMatchUserPin()
        if (value == CD_Global_enums.XX_OK.value.toInt())
        {
            value = verifyOtpEntered()
            if (value == CD_Global_enums.XX_OK.value.toInt())
            {
                userPinSetupContentValues = ContentValues()
                userPinSetupContentValues?.put("userPin",str_newPin)
                userPinSetupContentValues?.put("otpReceived",str_otpVerify)
            }
            else if (value == CD_Global_enums.XX_ERROR.value.toInt())
            {
                Toast.makeText(
                    this.context,
                    "Invalid OTP entered",
                    Toast.LENGTH_SHORT
                ).show()
                userPinSetupContentValues = null
            }
            else
            {
                Toast.makeText(
                    this.context,
                    "Please Enter Received OTP!",
                    Toast.LENGTH_SHORT
                ).show()
                userPinSetupContentValues = null
            }
        }
        else if (value == CD_Global_enums.XX_ERROR.value.toInt())
        {
            Toast.makeText(
                this.context,
                "Two Entered Pins Mismatch!",
                Toast.LENGTH_SHORT
            ).show()
            userPinSetupContentValues = null
        }
        else {
            Toast.makeText(
                this.context,
                "Please Enter Your New Pin",
                Toast.LENGTH_SHORT
            ).show()
            userPinSetupContentValues = null
        }
        userPinSetupCallback.getUserPinSetupEvents(userPinSetupContentValues)
    }

    fun checkAndMatchUserPin(): Int {
        if (str_newPin != null)
            str_newPin = null
        if (str_reEnteredPin != null)
            str_reEnteredPin = null

        str_newPin = userPinDetailBinder.etNewPinDigit1.text?.toString()
        str_newPin += userPinDetailBinder.etNewPinDigit2.text
        str_newPin += userPinDetailBinder.etNewPinDigit3.text
        str_newPin += userPinDetailBinder.etNewPinDigit4.text

        str_reEnteredPin = userPinDetailBinder.etReEnterPinDigit1.text?.toString()
        str_reEnteredPin += userPinDetailBinder.etReEnterPinDigit2.text
        str_reEnteredPin += userPinDetailBinder.etReEnterPinDigit3.text
        str_reEnteredPin += userPinDetailBinder.etReEnterPinDigit4.text

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

        str_otpVerify = userPinDetailBinder.etOtpDigit1.text?.toString()
        str_otpVerify += userPinDetailBinder.etOtpDigit2.text
        str_otpVerify += userPinDetailBinder.etOtpDigit3.text
        str_otpVerify += userPinDetailBinder.etOtpDigit4.text

        if (!str_otpVerify.isNullOrEmpty())
            return emailService.verifyInputOtp(str_otpVerify!!.toInt())
        else
            return CD_Global_enums.XX_NOTFOUND.value.toInt()
    }

    fun initializeParamsForEmailService(emailService:EmailVerificationService)
    {
        this.emailService = emailService
    }
}