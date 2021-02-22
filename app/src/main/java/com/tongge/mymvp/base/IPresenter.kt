package com.tongge.mymvp.base

/**
 *
 *  created : 2/20/21 10:11 AM
 *  @author chensong
 */
interface IPresenter<in V : IBaseView> {
    fun attachView(rootView: V)
    fun detachView()
}