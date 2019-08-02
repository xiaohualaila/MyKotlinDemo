package com.aier.mykotlindemo.module.category

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aier.mykotlindemo.App
import com.aier.mykotlindemo.R
import com.aier.mykotlindemo.widget.RecyclerViewWithFooter.OnLoadMoreListener
import com.aier.mykotlindemo.widget.RecyclerViewWithFooter.RecyclerViewWithFooter
import com.nanchen.aiyagirl.base.BaseFragment
import com.nanchen.aiyagirl.model.ResultsBean
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_category.view.*

class CategoryFragment : BaseFragment(),ICategoryView,SwipeRefreshLayout.OnRefreshListener,OnLoadMoreListener{
    override val categoryName: String by lazy {
            arguments?.getString(CATEGORY_NAME) ?: ""
    }
    private val mAdapter:CategoryRecyclerAdapter by lazy{
        CategoryRecyclerAdapter(context!!)
    }
    private val mICategoryPresenter:ICategoryPresenter by lazy {
        CategoryPresenter(this)
    }


    override fun getCategoryItemsFail(failMessage: String) {
        if (userVisibleHint) {
            Toasty.error(App.instance, failMessage).show()
        }
    }

    override fun setCategoryItems(data: List<ResultsBean>) {
        mAdapter.setData(data)
    }

    override fun addCategoryItems(data: List<ResultsBean>) {
        mAdapter.addData(data)
    }

    override fun showSwipeLoading() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun hideSwipeLoading() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun setLoading() {
        mRecyclerView.setLoading()
    }

    override fun setNoMore() {
        mRecyclerView.setEnd("没有更多数据")
    }

    private lateinit var mRecyclerView :RecyclerViewWithFooter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout


    override val contentViewLayoutID: Int
        get() = R.layout.fragment_category

    override fun init(view: View) {
        mSwipeRefreshLayout =view.swipe_refresh_layout
        mRecyclerView = view.recyclerView
        mSwipeRefreshLayout.setOnRefreshListener (this)

        mRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setOnLoadMoreListener(this)
        mRecyclerView.setEmpty()

        mICategoryPresenter.subscribe()
    }

    override fun onRefresh() {
        mICategoryPresenter.getCategoryItems(true)
    }

    override fun onLoadMore() {
        mICategoryPresenter.getCategoryItems(false)
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

    override fun onDestroy() {
        super.onDestroy()
        mICategoryPresenter.unSubscribe()
    }
}