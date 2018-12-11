package com.dandenongtaxi13cab.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * @author by Mohit Arora on 3/8/18.
 */
class MarshmallowPermissions {

    fun checkPermission(ctx: Context, permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(ctx, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(ctx: Context, permission: Array<String>, requestPermissionCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ctx as Activity, permission[0])) {
            ActivityCompat.requestPermissions(ctx, permission, requestPermissionCode)
        } else {
            ActivityCompat.requestPermissions(ctx, permission, requestPermissionCode)
        }
    }
}