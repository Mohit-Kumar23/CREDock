package co.mohit.credock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import co.mohit.credock.Controller.DatabaseService
import co.mohit.credock.View.AuthActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        moveToAuthActivity()
    }

    fun moveToAuthActivity()
    {
        try {
            DatabaseService().createDBService(this)
        }
        catch(ex:Exception)
        {
            Log.e("DB Exception",ex.message.toString())
        }
        var mHandleDelay = Handler(Looper.getMainLooper())
        mHandleDelay.postDelayed({
            var intent: Intent = Intent(this@SplashActivity, AuthActivity::class.java);
            startActivity(intent);
        },2000);
    }
}
