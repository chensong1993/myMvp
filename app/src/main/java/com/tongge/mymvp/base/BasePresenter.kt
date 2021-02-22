package com.tongge.mymvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 *
 *  created : 2/20/21 10:14 AM
 *  @author chensong
 */
abstract class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mView: T? = null

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(rootView: T) {
        this.mView = rootView
    }

    override fun detachView() {
        if (mView != null) {
            mView = null
        }
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addCompositeDisposable(d: Disposable?) {
        compositeDisposable.add(d!!)
    }
}