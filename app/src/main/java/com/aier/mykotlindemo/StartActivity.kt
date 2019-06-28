package com.aier.mykotlindemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.aier.mykotlindemo.base.BaseActivity
import com.aier.mykotlindemo.module.home.HomeActivity
import kotlinx.android.synthetic.main.activity_start.*
import java.util.ArrayList


class StartActivity :BaseActivity() {

    private var isIn: Boolean = false

    override fun initView(savedInstanceState: Bundle?) {
        initPermission()
//        val i= Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.size)
        splash_iv_defult_pic.setImageDrawable(resources.getDrawable(R.drawable.img_transition_default))
        Handler().postDelayed({ splash_iv_defult_pic.visibility = View.GONE }, 1500)

        Handler().postDelayed({ toMainActivity() }, 3500)
        splash_tv_jump.setOnClickListener { toMainActivity() }
    }

    private fun toMainActivity() {
        if (isIn) {
            return
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in,R.anim.screen_zoom_out)
        finish()
        isIn = true
    }




    override val contentViewLayoutID: Int
        get() = R.layout.activity_start

    /**
     * android 6.0 以上需要动态申请权限
     */
    private fun initPermission() {
        val permissions = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm)
                // 进入到这里代表没有权限.

            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toTypedArray(), 123)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
}