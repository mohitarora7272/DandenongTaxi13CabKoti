package com.dandenongtaxi13cab.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @author by Mohit Arora on 3/8/18.
 */
class NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = null
        if (true) {
            activeNetwork = cm.activeNetworkInfo
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}