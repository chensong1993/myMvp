package com.tongge.mymvp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import com.tongge.mymvp.app.App
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 *
 *  created : 2/20/21 11:12 AM
 *  @author chensong
 */
object SystemUtil {

    /**
     * 检查WIFI是否连接
     */
    fun isWifiConnected(): Boolean {
        val connectivityManager =
            App.instance!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return wifiInfo != null
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    fun isMobileNetworkConnected(): Boolean {
        val connectivityManager =
            App.instance!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobileNetworkInfo = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return mobileNetworkInfo != null
    }

    /**
     * 检查是否有可用网络
     */
    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            App.instance!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

    /**
     * 保存文字到剪贴板
     * @param context
     * @param text
     */
    fun copyToClipBoard(context: Context, text: String?) {
        val clipData = ClipData.newPlainText("url", text)
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.setPrimaryClip(clipData)
//        ToastUtil.shortShow("已复制到剪贴板");
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val scale: Float = App.instance!!.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2dp(pxValue: Float): Int {
        val scale: Float = App.instance!!.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}