package com.haiqi.base.common.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.haiqi.base.R
import com.haiqi.base.common.BaseLayout
import com.haiqi.base.common.UILoadLayout
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.widget.dialog.LoadingDialog
import com.ssdf.highup.base.interf.IBaseUi
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import okhttp3.ResponseBody
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/8/2.
 *
 * 添加统一的加载动画 头部
 */
abstract class DelegateAct: AbsAct(),IBaseUi{

    private val mLayout by lazy(LazyThreadSafetyMode.NONE){
        find<BaseLayout>(R.id.root)
    }

    /**
     * 是否使用使用手势滑动退出
     */
    open fun isUseSwipeBack(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        mLifeCycle.OnAttach(isUseSwipeBack())
        mLifeCycle.setOnExitListener {
            OnTopClick(R.id.iv_left)
        }

        //设置5.0以上的状态栏颜色 自定义的
        if(isStatusBarView())
            mLayout.setStatusBarColor(getStatusBarColor())
        setStatusBar(getStatusBarColor())

        setPresenter()
        initView()
    }

    /**
     * 禁止手势滑动退出
     */
    open fun setSwipeBackNoEnable(){
        mLifeCycle.setSwipeBackNoEnable()
    }

    /**
     * 是否添加状态栏的StatusBarView
     */
    open fun isStatusBarView(): Boolean  = true

    /**
     * 动态设置状态栏的颜色
     */
    open fun getStatusBarColor(): Int = R.color.colorPrimaryDark

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
            if(it.isShowing)
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
        mLayout.getCommonHeader()?.initHeader(title,isLeft){
            OnTopClick(it)
        }
    }

    /**
     * 设置标题
     */
    fun setCommonTitle(title: String){
        mLayout.getCommonHeader()?.setTitle(title)

    }


    /**
     * 设置右图标
     */
    fun setIvRight(drawble: Int = 0,onIvRight: (Int)->(Unit),w: Int = 0, h: Int = w){
        mLayout.getCommonHeader()?.setIvRight(drawble,onIvRight,w,h)
    }

    fun setIvRight(drawble: Int = 0,w: Int = 0, h: Int = w){
        mLayout.getCommonHeader()?.setIvRight(drawble,{
            OnTopClick(it)
        },w,h)
    }

    /**
     * 设置右文本
     */
    fun setTvRight(text: String,onTvRight: (Int)->(Unit),textcolor: Int = 0, size: Int = 15){
        mLayout.getCommonHeader()?.setTvRight(text,onTvRight,textcolor,size)
    }

    fun setTvRight(text: String,textcolor: Int = 0, size: Int = 15){
        mLayout.getCommonHeader()?.setTvRight(text,{
            OnTopClick(it)
        },textcolor,size)
    }


    protected var mUILoadLayout: UILoadLayout? = null
    /**
     * 添加统一的加载动画
     * top 表示 加载动画 距离顶部的距离
     * 使用UIVerlayout 不需要赋值
     * 当使用UILayout top 默认距离顶部 45dp
     */
    fun setUILoadLayout() {
        mUILoadLayout = UILoadLayout(this)
        val lp = RelativeLayout.LayoutParams(-1, -1)
        mUILoadLayout?.layoutParams = lp
        mLayout.addView(mUILoadLayout)
        mUILoadLayout?.setReload {
            requestData()
        }
    }


    //-------------------------封装统一的网络请求

    /**
     * url 请求的叠纸
     * mParams 参数
     */
    fun <T> setRequest(url: String, mParams: Params,
                               req: ReqCallBack<T>,
                       compose: LifecycleTransformer<ResponseBody>
                         = bindUntilEvent(ActivityEvent.DESTROY)){

        mLoadingDialog?.let {
            req.setLoading(it)
        }
        mUILoadLayout?.let {
            req.setUILayout(it)
        }
        super.setRequestBase(url,mParams,req, compose)
    }


    override fun onDestroy() {
        super.onDestroy()
        mLoadingDialog = null
    }
}