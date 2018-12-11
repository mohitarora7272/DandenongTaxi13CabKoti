package com.dandenongtaxi13cab.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.dandenongtaxi13cab.R
import com.dandenongtaxi13cab.interfaces.OnBookNowClick
import com.dandenongtaxi13cab.ui.fragments.*
import com.dandenongtaxi13cab.utils.AppConstants
import com.dandenongtaxi13cab.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * @author by Mohit Arora on 3/8/18.
 */
class MainActivity : BaseActivity(), OnBookNowClick, ActivityCompat.OnRequestPermissionsResultCallback {

    private var activityTitles: Array<String>? = null
    private var navItemIndex = 0

    private var currentTag = AppConstants().tagHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.BLACK)
        CommonUtils().blinkText(linear_toolbar)

        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
        loadNavHeader()
        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            currentTag = AppConstants().tagHome
            loadHomeFragment()
        }

        linear_toolbar.setOnClickListener { call(this) }

        fab.setOnClickListener { call(this) }
    }

    override fun setUp() {}

    private fun loadNavHeader() {
        // Navigation view header
        val navHeader = nav_view.getHeaderView(0)
        val tvNavContactText = navHeader.findViewById<View>(R.id.tvNavContactText) as TextView
        val imgPhone = navHeader.findViewById<View>(R.id.imgPhone) as ImageButton
        val linearNumber = navHeader.findViewById<View>(R.id.linearNumber) as LinearLayout
        CommonUtils().blinkText(tvNavContactText)
        CommonUtils().blinkText(imgPhone)

        linearNumber.setOnClickListener {
            drawer_layout.closeDrawers()
            call(this)
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun loadHomeFragment() {

        // selecting appropriate nav menu item
        selectNavMenu()

        // set toolbar title
        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(currentTag) != null) {
            drawer_layout.closeDrawers()

            // show or hide the fab button
            toggleFab(false)
            return
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Handler().postDelayed({
            // update the main content by replacing fragments
            val fragment = getHomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment, currentTag)
            fragmentTransaction.commitAllowingStateLoss()
        }, AppConstants().slidingLaggingTime.toLong())

        // show or hide the fab button
        toggleFab(false)

        //Closing drawer on item click
        drawer_layout.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun getHomeFragment(): Fragment? {
        when (navItemIndex) {
            0 -> return HomeFragment()
            1 -> return BookCabFragment()
            2 -> return OurServicesFragment()
            3 -> return AboutUsFragment()
            4 -> return ContactUsFragment()
            5 -> return PrivacyPolicyFragment()
            else -> return HomeFragment()
        }
    }

    private fun setToolbarTitle() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            //getSupportActionBar().setTitle(activityTitles[navItemIndex]);
            toolbar_title.text = activityTitles!![navItemIndex]
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun selectNavMenu() {
        nav_view.menu.getItem(navItemIndex).isChecked = true
    }

    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_home -> {
                    toggleFab(false)
                    navItemIndex = 0
                    currentTag = AppConstants().tagHome
                }
                R.id.nav_book_cab -> {
                    toggleFab(false)
                    navItemIndex = 1
                    currentTag = AppConstants().tagBookCab
                }
                R.id.nav_our_services -> {
                    toggleFab(false)
                    navItemIndex = 2
                    currentTag = AppConstants().tagOurServices
                }
                R.id.nav_about_us -> {
                    toggleFab(false)
                    navItemIndex = 3
                    currentTag = AppConstants().tagAboutUs
                }
                R.id.nav_contact_us -> {
                    toggleFab(false)
                    navItemIndex = 4
                    currentTag = AppConstants().tagContactUs
                }
                R.id.nav_privacy_policy -> {
                    toggleFab(false)
                    navItemIndex = 5
                    currentTag = AppConstants().tagPrivacyPolicy
                }
                R.id.nav_share -> {
                    toggleFab(false)
                    drawer_layout.closeDrawers()
                    CommonUtils().share(this)
                }
                else -> {
                    toggleFab(false)
                    navItemIndex = 0
                }
            }

            //Checking if the item is in checked state or not, if not make it in checked state
            menuItem.isChecked = !menuItem.isChecked
            menuItem.isChecked = true
            toggleFab(false)
            loadHomeFragment()
            true
        })

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
                toggleFab(false)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView)
                hideKeyboard()
                toggleFab(false)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toggleFab(false)
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                toggleFab(false)
            }
        }

        //Setting the actionbarToggle to drawer layout
        drawer_layout.addDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()
    }

    private fun toggleFab(isShow: Boolean) {
        if (isShow) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    override fun bookNowClick() {
        if (navItemIndex != 0) {
            navItemIndex = 1
            currentTag = AppConstants().tagBookCab
            loadHomeFragment()
        }
    }

    override fun toggleShowHide(isShow: Boolean) {
        toggleFab(isShow)
    }

    @SuppressLint("MissingPermission")
    private fun call(ctx: Context) {
        if (!isPermissionsGranted(this, Manifest.permission.CALL_PHONE)) {
            requestPermission(this, Array(1) { Manifest.permission.CALL_PHONE }, AppConstants().responseCodeCall)
            return
        }

        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + ctx.getString(R.string.contact_no))
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants().responseCodeCall -> call(this)
            else -> call(this)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        val shouldLoadHomeFragOnBackPress = true
        // checking if user is on other navigation menu
        // rather than home
        if (navItemIndex != 0) {
            navItemIndex = 0
            currentTag = AppConstants().tagHome
            loadHomeFragment()
            return
        }

        CommonUtils().showDialog(this, getString(R.string.exit_message), getString(R.string.yes_text), getString(R.string.no_text))
        //super.onBackPressed();
    }
}