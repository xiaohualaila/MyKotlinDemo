package com.aier.mykotlindemo

import android.app.Application
import com.aier.mykotlindemo.utils.Utils

class App:Application() {
    init {
        instance = this
    }

    companion object{
        @get:Synchronized
        lateinit var instance :App
    }

    override fun onCreate() {
        super.onCreate()
        ConfigManage.initConfig(this)
        Utils.init(this)
    }

    fun exitApp(){
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }
}