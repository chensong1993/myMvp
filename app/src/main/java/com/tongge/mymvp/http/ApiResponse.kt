package com.tongge.mymvp.http

import java.io.Serializable

/**
 *
 *  created : 2/20/21 10:49 AM
 *  @author chensong
 */
class ApiResponse<T> : Serializable {
    private var msg = 0
    private var data: T? = null

    fun getMsg(): Int {
        return msg
    }

    fun setMsg(msg: Int) {
        this.msg = msg
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }


}