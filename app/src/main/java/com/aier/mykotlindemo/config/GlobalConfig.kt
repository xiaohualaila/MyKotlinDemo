package com.aier.mykotlindemo.config

/**
 * 全局配置
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  9:43
 */

object GlobalConfig {

    private const val CATEGORY_COUNT = 10
    private const val CATEGORY_NAME_APP = "App"
    private const val CATEGORY_NAME_ANDROID = "Android"
    private const val CATEGORY_NAME_IOS = "iOS"
    private const val CATEGORY_NAME_FRONT_END = "前端"
    private const val CATEGORY_NAME_RECOMMEND = "瞎推荐"
    private const val CATEGORY_NAME_RESOURCE = "拓展资源"
    val TITLES = arrayOf(CATEGORY_NAME_APP)
  //val TITLES = arrayOf(CATEGORY_NAME_APP,CATEGORY_NAME_ANDROID,CATEGORY_NAME_IOS,CATEGORY_NAME_FRONT_END,CATEGORY_NAME_RECOMMEND,CATEGORY_NAME_RESOURCE)
}
