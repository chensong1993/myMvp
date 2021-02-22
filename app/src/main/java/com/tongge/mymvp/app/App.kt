package com.tongge.mymvp.app

import android.app.Application

/**
 *
 *  created : 2/20/21 9:54 AM
 *  @author chensong
 */
class App : Application() {

    //设置成static
    companion object {
        //单例模式
        var instance: App? = null
            get() {
                if (field == null) {
                    field = App()
                }
                return field
            }

        @Synchronized
        fun get(): App {
            return instance!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}