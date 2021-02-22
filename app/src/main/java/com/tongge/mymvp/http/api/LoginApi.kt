package com.tongge.mymvp.http.api

import com.tongge.mymvp.http.ApiResponse
import com.tongge.mymvp.mvp.model.entity.IdentityEntity
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Query


/**
 *
 *  created : 2/20/21 11:39 AM
 *  @author chensong
 */
interface LoginApi {

    /**
     * @return 登录 密码和验证码
     */
    @GET("userInfo/loginByPhone.do")
    fun loginByPhone(
        @Query("phone") phone: String,
        @Query("type") type: Int,
        @Query("pwd") pwd: String
    ): Single<ApiResponse<IdentityEntity>>


}