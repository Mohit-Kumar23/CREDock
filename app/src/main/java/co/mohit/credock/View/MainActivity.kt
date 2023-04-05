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
    private val userCredentialFrag = UserCredentialsFragment()
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

    override fun onStart() {
        super.onStart()
        initializeFragment(userCredentialFrag)
        mainActivityBinder.fabExpandNavigationBar.visibility = View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        mainActivityBinder.bottomNavBarMainActivity.setOnItemSelectedListener{it->
            when(it.itemId)
            {
                R.id.menuItem_newCredential -> {
                    if(!this.supportFragmentManager.fragments.equals(individualCredentialFrag)) {
                        mainActivityBinder.bottomNavBarMainActivity.visibility = View.GONE
                        supportActionBar?.hide()
                        mainActivityBinder.fabExpandNavigationBar.visibility = View.VISIBLE
                        initializeFragment(individualCredentialFrag)
                    }
                    else
                    {
                        supportActionBar?.hide()
                        mainActivityBinder.fabExpandNavigationBar.visibility = View.VISIBLE
                    }
                    true
                }

                R.id.menuItem_profile -> {
                    if(!this.supportFragmentManager.fragments.equals(userProfileFrag)) {
                        mainActivityBinder.bottomNavBarMainActivity.visibility = View.GONE
                        supportActionBar?.hide()
                        mainActivityBinder.fabExpandNavigationBar.visibility = View.VISIBLE
                        initializeFragment(userProfileFrag)
                    }
                    else
                    {
                        supportActionBar?.hide()
                        mainActivityBinder.fabExpandNavigationBar.visibility = View.VISIBLE
                    }
                    true
                }

                R.id.menuItem_credential -> {
                    if(!this.supportFragmentManager.fragments.equals(UserCredentialsFragment))
                    {
                        mainActivityBinder.bottomNavBarMainActivity.visibility = View.VISIBLE
                        supportActionBar?.show()
                        mainActivityBinder.fabExpandNavigationBar.visibility = View.GONE
                        initializeFragment(userCredentialFrag)
                    }
                    true
                }
                else -> false
            }
        }

        mainActivityBinder.fabExpandNavigationBar.setOnClickListener(View.OnClickListener {
            mainActivityBinder.bottomNavBarMainActivity.visibility =View.VISIBLE
            mainActivityBinder.fabExpandNavigationBar.visibility = View.GONE
        })


    }
}