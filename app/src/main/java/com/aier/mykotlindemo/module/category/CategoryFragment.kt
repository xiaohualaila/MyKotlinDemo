package com.aier.mykotlindemo.module.category

import android.os.Bundle
import android.view.View
import com.aier.mykotlindemo.R
import com.nanchen.aiyagirl.base.BaseFragment

class CategoryFragment : BaseFragment(){
    override val contentViewLayoutID: Int
        get() = R.layout.fragment_category

    override fun init(view: View) {

    }
    companion object{
        const val CATEGORY_NAME = "com.nanchen.aiyagirl.module.category.CategoryFragment.CATEGORY_NAME"
        fun newInstance(mCategoryName :String):CategoryFragment{
            val categoryFragment = CategoryFragment()
            val bundle =Bundle()
            bundle.putString(CATEGORY_NAME,mCategoryName)
            categoryFragment.arguments=bundle
            return categoryFragment

        }
    }
}