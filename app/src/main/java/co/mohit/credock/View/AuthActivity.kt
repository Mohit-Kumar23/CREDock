package co.mohit.credock.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import co.mohit.credock.R
import kotlinx.android.synthetic.main.activity_auth.*
import java.time.Duration

class AuthActivity : AppCompatActivity() {

    private var pinDigit : String? = null
    private var submitBtn: Button? = null
    private var accountCreateTextBtn : TextView? = null;
    private val MAX_PIN_LENGTH: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        submitBtn = findViewById(R.id.btn_pinSubmit)
        accountCreateTextBtn = findViewById(R.id.tv_create_accnt_desc)
    }


    override fun onResume() {
        super.onResume()
        submitBtn?.setOnClickListener(View.OnClickListener {
            readPinFromView()
        })

        tv_create_accnt_desc.setOnClickListener(View.OnClickListener {
            moveToAccountCreateActivity()
        })

       /* accountCreateTextBtn?.setOnClickListener(View.OnClickListener {
            var intent:Intent = Intent(this@AuthActivity,null); //Activity name will come
            startActivity(intent)
        })*/
    }

    private fun moveToAccountCreateActivity() {
        var intent:Intent = Intent(this@AuthActivity,AccountCreateActivity::class.java)
        startActivity(intent)
    }

    private fun readPinFromView()
    {
        if(!(et_pinDigit1.text.isNullOrEmpty()))
        {
            pinDigit = et_pinDigit1.text.toString()
        }
        if(!(et_pinDigit2.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += et_pinDigit2.text.toString()
        }
        if(!(et_pinDigit3.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += et_pinDigit3.text.toString()
        }
        if(!(et_pinDigit4.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += et_pinDigit4.text.toString()
        }
        else
        {
            Toast.makeText(this,"Please Enter your Pin",Toast.LENGTH_SHORT).show()
            pinDigit = null;
        }
        if(!pinDigit.isNullOrEmpty() && pinDigit?.length == MAX_PIN_LENGTH)
        {
            //Method for calling next activity and mean while verifying the PIN from DB and fetching records.
            //Start Animation
            //Fetch the record from DB
            //End Animation
        }
        if(!pinDigit.isNullOrEmpty())
        {
            pinDigit = null;
        }
    }

}