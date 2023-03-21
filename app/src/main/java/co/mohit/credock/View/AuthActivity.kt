package co.mohit.credock.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import co.mohit.credock.CD_Global_enums
import co.mohit.credock.R
import kotlinx.android.synthetic.main.activity_auth.*
import java.time.Duration

class AuthActivity : AppCompatActivity() {

    private var pinDigit : String? = null
    private var submitBtn: Button? = null
    private var accountCreateTextBtn : TextView? = null;
    
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

        et_pinDigit1.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_pinDigit1.text.isNullOrEmpty())
                    et_pinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_pinDigit2.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_pinDigit2.text.isNullOrEmpty())
                    et_pinDigit3.requestFocus()
                else
                    et_pinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_pinDigit3.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!et_pinDigit3.text.isNullOrEmpty())
                    et_pinDigit4.requestFocus()
                else
                    et_pinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        et_pinDigit4.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_pinDigit4.text.isNullOrEmpty())
                    et_pinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

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
        if(!pinDigit.isNullOrEmpty() && pinDigit?.length == CD_Global_enums.MAX_PIN_LENGTH.value.toInt())
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