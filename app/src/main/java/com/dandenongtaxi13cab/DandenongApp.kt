package com.dandenongtaxi13cab

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * @author by Mohit Arora on 2/8/18.
 */
class DandenongApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Oxygen_Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}