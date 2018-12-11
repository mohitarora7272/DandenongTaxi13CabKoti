package com.dandenongtaxi13cab.ui.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.dandenongtaxi13cab.interfaces.MvpView
import com.dandenongtaxi13cab.utils.CommonUtils
import com.dandenongtaxi13cab.utils.KeyboardUtils
import com.dandenongtaxi13cab.utils.MarshmallowPermissions
import com.dandenongtaxi13cab.utils.NetworkUtils

/**
 * @author by Mohit Arora on 2/8/18.
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), MvpView {

    private var mProgressDialog: ProgressDialog? = null

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils().showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onError(resId: Int) {
        showMessage(getString(resId))
    }

    override fun onError(message: String) {
        showSnackBar(message)
    }

    override fun showMessage(message: String) {
        showSnackBar(message)
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun hideKeyboard() {
        KeyboardUtils().hideSoftInput(this)
    }

    override fun isNetworkConnected(ctx: Context): Boolean {
        return NetworkUtils().isNetworkConnected(ctx)
    }

    override fun isPermissionsGranted(ctx: Context, permission: String): Boolean {
        return MarshmallowPermissions().checkPermission(ctx, permission)
    }

    override fun requestPermission(ctx: Context, permission: Array<String>, requestPermissionCode: Int) {
        MarshmallowPermissions().requestPermissions(ctx, permission, requestPermissionCode)
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val tv = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        tv.setBackgroundColor(Color.BLACK)
        snackbar.show()
    }

    protected abstract fun setUp()

    override fun onDestroy() {
        super.onDestroy()
    }
}