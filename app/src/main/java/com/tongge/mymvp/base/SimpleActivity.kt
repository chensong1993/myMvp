package com.tongge.mymvp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *
 *  created : 2/20/21 4:49 PM
 *  @author chensong
 */
abstract class SimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initData()
    }

    abstract fun layoutId(): Int

    abstract fun initData()

}