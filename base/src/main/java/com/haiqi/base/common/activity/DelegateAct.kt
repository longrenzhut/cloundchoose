package com.haiqi.base.common.activity

import android.os.Bundle
import android.view.ViewGroup
import com.haiqi.base.R
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.common.listener.ICommonLayout
import com.haiqi.base.widget.dialog.LoadingDialog
import com.ssdf.highup.base.interf.IBaseUi
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/8/2.
 *
 * 添加统一的加载动画 头部
 */
abstract class DelegateAct: AbsAct(),IBaseUi{

    private val mUiLayout by lazy(LazyThreadSafetyMode.NONE){
        val root = find<ViewGroup>(R.id.root)
        root as ICommonLayout
    }

    /**
     * 是否可以滑动退出
     */
    open fun isSwipeBackEnable(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifeCycle.OnAttach(isSwipeBackEnable())
        if(isSwipeBackEnable())
            mLifeCycle.setOnExitListener {
                OnTopClick(R.id.iv_left)
            }
        setContentView(getLayoutId())
        if(isStatusBarView())
            mUiLayout.setStatusBarView(getStatusBarColor())
        setStatusBar(getStatusBarColor())
        setPresenter()
        initView()
    }

    /**
     * 是否添加状态栏的StatusBarView
     */
    fun isStatusBarView(): Boolean  = true

    /**
     * 动态设置状态栏的颜色
     */
    fun getStatusBarColor(): Int = R.color.white

    override fun onBackPressed() {
        if(!mLifeCycle.onBackPressed())
            super.onBackPressed()
    }


    private var mLoadingDialog: LoadingDialog? = null

    fun show(text: String = "加载中..."){
        if(null == mLoadingDialog)
            mLoadingDialog = LoadingDialog(this)

        mLoadingDialog?.let{
            it.show()
        }
    }

    fun dismissDialog(){
        mLoadingDialog?.let{
            if(it.isShowing())
                it.show()
        }
    }


    open fun OnTopClick(viewId: Int){
        if(viewId == R.id.iv_left)
            finish()
    }

    /**
     * 只能调用一次  添加头部
     */
    fun setCommonHeader(title: String,isLeft: Boolean = true){
        mUiLayout.setCommonHeader(title,isLeft){
            OnTopClick(it)
        }
    }

    fun setCommonHeader(layoutid: Int,headerHeight: Int){
        mUiLayout.setCommonHeader(layoutid,headerHeight)
    }

    fun setCommonHeader(title: String,isLeft: Boolean = true,Onleft: (Int)->(Unit)){
        mUiLayout.setCommonHeader(title,isLeft,Onleft)
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String){
        mUiLayout.setTitle(title)
    }

    /**
     * 设置右图标
     */
    fun setIvRight(drawble: Int = 0,onIvRight: (Int)->(Unit),w: Int = 0, h: Int = w){
        mUiLayout.setIvRight(drawble,onIvRight,w,h)
    }

    fun setIvRight(drawble: Int = 0,w: Int = 0, h: Int = w){
        mUiLayout.setIvRight(drawble,{
            OnTopClick(it)
        },w,h)
    }

    /**
     * 设置右文本
     */
    fun setTvRight(text: String,onTvRight: (Int)->(Unit),textcolor: Int = 0, size: Int = 15){
        mUiLayout.setTvRight(text,onTvRight,textcolor,size)
    }

    fun setTvRight(text: String,textcolor: Int = 0, size: Int = 15){
        mUiLayout.setTvRight(text,{
            OnTopClick(it)
        },textcolor,size)
    }

    /**
     * 添加统一的加载动画
     * top 表示 加载动画 距离顶部的距离
     * 使用UIVerlayout 不需要赋值
     * 当使用UILayout top 默认距离顶部 45dp
     */
    fun setUILoadLayout(top: Int = 45, bottom: Int = 0){
        mUiLayout.setUILoadLayout({
            requestData()
        },top,bottom)
    }


    //-------------------------封装统一的网络请求

    /**
     * url 请求的叠纸
     * mParams 参数
     */
    override fun <T> setRequest(url: String, mParams: Params, req: ReqCallBack<T>){

        mLoadingDialog?.let {
            req.setLoading(it)
        }
        mUiLayout.getUILoadLayout()?.let {
            req.setUILayout(it)
        }
        super.setRequest(url,mParams,req)
    }


    override fun onDestroy() {
        super.onDestroy()
        mLoadingDialog = null
    }
}