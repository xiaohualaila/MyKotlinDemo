package com.aier.mykotlindemo.module.home

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.widget.ImageView
import android.widget.Toast
import com.aier.mykotlindemo.GlideImageLoader
import com.aier.mykotlindemo.R
import com.aier.mykotlindemo.base.BaseActivity
import com.aier.mykotlindemo.config.GlobalConfig
import com.aier.mykotlindemo.module.category.CategoryFragment
import com.aier.mykotlindemo.module.picture.PictureActivity
import com.aier.mykotlindemo.utils.AndroidWorkaround
import com.aier.mykotlindemo.utils.ScreenUtil
import com.aier.mykotlindemo.utils.StatusBarUtil
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import com.nanchen.aiyagirl.base.adapter.CommonViewPagerAdapter
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity(), HomeContract.IHomeView, OnBannerListener {

    lateinit var mHeadImg: ImageView
    lateinit var mToolbar: android.support.v7.widget.Toolbar
    lateinit var mAppbar: AppBarLayout
    lateinit var mTabLayout: DachshundTabLayout
    lateinit var mViewPager: ViewPager
    lateinit var mNavView: NavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mBanner: Banner
    lateinit var mFab: FloatingActionButton

    private var mExitTime: Long = 0

    private val mHomePresenter: HomePresenter by lazy {
        HomePresenter(this)
    }

    override fun beforeInit() {
        super.beforeInit()
        StatusBarUtil.setTranslucent(this)
    }


    override val contentViewLayoutID: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mHeadImg = main_head_img
        mToolbar = main_toolbar
        mAppbar = main_appbar
        mTabLayout = main_tab
        mViewPager = main_vp
        mNavView = nav_view
        mDrawerLayout = mainActivity
        mBanner = main_banner
        mFab = main_fab
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            val layoutParams = mToolbar.layoutParams
            layoutParams.height = ScreenUtil.dip2px(this, 80f)
            mToolbar.layoutParams = layoutParams
        }

        // 华为底部导航栏适配
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content))
        }

        mBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mBanner.setOnBannerListener(this)
        val titles = GlobalConfig.TITLES
        val infoPagerAdapter = CommonViewPagerAdapter(supportFragmentManager, titles)
        val appFragment = CategoryFragment.newInstance(titles[0])
        infoPagerAdapter.addFragment(appFragment)
        mViewPager.adapter = infoPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 1
        mViewPager.offscreenPageLimit = 6
        mHomePresenter.subscribe()
    }

    override fun OnBannerClick(position: Int) {
        val (desc, url) = mHomePresenter.bannerModel[position]
        PictureActivity.start(this, url, desc, mBanner)
    }


    override fun showBannerFail(failMessage: String) {
        Toasty.error(this, failMessage).show()
    }

    override fun setBanner(imgUrls: List<String>) {
        mBanner.setImages(imgUrls).setImageLoader(GlideImageLoader()).start()

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - mExitTime>2000){
            Snackbar.make(mDrawerLayout,"再按一次退出程序哦~",Toast.LENGTH_SHORT).show()
            mExitTime = System.currentTimeMillis()
        } else{
            finish()
        }
    }

    override fun onDestroy() {
        mHomePresenter.unSubscribe()
        super.onDestroy()
    }

}
