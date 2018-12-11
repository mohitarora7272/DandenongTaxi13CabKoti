package com.dandenongtaxi13cab.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.utils.AppConstants
import kotlinx.android.synthetic.main.activity_splash.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * @author by Mohit Arora on 2/8/18.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startAnim()
        decideNextActivity()
    }

    private fun decideNextActivity() {
        Handler().postDelayed({
            stopAnim()
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, AppConstants().splashTimeOut.toLong())
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun startAnim() {
        avi!!.smoothToShow()
    }

    private fun stopAnim() {
        avi!!.smoothToHide()
    }

    override fun setUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}