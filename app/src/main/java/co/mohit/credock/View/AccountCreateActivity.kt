package co.mohit.credock.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.mohit.credock.R

class AccountCreateActivity : AppCompatActivity() {

    val userProfileFragment = findViewById<View>(R.id.fragment_userProfileDetails)
    val userPinFragment = findViewById<View>(R.id.fragment_userPinDetails)
    val fragmentMgr = getSupportFragmentManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_create)

    }

    override fun onResume() {
        super.onResume()

    }
}