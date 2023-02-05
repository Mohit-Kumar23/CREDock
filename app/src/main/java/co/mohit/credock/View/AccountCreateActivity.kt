package co.mohit.credock.View

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.mohit.credock.R
import kotlinx.android.synthetic.main.activity_account_create.*
import kotlinx.android.synthetic.main.activity_auth.*

class AccountCreateActivity : AppCompatActivity() {

    val userProfileFrag = UserProfileDetails()
    val userPinFrag = UserPinSetup()
    val fragmentMgr = supportFragmentManager
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
    }
}