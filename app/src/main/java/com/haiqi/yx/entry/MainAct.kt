package com.haiqi.yx.entry

import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.common.activity.BaseAct
import com.haiqi.base.rx.rxbinding.RxClick
import com.haiqi.base.utils.glide.GlideHelper
import com.haiqi.yx.R.layout.act_main
import com.haiqi.yx.entry.presenter.IMain
import com.haiqi.yx.entry.presenter.MainPt
import kotlinx.android.synthetic.main.act_main.*


/**
 * Created by zhutao on 2017/8/3.
 */
class MainAct: BaseAct<MainPt, IMain>(), IMain {

    override fun getPresenter(): MainPt {
        val pt = MainPt()
        pt.attach(this)
        return pt
    }

    override fun getLayoutId(): Int =
            act_main

    override fun initView() {
//        setCommonHeader()
        setCommonHeader("云选")
//        setUILoadLayout()
        requestData()
    }

    override fun requestData() {
        mPrsenter.requestData()
//        val list = listOf(1,2)
//        Observable.from(list).filter(object : Func1<Int,Boolean>{
//            override fun call(t: Int?): Boolean? {
//                return false
//            }
//
//        }).subscribe {  }
        tv_input.RxClick(this){
            ARouter.getInstance().build("/tent/sss").navigation(this)
//            Router(this@MainAct).go<TestAct>()
        }
        val url = "http://haiqihuocang.oss-cn-hangzhou.aliyuncs.com/product_img/20170712149986128464639794.jpg"
        GlideHelper.instance().load(iv_icon,url,40,40)
        glide_test.setUrl(url)
        glide_s.setUrl(url)
//        Observable.
//        RxTextView.textChanges(usernameEditText);
//        RxTextView.textChanges()
    }


}