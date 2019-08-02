package com.aier.mykotlindemo.module.navhome

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import butterknife.OnClick
import com.aier.mykotlindemo.R
import com.aier.mykotlindemo.base.BaseActivity
import com.aier.mykotlindemo.utils.StatusBarUtil
import com.aier.mykotlindemo.utils.ToastyUtil
import kotlinx.android.synthetic.main.activity_nav_home.*

class NavHomeActivity : BaseActivity(){

    lateinit var mToolbar: Toolbar
    lateinit var mFab:FloatingActionButton

    override val contentViewLayoutID: Int
        get() = R.layout.activity_nav_home

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = nav_home_toolbar
        mFab = nav_home_fab
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar)
        mToolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(mToolbar)
        //不添加这句华为手机会出现标题显示不完整的问题
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.nav_home_fab)
    fun onClick() {
       // ShareUtil.share(this, R.string.string_share_text)
        ToastyUtil.showSuccess("分享")
    }
}