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
import co.mohit.credock.Controller.DatabaseService
import co.mohit.credock.R
import co.mohit.credock.databinding.ActivityAuthBinding
import kotlinx.android.synthetic.main.activity_auth.*
import java.time.Duration

class AuthActivity : AppCompatActivity() {

    private var pinDigit : String? = null
    private var submitBtn: Button? = null
    private lateinit var authActivityBinding:ActivityAuthBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authActivityBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(authActivityBinding.root)

      }


    override fun onResume() {
        super.onResume()
        authActivityBinding.btnPinSubmit.setOnClickListener(View.OnClickListener {
            readPinFromView()
            if(!pinDigit.isNullOrEmpty()) {
                authenticateUser()
            }
        })

        authActivityBinding.tvCreateAccntDesc.setOnClickListener(View.OnClickListener {
            moveToAccountCreateActivity()
        })

        authActivityBinding.etPinDigit1.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!authActivityBinding.etPinDigit1.text.isNullOrEmpty())
                    authActivityBinding.etPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        authActivityBinding.etPinDigit2.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!authActivityBinding.etPinDigit2.text.isNullOrEmpty())
                    authActivityBinding.etPinDigit3.requestFocus()
                else
                    authActivityBinding.etPinDigit1.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        authActivityBinding.etPinDigit3.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!authActivityBinding.etPinDigit3.text.isNullOrEmpty())
                    authActivityBinding.etPinDigit4.requestFocus()
                else
                    authActivityBinding.etPinDigit2.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        authActivityBinding.etPinDigit4.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(authActivityBinding.etPinDigit4.text.isNullOrEmpty())
                    authActivityBinding.etPinDigit3.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun authenticateUser() {
        var retVal = DatabaseService.fetchUserIDFromDB(pinDigit!!.toInt())
        if(retVal == 1)
        {
            moveToMainActivity()
        }
        else
        {
            Toast.makeText(this,"Invalid Pin",Toast.LENGTH_LONG).show()
        }
    }

    private fun moveToMainActivity()
    {
        var intent = Intent(this@AuthActivity,MainActivity::class.java)
        startActivity(intent)
    }
    private fun moveToAccountCreateActivity() {
        var intent:Intent = Intent(this@AuthActivity,AccountCreateActivity::class.java)
        startActivity(intent)
    }

    private fun readPinFromView()
    {
        if(!pinDigit.isNullOrEmpty()) {
            pinDigit = null
        }

        if(!(authActivityBinding.etPinDigit1.text.isNullOrEmpty()))
        {
            pinDigit = authActivityBinding.etPinDigit1.text.toString()
        }
        if(!(authActivityBinding.etPinDigit2.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += authActivityBinding.etPinDigit2.text.toString()
        }
        if(!(authActivityBinding.etPinDigit3.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += authActivityBinding.etPinDigit3.text.toString()
        }
        if(!(authActivityBinding.etPinDigit4.text.isNullOrEmpty()) and !pinDigit.isNullOrEmpty())
        {
            pinDigit += authActivityBinding.etPinDigit4.text.toString()
        }
        if(!pinDigit.isNullOrEmpty() && pinDigit?.length != CD_Global_enums.MAX_PIN_LENGTH.value.toInt())
        {
            Toast.makeText(this,"Please Enter your Pin",Toast.LENGTH_SHORT).show()
            pinDigit = null;
        }
    }

}