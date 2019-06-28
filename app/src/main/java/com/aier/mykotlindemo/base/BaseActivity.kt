package com.aier.mykotlindemo.base

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.aier.mykotlindemo.utils.StatusBarUtil

abstract class BaseActivity:AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInit()
        if(contentViewLayoutID !=0){
            setContentView(contentViewLayoutID)
            ButterKnife.bind(this)
            initView(savedInstanceState)
        }
    }

    protected open fun beforeInit() {

    }
    protected abstract val contentViewLayoutID: Int

    protected abstract fun initView(savedInstanceState: Bundle?)

    fun setStatusBarTransparent() {
        StatusBarUtil.setTransparent(this)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected fun setStatusBarColor(@ColorInt color: Int) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha)
    }
}