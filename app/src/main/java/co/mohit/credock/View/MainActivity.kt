package co.mohit.credock.View

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import co.mohit.credock.R
import co.mohit.credock.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val userProfileFrag:Fragment = UserProfileFragment()
    private val individualCredentialFrag = IndividualCredentialFragment()
    private lateinit var mainActivityBinder:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinder.root)
    }

    fun initializeFragment(fragment: Fragment)
    {
        var fragTrans = supportFragmentManager.beginTransaction()
        fragTrans.replace(R.id.fragContainerView_userProfile,fragment)
        fragTrans.commit()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        mainActivityBinder.bottomNavBarMainActivity.setOnItemSelectedListener{
            when(it.itemId)
            {
                R.id.menuItem_newCredential -> {
                    mainActivityBinder.bottomNavBarMainActivity.visibility = View.GONE
                    supportActionBar?.hide()
                    initializeFragment(individualCredentialFrag)
                    true
                }

                R.id.menuItem_profile -> {
                    mainActivityBinder.bottomNavBarMainActivity.visibility = View.GONE
                    supportActionBar?.hide()
                    initializeFragment(userProfileFrag)
                    true
                }
                else -> false
            }
        }


    }
}