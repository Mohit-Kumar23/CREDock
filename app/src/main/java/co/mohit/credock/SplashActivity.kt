package co.mohit.credock

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import co.mohit.credock.Controller.DatabaseService
import co.mohit.credock.View.AuthActivity

class SplashActivity : AppCompatActivity() {

    private var sharedPreferences:SharedPreferences? = null
    private lateinit var dbService:DatabaseService;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try {
           sharedPreferences = this@SplashActivity.getSharedPreferences("LaunchEvent", MODE_PRIVATE)
        }
        catch (ex:Exception)
        {
            Log.e("Shared Pref",ex.message.toString())
        }

        moveToAuthActivity()
    }

    fun moveToAuthActivity()
    {
        try {
            if(!(sharedPreferences?.contains("DBCreated"))!!)
            {
                callToCreateDatabaseAndTables()
            }
            else if(1 != sharedPreferences?.getInt("DBCreated",-1))
            {
                Log.i("shared Value",sharedPreferences?.getInt("DBCreated",-1).toString())
                callToCreateDatabaseAndTables()
            }
            else
            {
                dbService = DatabaseService()
            }
        }
        catch(ex:Exception)
        {
            Log.e("DB Exception",ex.message.toString())
            if(dbService != null)
            {
                dbService.closeConnectionFromDB()
            }
        }
        var mHandleDelay = Handler(Looper.getMainLooper())
        mHandleDelay.postDelayed({
            var intent: Intent = Intent(this@SplashActivity, AuthActivity::class.java);
            //intent.putExtra("DBHelperInstance",dbService)
            startActivity(intent);
        },2000);
    }

    fun callToCreateDatabaseAndTables()
    {
        dbService = DatabaseService()
        dbService.createDBService(this)
        var sharedPrefEditor = sharedPreferences?.edit()
        if(dbService.isDBCreated)
        {
            sharedPrefEditor?.putInt("DBCreated",1)
        }
        else
        {
            sharedPrefEditor?.putInt("DBCreated",0)
        }
        sharedPrefEditor?.commit()
    }
}
