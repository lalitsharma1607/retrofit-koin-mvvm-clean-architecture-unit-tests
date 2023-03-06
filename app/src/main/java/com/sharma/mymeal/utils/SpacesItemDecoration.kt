package com.sharma.mymeal.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(private val space: Int, private val mNumCol: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {

        //outRect.right = space;
        outRect.bottom = space
        //outRect.left = space;

        //Log.d("ttt", "item position" + parent.getChildLayoutPosition(view));
        val position = parent.getChildLayoutPosition(view)
        if (mNumCol <= 2) {
            if (position == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                if (position % mNumCol != 0) {
                    outRect.left = space / 2
                    outRect.right = space
                } else {
                    outRect.left = space
                    outRect.right = space / 2
                }
            }
        } else {
            if (position == 0) {
                outRect.left = space
                outRect.right = space / 2
            } else {
                if (position % mNumCol == 0) {
                    outRect.left = space
                    outRect.right = space / 2
                } else if (position % mNumCol == mNumCol - 1) {
                    outRect.left = space / 2
                    outRect.right = space
                } else {
                    outRect.left = space / 2
                    outRect.right = space / 2
                }
            }
        }
        if (position < mNumCol) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
        // Add top margin only for the first item to avoid double space between items
        /*
        if (parent.getChildLayoutPosition(view) == 0 ) {

        } else {
            outRect.top = 0;
        }*/
    }
}