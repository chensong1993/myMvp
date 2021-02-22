package com.tongge.mymvp.mvp.contract

import com.tongge.mymvp.base.IBaseView
import com.tongge.mymvp.base.IPresenter
import com.tongge.mymvp.mvp.model.entity.IdentityEntity

/**
 *
 *  created : 2/20/21 11:36 AM
 *  @author chensong
 */
interface ContractLogin {

    interface View : IBaseView {
        fun loginSuccess(s: IdentityEntity?)
        fun loginErr(err:String)
    }

    interface Presenter:IPresenter<View>{
        fun loginByPhone(phone: String, type: Int, pwd: String)
    }
}