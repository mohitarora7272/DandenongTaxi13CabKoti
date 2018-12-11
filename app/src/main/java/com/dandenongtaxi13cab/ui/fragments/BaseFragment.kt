package com.dandenongtaxi13cab.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.dandenongtaxi13cab.interfaces.MvpView
import com.dandenongtaxi13cab.ui.activities.BaseActivity
import com.dandenongtaxi13cab.utils.CommonUtils
import com.dandenongtaxi13cab.utils.MarshmallowPermissions

/**
 * @author by Mohit Arora on 3/8/18.
 */
abstract class BaseFragment : Fragment(), MvpView {

    private var mActivity: BaseActivity? = null
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
        }
    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils().showLoadingDialog(this.context!!)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onError(resId: Int) {
        if (mActivity != null) {
            mActivity!!.onError(resId)
        }
    }

    override fun onError(message: String) {
        if (mActivity != null) {
            mActivity!!.onError(message)
        }
    }

    override fun showMessage(message: String) {
        if (mActivity != null) {
            mActivity!!.showMessage(message)
        }
    }

    override fun showMessage(resId: Int) {
        if (mActivity != null) {
            mActivity!!.showMessage(resId)
        }
    }

    override fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    override fun isNetworkConnected(ctx: Context): Boolean {
        return mActivity != null && mActivity!!.isNetworkConnected(ctx)
    }

    override fun isPermissionsGranted(ctx: Context, permission: String): Boolean {
        return MarshmallowPermissions().checkPermission(mActivity!!, permission)
    }

    override fun requestPermission(ctx: Context, permission: Array<String>, requestPermissionCode: Int) {
        MarshmallowPermissions().requestPermissions(mActivity!!, permission, requestPermissionCode)
    }

    fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    protected abstract fun setUp(view: View)

    override fun onDestroy() {
        super.onDestroy()
    }
}