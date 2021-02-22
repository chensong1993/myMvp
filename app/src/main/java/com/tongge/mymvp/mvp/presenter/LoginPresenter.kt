package com.tongge.mymvp.mvp.presenter

import com.tongge.mymvp.base.BasePresenter
import com.tongge.mymvp.http.ApiResponse
import com.tongge.mymvp.mvp.contract.ContractLogin
import com.tongge.mymvp.mvp.model.LoginModel

/**
 *
 *  created : 2/20/21 11:53 AM
 *  @author chensong
 */
class LoginPresenter : BasePresenter<ContractLogin.View>(), ContractLogin.Presenter {

    private val loginModel by lazy {
        LoginModel()
    }


    override fun loginByPhone(phone: String, type: Int, pwd: String) {
        val dis = loginModel.loginByPhone(phone, type, pwd)
            .subscribe({ s ->
                mView?.apply {
                    loginSuccess(s)
                }
            }, { err ->
                mView?.apply {
                    loginErr(err.message!!)
                }
            }
            )
        addCompositeDisposable(dis)
    }

}