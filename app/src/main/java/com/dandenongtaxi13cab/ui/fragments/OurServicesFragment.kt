package com.dandenongtaxi13cab.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.data.adapters.MyRecyclerViewAdapter
import com.dandenongtaxi13cab.data.modals.ItemObject
import com.dandenongtaxi13cab.interfaces.OnBookNowClick
import com.dandenongtaxi13cab.utils.ItemOffsetDecoration
import kotlinx.android.synthetic.main.fragment_our_services.*
import java.util.*

/**
 * @author by Mohit Arora on 3/8/18.
 */
class OurServicesFragment : BaseFragment(), MyRecyclerViewAdapter.ItemClickListener {

    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var bookNowClick: OnBookNowClick? = null
    private var lLayout: GridLayoutManager? = null
    private var rcAdapter: MyRecyclerViewAdapter? = null

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
        return inflater.inflate(R.layout.fragment_our_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rowListItem = getAllItemList()
        lLayout = GridLayoutManager(activity, 2)
        lLayout!!.isSmoothScrollbarEnabled = true
        val itemDecoration = ItemOffsetDecoration()
        itemDecoration.setItemOffsetDecoration(activity!!, R.dimen.item_offset)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = lLayout

        rcAdapter = MyRecyclerViewAdapter(rowListItem, activity)
        rcAdapter!!.setClickListener(this@OurServicesFragment)
        recyclerView.adapter = rcAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is OnBookNowClick) {
            bookNowClick = activity as OnBookNowClick
        } else {
            throw RuntimeException(activity.toString() + " must implement OnBookNowClick in MainActivity")
        }
    }

    private fun getAllItemList(): List<ItemObject> {

        val allItems = ArrayList<ItemObject>()

        val itemObject = ItemObject(getString(R.string.wedding_text), "http://www.dandenongtaxi13cab.com.au/wp-content/uploads/2017/10/James-and-Olivia-Wedding_1458-copy.jpg")
        allItems.add(itemObject)

        val itemObject2 = ItemObject(getString(R.string.word_class_text), "http://www.dandenongtaxi13cab.com.au/wp-content/uploads/2017/10/IMG_6750.jpg")
        allItems.add(itemObject2)

        val itemObject3 = ItemObject(getString(R.string.airport_taxi_text), "http://www.dandenongtaxi13cab.com.au/wp-content/uploads/2017/10/92539bff03d262350130e819436bb1b7.jpg")
        allItems.add(itemObject3)

        return allItems
    }

    override fun onItemClick(view: View, position: Int) {
        bookNowClick!!.bookNowClick()
    }
}