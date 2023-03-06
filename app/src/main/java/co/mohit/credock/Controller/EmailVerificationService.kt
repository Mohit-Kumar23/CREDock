package co.mohit.credock.Controller

import android.os.IBinder.DeathRecipient
import android.util.Log
import android.widget.Toast
import co.mohit.credock.CD_Global_enums
import java.util.*
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import kotlin.properties.Delegates

class EmailVerificationService{

    private val userName:String = "apikey"
    private lateinit var apiKeyAsPassword:String
    private var props: Properties? = null
    private var senderEmail = "mohitkc1234@rediffmail.com"
    private lateinit var recipientEmail:String;
    private lateinit var recipientName: String;
    private var generatedOTP:Int = 0;

    constructor(pwd:String,recipientEmail: String,recipientName: String)
    {
        apiKeyAsPassword = pwd;
        this.recipientEmail = recipientEmail;
        this.recipientName = recipientName;
    }

    fun sendOTPForEmailVerification():Int
    {
        var retVal = CD_Global_enums.XX_ERROR.value.toInt()
        if(props == null)
        {
            preparePropertiesForSMTP()
        }

        val session = javax.mail.Session.getInstance(props,object:javax.mail.Authenticator()
        {
            override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                return javax.mail.PasswordAuthentication(userName,apiKeyAsPassword)
            }
        })

        Thread(Runnable {

            generatedOTP = (1000..9999).shuffled().first().toInt()

            try {
                val message = com.sun.mail.smtp.SMTPMessage(session)
                message.setFrom(InternetAddress(senderEmail))
                message.setRecipient(Message.RecipientType.TO,InternetAddress(recipientEmail))
                message.subject = "CREDock Team Support: Email Verification OTP"
                message.setText("Hello ${recipientName},\n  Thanks for starting your safe and secure journey with us.\nYour OTP for email verification is ${generatedOTP.toString()}")
                com.sun.mail.smtp.SMTPTransport.send(message)

                retVal = CD_Global_enums.XX_OK.value.toInt()
            }
            catch (ex:Exception)
            {
                Log.i("apiException",ex.message.toString())
            }
        }).start()

        return retVal
    }

    private fun preparePropertiesForSMTP()
    {
        props = Properties()
        props!!["mail.smtp.host"] = "smtp.sendgrid.net"
        props!!["mail.smtp.socketFactory.port"] = "465"
        props!!["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props!!["mail.smtp.auth"] = "true"
        props!!["mail.smtp.port"] = "465"
    }

    fun verifyInputOtp(inputOTP:Int):Int
    {
        if(generatedOTP == inputOTP)
            return CD_Global_enums.XX_OK.value.toInt()
        else
            return CD_Global_enums.XX_ERROR.value.toInt()
    }


}