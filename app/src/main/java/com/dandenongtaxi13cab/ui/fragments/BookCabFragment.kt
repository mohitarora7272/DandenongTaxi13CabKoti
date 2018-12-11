package com.dandenongtaxi13cab.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.interfaces.OnBookNowClick
import com.dandenongtaxi13cab.utils.AppConstants
import com.dandenongtaxi13cab.utils.CommonUtils
import com.dandenongtaxi13cab.utils.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_book_cab.*
import java.util.*

/**
 * @author by Mohit Arora on 3/8/18.
 */
class BookCabFragment : BaseFragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mTaxiListArray = arrayOf("Sedan", "Silver Service", "Wagon", "Maxi")

    private var mNowLaterListArray = arrayOf("Now", "Later")

    private var bookNowClick: OnBookNowClick? = null
    private var suburbsList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suburbsList = CommonUtils().getSuburbsList(context!!, AppConstants().suburbsFileName)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun setUp(view: View) {
        view.setOnClickListener { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_cab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KeyboardUtils().showSoftInput(edt_Name, context!!)
        spnr_now_later.onItemSelectedListener = this
        tv_Date.setOnClickListener(this)
        tv_Time.setOnClickListener(this)
        btn_Send.setOnClickListener(this)
        auto_tv_pick.threshold = 1
        auto_tv_going_to.threshold = 1
        setAdapterTaxiSpnr()
        setAdapterNowLaterSpnr()
        setPickAdapter()
        setGoingToAdapter()
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
        auto_tv_pick.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    linearUnit.visibility = View.VISIBLE
                } else {
                    linearUnit.visibility = View.GONE
                    edt_Unit.setText("")
                    edt_HouseNo.setText("")
                    edt_Street.setText("")
                }
            }
        })

        edt_Unit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 4) {
                    edt_HouseNo.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        edt_HouseNo.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 4) {
                    edt_Street.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        edt_ContactNo.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 10) {
                    auto_tv_pick.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {

            }
        })

        auto_tv_pick.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> edt_Unit.requestFocus() }
        auto_tv_going_to.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> edt_Remarks.requestFocus() }
    }

    private fun setPickAdapter() {
        //Creating the instance of ArrayAdapter containing list of language names
        val adapterPickUp = ArrayAdapter(context!!, android.R.layout.select_dialog_item, suburbsList)
        auto_tv_pick.setAdapter(adapterPickUp)
    }

    private fun setGoingToAdapter() {
        //Creating the instance of ArrayAdapter containing list of language names
        val adapterGoingTo = ArrayAdapter(context!!, android.R.layout.select_dialog_item, suburbsList)
        auto_tv_going_to.setAdapter(adapterGoingTo)
    }

    private fun setAdapterTaxiSpnr() {
        //Creating the ArrayAdapter instance having the country list
        val adapterTaxi = ArrayAdapter(context!!, R.layout.spinner_item, mTaxiListArray)
        adapterTaxi.setDropDownViewResource(android.R.layout.select_dialog_item)
        //Setting the ArrayAdapter data on the Spinner
        spnr_taxi.adapter = adapterTaxi
    }

    private fun setAdapterNowLaterSpnr() {
        //Creating the ArrayAdapter instance having the country list
        val adapterNowLater = ArrayAdapter(context!!, R.layout.spinner_item, mNowLaterListArray)
        adapterNowLater.setDropDownViewResource(android.R.layout.select_dialog_item)
        //Setting the ArrayAdapter data on the Spinner
        spnr_now_later.adapter = adapterNowLater
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
        val item = adapterView!!.getItemAtPosition(i).toString()
        if (item == getString(R.string.now_text)) {
            linearDateTime.visibility = View.GONE
            tv_Date.text = ""
            tv_Time.text = ""
        } else {
            linearDateTime.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_Date -> {
                hideKeyboard()
                // Get Current Date
                val c = Calendar.getInstance()
                val mYear = c.get(Calendar.YEAR)
                val mMonth = c.get(Calendar.MONTH)
                val mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(context!!,
                        DatePickerDialog.OnDateSetListener { views, year, monthOfYear, dayOfMonth -> tv_Date.text = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year }, mYear, mMonth, mDay)

                datePickerDialog.show()
                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            }
            R.id.tv_Time -> {
                hideKeyboard()
                // Get Current Time
                val c2 = Calendar.getInstance()
                val mHour = c2.get(Calendar.HOUR_OF_DAY)
                val mMinute = c2.get(Calendar.MINUTE)

                // Launch Time Picker Dialog
                val timePickerDialog = TimePickerDialog(context,
                        TimePickerDialog.OnTimeSetListener { views, hourOfDay, minute ->
                            var hourOfDays = hourOfDay
                            val time: String
                            if (hourOfDay in 0..11) {
                                time = hourOfDay.toString() + ":" + minute + " AM"
                            } else {
                                if (hourOfDay == 12) {
                                    time = hourOfDay.toString() + ":" + minute + " PM"
                                } else {
                                    hourOfDays -= 12
                                    time = hourOfDay.toString() + ":" + minute + " PM"
                                }
                            }
                            tv_Time.text = time
                        }, mHour, mMinute, true)
                timePickerDialog.show()
            }
            R.id.btn_Send -> if (isNetworkConnected(context!!)) {
                if (edt_Name.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_name))
                } else if (edt_ContactNo.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_contact_no))
                } else if (edt_ContactNo.text.toString().length < 10) {
                    showMessage(resources.getString(R.string.enter_correct_contact_no))
                } else if (auto_tv_pick.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_pick_up_address))
                } else if (edt_HouseNo.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_house_no))
                } else if (edt_Street.text.toString().isEmpty()) {
                    showMessage(resources.getString(R.string.enter_street_address))
                } else {
                    if (spnr_now_later.selectedItem == "Later") {
                        if (tv_Date.text.isEmpty()) {
                            showMessage(resources.getString(R.string.enter_date))
                            return
                        } else if (tv_Time.text.isEmpty()) {
                            showMessage(resources.getString(R.string.enter_time))
                            return
                        }
                    }

                    sendEmail(spnr_taxi.selectedItem.toString(), edt_Name.text.toString(),
                            edt_ContactNo.text.toString(), spnr_now_later.selectedItem.toString(),
                            tv_Date.text.toString(), tv_Time.text.toString(), auto_tv_pick.text.toString(),
                            edt_Unit.text.toString(), edt_HouseNo.text.toString(), edt_Street.text.toString(),
                            auto_tv_going_to.text.toString(), edt_Remarks.text.toString())
                }
            } else {
                showMessage(resources.getString(R.string.no_internet))
            }
            else -> {
            }
        }
    }

    private fun sendEmail(taxiName: String, name: String, contactNo: String, dateTimeForm: String,
                  date: String, time: String, pickUpAddress: String, unitId: String,
                  houseNo: String, streetNo: String, goingToAddress: String, remarks: String) {

        val uri = Uri.parse("mailto:" + resources.getString(R.string.email))
                .buildUpon()
                .appendQueryParameter("to", resources.getString(R.string.email))
                .appendQueryParameter("subject", resources.getString(R.string.booking_request) + " "
                        + resources.getString(R.string.app_name))
                .appendQueryParameter("body", "TaxiType: " + taxiName + "\n" + "Name: " + name + "\n" +
                        "ContactNo: " + contactNo + "\n" + "Book: " + dateTimeForm + "\n" +
                        "Date: " + date + "\n" + "Time: " + time + "\n" + "PickUp Address: " + pickUpAddress + "\n" +
                        "Unit & Apartment Id: " + unitId + "\n" + "HouseNo: " + houseNo + "\n" +
                        "Street: " + streetNo + "\n" + "GoingTo: " + goingToAddress + "\n" + "Remarks: " + remarks)
                .build()

        val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
        startActivityForResult(Intent.createChooser(emailIntent, "Send mail for confirm Booking..."), 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            resetAll()
        }
    }

    private fun resetAll() {
        tv_Date.text = ""
        tv_Time.text = ""
        edt_Name.text = null
        edt_ContactNo.text = null
        edt_Street.text = null
        edt_Unit.text = null
        edt_HouseNo.text = null
        edt_Remarks.text = null
        auto_tv_pick.text = null
        auto_tv_going_to.text = null
        linearUnit.visibility = View.GONE
        linearDateTime.visibility = View.GONE
        setAdapterTaxiSpnr()
        setAdapterNowLaterSpnr()
        setPickAdapter()
        setGoingToAdapter()
        edt_Name.requestFocus()
    }
}