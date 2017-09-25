package com.haiqi.base.common.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.haiqi.base.R
import com.haiqi.base.common.BaseLayout
import com.haiqi.base.common.UILoadLayout
import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.rx.rxlifecycle.RxFragment
import com.ssdf.highup.base.interf.IBaseUi
import com.trello.rxlifecycle2.android.FragmentEvent
import org.jetbrains.anko.find

/**
 * Created by zhutao on 2017/8/2.
 *  第一层封装fragment 统一的头部 加载动画
 */
abstract class DelegateFra : RxFragment(),IBaseUi{

    var mActivity: DelegateAct? = null

    private val mLayout by lazy(LazyThreadSafetyMode.NONE){
        find<BaseLayout>(R.id.root)
    }

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
            mLayout.setStatusBarColor(getStatusBarColor())
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



    open fun OnTopClick(viewId: Int){
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
        mUILoadLayout = UILoadLayout(context)
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
     fun <T> setRequest(url: String, mParams: Params, req: ReqCallBack<T>){

        mUILoadLayout?.let {
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