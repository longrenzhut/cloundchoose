package com.haiqi.base.common.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.haiqi.base.Config
import com.haiqi.base.R
import com.haiqi.base.http.*
import com.haiqi.base.rx.rxbus.RxBus2
import com.haiqi.base.rx.rxbus.RxMessage
import com.haiqi.base.rx.rxlifecycle.RxAppCompatActivity
import com.haiqi.base.thirdsdk.statusbar.StatusBarCompat
import com.haiqi.base.utils.Mylogger
import com.haiqi.base.utils.getActColor
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.umeng.analytics.MobclickAgent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.util.*

/**
 * Created by zhutao on 2017/8/2.
 * 集成友盟等监听
 * activity 第一层封装（基础层）。生命周期的打印、观察，
 * 触屏收缩键盘， 沉浸式标题栏
 */
open class AbsAct: RxAppCompatActivity(){

    val mLifeCycle by lazy(LazyThreadSafetyMode.NONE){
        LifeCycle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifeCycle.OnCreate(this)
        log("onCreate()")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mLifeCycle.onPostCreate()
    }

    override fun onStart() {
        super.onStart()
        mLifeCycle.onStart()
        log("onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        mLifeCycle.onRestart()
        log("onRestart()")
    }

    override fun onResume() {
        super.onResume()
        mLifeCycle.onResume()
        log("onResume()")
        if(Config.DEBUG)
            return
        val name = javaClass.name
        MobclickAgent.onPageStart(name)
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        mLifeCycle.onPause()
        log("onPause()")
        if(Config.DEBUG)
            return
        val name = javaClass.name
        MobclickAgent.onPageEnd(name)
        MobclickAgent.onPause(this)
    }

    override fun onStop() {
        mLifeCycle.onStop()
        super.onStop()
        log("onStop()")
    }


    override fun onDestroy() {
        mLifeCycle.OnDestroy()
        super.onDestroy()
        log("onDestroy()")
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus;
            if (isHideInput(view, ev)) {
                hideSoftInput(view.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 判定是否需要隐藏
     */
    fun  isHideInput(v: View?, ev: MotionEvent): Boolean {
        if (v != null && (v is EditText)) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            if (ev.x > left && ev.x < right && ev.y > top
                    && ev.y < bottom) {
                return false
            } else {
                return true
            }
        }
        return false
    }

    /**
     * 隐藏软键盘
     */
    fun  hideSoftInput(token: IBinder?) {
        if (token != null) {
            val manager: InputMethodManager =  getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /***
     * 5.0以下通过获取状态栏对象 设置颜色
     * 5.0以上 设置全屏通过StustasBarView 取代状态栏设置颜色
     */
    protected fun setStatusBar(color: Int = R.color.transparent) {
        StatusBarCompat.setStatusBarColor(window,
                getActColor(color), true,true)
    }

    fun log(msg: String){
        Mylogger.d(javaClass.name + " :  " +  msg)
    }


    //-------------------------封装统一的网络请求


    /**
     * url 请求的叠纸
     * mParams 参数
     */
    fun request(observable: Observable<ResponseBody>,
                observer: BaseSubscriber<ResponseBody>,
                compose: LifecycleTransformer<ResponseBody>
                = bindUntilEvent(ActivityEvent.DESTROY)){
        observable.compose(compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer)
    }

    //----------通信--------------

    /**
     * 先注册 订阅
     */
    fun <T> registerRxBus(code: Int,obtainMsg: (T)-> Unit,
                          compose: LifecycleTransformer<RxMessage>){
        RxBus2.instance().toFlowable(code)
                .compose(compose)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    obtainMsg(it.msg as T)
                }
    }
    fun <T> registerRxBus(code: Int,obtainMsg: (T)-> Unit){
        registerRxBus(code,obtainMsg,bindUntilEvent(ActivityEvent.DESTROY))
    }


    fun post(code: Int,any: Any){
        RxBus2.instance().post(code,any)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        val fras = supportFragmentManager.fragments
        fras?.let{
            for(fra in it)
                fra.onActivityResult(requestCode,resultCode,data)
        }
    }

}