package com.wasseemb.featherforreddit

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * Created by Wasseem on 24/06/2017.
 */

class ScrollAwareFABBehavior(context: Context,
    attributeSet: AttributeSet) : FloatingActionButton.Behavior() {

  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?,
      child: FloatingActionButton?,
      directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
    // Ensure we react to vertical scrolling
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
        coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
  }

  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
      target: View?, dxConsumed: Int, dyConsumed: Int,
      dxUnconsumed: Int, dyUnconsumed: Int) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
        dyUnconsumed)
    if (dyConsumed > 0 && child!!.visibility == View.VISIBLE) {
      // User scrolled down and the FAB is currently visible -> hide the FAB
      child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
          super.onHidden(fab)
          fab!!.visibility = View.INVISIBLE
        }
      })
    } else if (dyConsumed < 0 && child!!.visibility != View.VISIBLE) {
      // User scrolled up and the FAB is currently not visible -> show the FAB
      child.show()
    }
  }

  companion object {

    private val TAG = "ScrollAwareFABBehavior"
  }


}