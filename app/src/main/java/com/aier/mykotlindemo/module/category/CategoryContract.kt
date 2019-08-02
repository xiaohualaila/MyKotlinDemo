package com.aier.mykotlindemo.module.category

import com.nanchen.aiyagirl.base.BasePresenter
import com.nanchen.aiyagirl.base.BaseView
import com.nanchen.aiyagirl.model.ResultsBean

interface ICategoryView :BaseView {
    val categoryName :String

    fun getCategoryItemsFail(failMessage:String)

    fun setCategoryItems(data :List<ResultsBean>)

    fun addCategoryItems(data: List<ResultsBean>)

    fun showSwipeLoading()

    fun hideSwipeLoading()

    fun setLoading()

    fun setNoMore()
}
interface ICategoryPresenter : BasePresenter{
    fun getCategoryItems(isRefresh :Boolean)
}

