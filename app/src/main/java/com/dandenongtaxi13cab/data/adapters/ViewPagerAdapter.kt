package com.dandenongtaxi13cab.data.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dandenongtaxi13cab.R

/**
 * @author by Mohit Arora on 3/8/18.
 */
class ViewPagerAdapter internal constructor(private val context: Context?, private val mImageResources: Array<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun getCount(): Int {
        return mImageResources.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false)
        val imageView = itemView.findViewById<View>(R.id.img_pager_item) as ImageView
        val pbLoading = itemView.findViewById<View>(R.id.pb_loading) as ProgressBar
        pbLoading.visibility = View.VISIBLE
        Glide.with(context)
                .load(mImageResources[position])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        // log exception
                        pbLoading.visibility = View.GONE
                        return false // important to return false so the error placeholder can be placed
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        pbLoading.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}