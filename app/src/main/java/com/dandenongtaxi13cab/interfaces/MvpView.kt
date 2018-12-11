package com.dandenongtaxi13cab.interfaces

import android.content.Context
import android.support.annotation.StringRes

/**
 * @author by Mohit Arora on 3/8/18.
 */
interface MvpView {
    fun showLoading()

    fun hideLoading()

    fun onError(@StringRes resId: Int)

    fun onError(message: String)

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

    fun hideKeyboard()

    fun isNetworkConnected(ctx: Context): Boolean

    fun isPermissionsGranted(ctx: Context, permission: String): Boolean

    fun requestPermission(ctx: Context, permission: Array<String>, requestPermissionCode: Int)
}