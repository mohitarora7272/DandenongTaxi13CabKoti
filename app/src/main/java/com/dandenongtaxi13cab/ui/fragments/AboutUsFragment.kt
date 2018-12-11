package com.dandenongtaxi13cab.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.interfaces.OnBookNowClick

/**
 * @author by Mohit Arora on 3/8/18.
 */
class AboutUsFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is OnBookNowClick) {
            bookNowClick = activity as OnBookNowClick
        } else {
            throw RuntimeException(activity.toString() + " must implement OnBookNowClick in MainActivity")
        }
    }
}