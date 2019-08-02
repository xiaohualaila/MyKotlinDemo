package com.aier.mykotlindemo.module.picture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.OnClick

import com.aier.mykotlindemo.R
import com.aier.mykotlindemo.base.BaseActivity
import com.aier.mykotlindemo.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.github.chrisbanes.photoview.PhotoView
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : BaseActivity(), PictureContract.PictureView {
    private var mBitmap: Bitmap? = null
    lateinit var mImageUrl: String
    lateinit var mImageTitle: String
    lateinit var mToolbar: Toolbar
    lateinit var mImgView: PhotoView
    lateinit var mProgressBar: ProgressBar
    lateinit var mSaveBtn: ImageView

    private val mPresenter: PictureContract.Presenter by lazy {
        PicturePresenter(this)
    }

    private fun parseIntent() {
        mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        mImageTitle = intent.getStringExtra(EXTRA_IMAGE_TITLE)
    }

    override val contentViewLayoutID: Int
        get() = R.layout.activity_picture

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = picture_toolbar
        mImgView = picture_img
        mProgressBar = picture_progress
        mSaveBtn = picture_btn_save
        showProgress()
        parseIntent()
        ViewCompat.setTransitionName(mImgView, TRANSIT_PIC)
        mToolbar.title = if (TextUtils.isEmpty(mImageTitle)) "图片预览" else mImageTitle
        //setSupportActionBar(toolbar)一定要放在setNavigationOnClickListener前,
        //不然onclick无效
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.hide()

        ////kotlin:
        //  a?.foo()
        //
        ////相当于java:
        //  if(a!=null){
        //   a.foo();
        //  }
        val target = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                hideProgress()
                mBitmap = resource
                if (resource != null) {
                    mSaveBtn.visibility = View.VISIBLE
                    mImgView.setImageBitmap(resource)
                } else {
                    mSaveBtn.visibility = View.GONE
                }
            }
        }

        Glide.with(Utils.getContext())
            .load(mImageUrl)
            .asBitmap()
            .dontAnimate()
            .into(target)
    }

    override fun hideProgress() {
        mProgressBar.visibility = View.GONE
    }

    override fun showProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    companion object {
        const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"
        const val EXTRA_IMAGE_TITLE = "EXTRA_IMAGE_TITLE"
        const val TRANSIT_PIC = "picture"
        fun newIntent(context: Context, url: String, desc: String): Intent {
            val intent = Intent(context, PictureActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URL, url)
            intent.putExtra(EXTRA_IMAGE_TITLE, desc)
            return intent
        }

        fun start(context: Activity, url: String, desc: String, banner: Banner) {
            val intent = Intent(context, PictureActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URL, url)
            intent.putExtra(EXTRA_IMAGE_TITLE, desc)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context, banner, TRANSIT_PIC
            )//与xml文件对应
            ActivityCompat.startActivity(context, intent, options.toBundle())
        }
    }

    @OnClick(R.id.picture_img)
    fun onPictureClick() {
        if (supportActionBar != null) {
            if (supportActionBar!!.isShowing) {// !!表示当前对象不为空的情况下执行
                supportActionBar!!.hide()
            } else {
                supportActionBar!!.show()
            }
        }
    }

    @OnClick(R.id.picture_btn_save)
    fun onClick() {
        Log.i("sss","mImageUrl " + mImageUrl);
        mPresenter.saveGirl(mImageUrl, mBitmap!!, mImageTitle)
    }

}
