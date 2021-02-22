package com.tongge.mymvp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tongge.mymvp.mvp.presenter.LoginPresenter

/**
 *
 *  created : 2/20/21 10:15 AM
 *  @author chensong
 */
abstract class BaseActivity<V : IBaseView, P : BasePresenter<V>> : SimpleActivity(), IBaseView {

    private var mPresenter: P? = null
    private var mView: V? = null

    init {
        if (mPresenter == null) {
            mPresenter = createPresenter()
        }
        if (mView == null) {
            mView = createView()
        }

        if (mPresenter != null) {
            mPresenter!!.attachView(mView!!)
        }
    }


    fun mPresenter(): P {
        return mPresenter!!
    }

    abstract fun createPresenter(): P
    abstract fun createView(): V


}