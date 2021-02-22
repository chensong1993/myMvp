package com.tongge.mymvp.mvp.model

import android.annotation.SuppressLint
import android.util.Log
import com.tongge.mymvp.app.Constants
import com.tongge.mymvp.http.ApiResponse
import com.tongge.mymvp.http.ApiService
import com.tongge.mymvp.http.api.LoginApi
import com.tongge.mymvp.mvp.model.entity.IdentityEntity
import com.tongge.mymvp.rx.scheduler.RxUtils
import io.reactivex.Single


/**
 *
 *  created : 2/20/21 11:43 AM
 *  @author chensong
 */
class LoginModel {

    /**
     * @param type      1账号密码登录，2验证码登录
     */
    fun loginByPhone(
        phone: String,
        type: Int,
        pwd: String
    ): Single<IdentityEntity> {
        return ApiService.getInstance.create(LoginApi::class.java)
            .loginByPhone(phone, type, pwd)
            .compose(RxUtils.rxSchedulerHelperSingle())
            .compose(RxUtils.handleResultsSingle())

    }
}