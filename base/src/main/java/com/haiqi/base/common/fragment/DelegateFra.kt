package com.haiqi.base.common.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haiqi.base.R
import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.common.listener.ICommonLayout
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.rx.rxlifecycle.RxFragment
import com.ssdf.highup.base.interf.IBaseUi
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/8/2.
 *  第一层封装fragment 统一的头部 加载动画
 */
abstract class DelegateFra : RxFragment(),IBaseUi{

    var mActivity: DelegateAct? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = activity as DelegateAct
    }

    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView  = inflater.inflate(getLayoutId(), container, false)
        return mView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isStatusBarView())
            mUiLayout.setStatusBarView(getStatusBarColor())
        setPresenter()
        initView()
    }

    fun getStatusBarColor(): Int = R.color.white

    fun isStatusBarView(): Boolean  = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun show(text: String = "加载中..."){
        mActivity?.show(text)
    }

    fun dismissDialog(){
        mActivity?.dismissDialog()
    }


    inline fun <reified T: View> find(id: Int): T{
       return mView.find<T>(id)
    }



    val mUiLayout by lazy(LazyThreadSafetyMode.NONE){
        val root = find<ViewGroup>(R.id.root)
        root as ICommonLayout
    }

    open fun OnTopClick(viewId: Int){
    }

    /**
     * 只能调用一次  添加头部
     */
    fun setCommonHeader(title: String,isLeft: Boolean = false){
        mUiLayout.setCommonHeader(title,isLeft){
            OnTopClick(it)
        }
    }

    fun setCommonHeader(title: String,isLeft: Boolean = false,Onleft: (Int)->(Unit)){
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
     * 使用UIVerlayout top 可以不赋值
     * 使用UILayout top 默认头部为 45dp,
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
     fun <T> setRequest(url: String, mParams: Params, req: ReqCallBack<T>){

        mUiLayout.getUILoadLayout()?.let {
            req.setUILayout(it)
        }
        mActivity?.setRequest(url,mParams,req,bindUntilEvent(FragmentEvent.DESTROY))
    }

    //----------通信--------------

    /**
     * 先注册 订阅
     */
    fun <T> registerRxBus(code: Int,obtainMsg: (T)-> Unit){
        mActivity?.registerRxBus(code,obtainMsg,bindUntilEvent(FragmentEvent.DESTROY))
    }

    fun post(code: Int,any: Any){
        mActivity?.post(code,any)
    }
}