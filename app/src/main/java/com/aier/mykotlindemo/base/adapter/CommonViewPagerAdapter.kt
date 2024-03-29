package com.nanchen.aiyagirl.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.aier.mykotlindemo.base.adapter.MyFragmentPagerAdapter
import java.util.*

/**
 * ViewPager通用
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:29
 */

class CommonViewPagerAdapter(fm: FragmentManager, titles: Array<String>) : MyFragmentPagerAdapter(fm, titles) {
    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
