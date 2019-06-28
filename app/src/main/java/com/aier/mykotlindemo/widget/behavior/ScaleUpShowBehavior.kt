package com.nanchen.aiyagirl.widget.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.AttributeSet
import android.view.View
import com.aier.mykotlindemo.utils.AnimatorUtil


/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  15:21
 */

class ScaleUpShowBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

    private var isAnimatingOut = false

    private val viewPropertyAnimatorListener = object : ViewPropertyAnimatorListener {

        override fun onAnimationStart(view: View) {
            isAnimatingOut = true
        }

        override fun onAnimationEnd(view: View) {
            isAnimatingOut = false
            view.visibility = View.GONE
        }

        override fun onAnimationCancel(arg0: View) {
            isAnimatingOut = false
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        if ((dyConsumed > 0 && dyUnconsumed == 0 || dyConsumed == 0 && dyUnconsumed > 0) && child.visibility != View.VISIBLE) {// 显示
            AnimatorUtil.scaleShow(child, null)
        } else if ((dyConsumed < 0 && dyUnconsumed == 0 || dyConsumed == 0 && dyUnconsumed < 0) && child.visibility != View.GONE && !isAnimatingOut) {
            AnimatorUtil.scaleHide(child, viewPropertyAnimatorListener)
        }
    }
}
