package co.mohit.credock.View

//import uk.co.jakebreen.sendgridandroid.SendGrid
//import uk.co.jakebreen.sendgridandroid.SendGrid.create
//import uk.co.jakebreen.sendgridandroid.SendGridMail
//import uk.co.jakebreen.sendgridandroid.SendGridResponse
//import uk.co.jakebreen.sendgridandroid.SendTask

import android.os.AsyncTask
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
import com.sendgrid.*
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_user_pin_setup.*
import java.io.IOException
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

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
            /*if(value == CD_Global_enums.XX_ERROR.value.toInt())
            {
                Toast.makeText(this@AccountCreateActivity,"Got into some error",Toast.LENGTH_SHORT).show()
            }*/
          /*  var myTask = MyAsyncTask()
            myTask.execute(GetSendGridApiKey())*/
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

       /* tv_resendOtpText.setOnClickListener(View.OnClickListener {
            emailVerifyService.sendOTPForEmailVerification()
        })*/
    }

/*    private fun sendVerificationEmail() {
        val from = com.sendgrid.Email("mohitkc1234@rediffmail.com")
        val subject = "Sending with SendGrid is Fun"
        val to = com.sendgrid.Email("kvnl.mohitchanchlani@gmail.com")
        val content = Content("text/plain", "and easy to do anywhere, even with Java")
        val mail = Mail(from, subject, to, content)

        val sg = SendGrid(GetSendGridApiKey().toString())
        val request = Request()
        try {
            request.setMethod(Method.POST)
            request.setEndpoint("/mail/send")
            request.setBody(mail.build())
            val response: Response = sg.api(request)
            Toast.makeText(this@AccountCreateActivity,response.statusCode.toString(),Toast.LENGTH_LONG).show()
        } catch (ex: IOException) {
            Toast.makeText(this@AccountCreateActivity, ex.message.toString(),Toast.LENGTH_LONG).show()
        }
    }*/

    //fun sendVerificationEmail() {
//        val userName:String = "apikey"
//        val password:String = GetSendGridApiKey()
//
//        val props = Properties()
//        props["mail.smtp.host"] = "smtp.sendgrid.net"
//        props["mail.smtp.socketFactory.port"] = "465"
//        props["mail.smtp.socketFactory.class"]= "javax.net.ssl.SSLSocketFactory"
//        props["mail.smtp.auth"] = "true"
//        props["mail.smtp.port"] = "465"
//
//        val session = javax.mail.Session.getInstance(props, object : javax.mail.Authenticator() {
//            override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
//                return javax.mail.PasswordAuthentication(userName, password)
//            }
//        })
//
//        try {
//            var message = com.sun.mail.smtp.SMTPMessage(session)
//            message.setFrom(InternetAddress("mohitkc1234@rediffmail.com"))
//            message.setRecipient(Message.RecipientType.TO,InternetAddress("kvnl.mohitchanchlani@gmail.com"))
//            message.subject = "Email Verification OTP"
//            message.setText("Hello, This mail is for Email verification. Your OTP is ${(1000..9999).shuffled().first().toString()}")
//            com.sun.mail.smtp.SMTPTransport.send(message)
//        }
//        catch (ex:Exception)
//        {
//            Log.i("Mail Exception",ex.message.toString())
//        }
    //}

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
/*    class MyAsyncTask:AsyncTask<String,Unit,String>()
    {

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?): String {
            val userName:String = "apikey"
            val password:String? = params.get(0)

            val props = Properties()
            props["mail.smtp.host"] = "smtp.sendgrid.net"//"smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "25"//"587"
            //props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.socketFactory.class"]= "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
//            props["spring.mail.smtp.ssl.enable"] = "true"
            props["mail.smtp.port"] = "25"//"587"

            val session = javax.mail.Session.getInstance(props, object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                    return javax.mail.PasswordAuthentication(userName, password)
                }
            })

            try {
                var message = com.sun.mail.smtp.SMTPMessage(session)
                message.setFrom(InternetAddress("mohitkc1234@rediffmail.com"))
                message.setRecipient(Message.RecipientType.TO,InternetAddress("kvnl.mohitchanchlani@gmail.com"))
                message.subject = "Email Verification OTP"
                message.setText("Hello, This mail is for Email verification. Your OTP is ${(1000..9999).shuffled().first().toString()}")
                com.sun.mail.smtp.SMTPTransport.send(message)
            }
            catch (ex:Exception)
            {
                Log.i("Mail Exception",ex.message.toString())
            }
            return "Hello"
        }
    }*/

