package com.tongge.mymvp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tongge.mymvp.R
import com.tongge.mymvp.base.BaseActivity
import com.tongge.mymvp.mvp.contract.ContractLogin
import com.tongge.mymvp.mvp.model.entity.IdentityEntity
import com.tongge.mymvp.mvp.presenter.LoginPresenter

class LoginActivity : BaseActivity<ContractLogin.View, LoginPresenter>(), ContractLogin.View {

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        mPresenter().loginByPhone("17621147941", 1, "12345678")
    }

    override fun loginSuccess(s: IdentityEntity?) {
        println("okk")
    }

    override fun loginErr(err: String) {
        println(err)
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun createView(): ContractLogin.View {
        return this
    }


}