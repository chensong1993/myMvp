package com.tongge.mymvp.http

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tongge.mymvp.app.App
import com.tongge.mymvp.app.Constants
import com.tongge.mymvp.utils.SystemUtil
import okhttp3.Cache
import okhttp3.CacheControl.Companion.FORCE_CACHE
import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 *  created : 2/20/21 10:53 AM
 *  @author chensong
 */
class ApiService {

    private var mOkHttpClient: OkHttpClient? = null


    constructor() {
        initSetting()
    }


    companion object {
        val getInstance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiService()
        }
    }

    /**
     * 创建相应的服务接口
     */
    fun <T> create(service: Class<T>?): T {
        checkNotNull(service, "service is null")
        return Retrofit.Builder()
            .baseUrl(Constants.MAIN_URL)
            .client(mOkHttpClient) //工厂转换器
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(service)
    }

    /**
     * 创建相应的服务接口
     */
    fun <T> create(service: Class<T>?, baseUrl: String): T {
        checkNotNull(service, "service is null")
        checkNotNull(baseUrl, "baseUrl is null")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkHttpClient) //工厂转换器
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(service)
    }

    private fun <T> checkNotNull(o: T?, message: String): T {
        if (o == null) {
            throw NullPointerException(message)
        }
        return o
    }

    private fun initSetting() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        if (mOkHttpClient == null) {
            if (mOkHttpClient == null) {

                // 指定缓存路径,缓存大小100Mb
                val cache = Cache(File(Constants.PATH_CACHE), 1024 * 1024 * 10)
                val sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(App.instance)
                val cookies: List<Cookie> = sharedPrefsCookiePersistor.loadAll()
                for (i in cookies.indices) {
                    val cookie = cookies[i]
                    println("${"ApiService createOkHttpClient: 1810=" + cookie.name + "  " + cookie.value}")
                }
                val cookieJar: ClearableCookieJar = PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
                mOkHttpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .cookieJar(cookieJar)
                    .addInterceptor(mRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build()
            }
        }
    }


    // 云端响应头拦截器，用来配置缓存策略
    private val mRewriteCacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        if (!SystemUtil.isNetworkConnected()) {
            request = request.newBuilder().cacheControl(FORCE_CACHE).build()
        }
        val originalResponse = chain.proceed(request)
        if (SystemUtil.isNetworkConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            //  Log.i(TAG, "isNetworkConnected: ")
            val cacheControl = request.cacheControl.toString()
            originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma").build()
        } else {
            // Log.i(TAG, "noisNetworkConnected: ")
            originalResponse.newBuilder()
                .header("Cache-Control", "public,${Constants.CACHE_CONTROL_CACHE}")
                .removeHeader("Pragma").build()
        }
    }

}

