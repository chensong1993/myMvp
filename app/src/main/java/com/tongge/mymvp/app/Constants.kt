package com.tongge.mymvp.app

import java.io.File

/**
 *
 *  created : 2/20/21 10:55 AM
 *  @author chensong
 */
object Constants {
    //================= PATH ====================
    //okhttp缓存路径
    val PATH_CACHE = App.instance!!.cacheDir.absolutePath.toString() + File.separator + "data/NetCache"

    val CACHE_CONTROL_AGE = "Cache-Control: public, max-age=360"

    //长缓存有效期为1天
    val CACHE_STALE_LONG = 60 * 60 * 24 * 2

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=$CACHE_STALE_LONG"

    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    val CACHE_CONTROL_NETWORK = "max-age=0"


    val MAIN_URL = "http://yijianjia.com/suieya/"


}