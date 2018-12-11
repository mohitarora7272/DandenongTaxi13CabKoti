package com.dandenongtaxi13cab.utils

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author by Mohit Arora on 3/8/18.
 */
class ItemOffsetDecoration : RecyclerView.ItemDecoration(){
    private var mItemOffset: Int = 0

    fun setItemOffsetDecoration(context: Context, @DimenRes itemOffsetId: Int) {
        context.resources.getDimensionPixelSize(itemOffsetId)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
    }
}