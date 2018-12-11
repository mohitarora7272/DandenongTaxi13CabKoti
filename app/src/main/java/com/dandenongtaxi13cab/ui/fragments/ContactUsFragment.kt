package com.dandenongtaxi13cab.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.interfaces.OnBookNowClick
import com.dandenongtaxi13cab.utils.CommonUtils
import com.dandenongtaxi13cab.utils.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_contact_us.*

/**
 * @author by Mohit Arora on 3/8/18.
 */
class ContactUsFragment : BaseFragment(), View.OnClickListener {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var bookNowClick: OnBookNowClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun setUp(view: View) {
        view.setOnClickListener { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_Send.setOnClickListener(this@ContactUsFragment)
        KeyboardUtils().showSoftInput(edt_Name, context!!)
        setTextChangeListener()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is OnBookNowClick) {
            bookNowClick = activity as OnBookNowClick
        } else {
            throw RuntimeException(activity.toString() + " must implement OnBookNowClick in MainActivity")
        }
    }

    private fun setTextChangeListener() {
        edt_ContactNo.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 10) {
                    edt_Email.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    override fun onClick(p0: View?) {
        when (view!!.id) {
            R.id.btn_Send -> if (isNetworkConnected(context!!)) {
                if (edt_Name.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_name))
                } else if (edt_ContactNo.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_contact_no))
                } else if (edt_ContactNo.text.toString().length < 10) {
                    showMessage(resources.getString(R.string.enter_correct_contact_no))
                } else if (edt_Email.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_email))
                } else if (!CommonUtils().isEmailValid(edt_Email.text.toString())) {
                    showMessage(resources.getString(R.string.enter_valid_email))
                } else if (edt_YourMsg.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_message))
                } else {
                    sendEmail(edt_Name.text.toString(), edt_ContactNo.text.toString(), edt_Email.text.toString(),
                            edt_YourMsg.text.toString())
                }
            } else {
                showMessage(resources.getString(R.string.no_internet))
            }
            else -> {
            }
        }
    }

    private fun sendEmail(name: String, contact_no: String, email: String, message: String) {
        val uri = Uri.parse("mailto:" + resources.getString(R.string.email))
                .buildUpon()
                .appendQueryParameter("to", resources.getString(R.string.email))
                .appendQueryParameter("subject", resources.getString(R.string.contact_request) + " "
                        + resources.getString(R.string.app_name))
                .appendQueryParameter("body", "Name: " + name + "\n" + "ContactNo: " + contact_no + "\n"
                        + "Email: " + email + "\n" + "Message: " + message)
                .build()

        val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
        startActivityForResult(Intent.createChooser(emailIntent, "Contact Us..."), 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            resetAll()
        }
    }

    private fun resetAll() {
        edt_Name.text = null
        edt_ContactNo.text = null
        edt_Email.text = null
        edt_YourMsg.text = null
        edt_Name.requestFocus()
    }
}