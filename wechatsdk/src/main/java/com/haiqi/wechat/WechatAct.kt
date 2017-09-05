package com.haiqi.wechat

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.rx.rxbinding.RxClick
import com.haiqi.base.utils.showToast
import com.haiqi.wechat.R.layout.act_qq_test
import kotlinx.android.synthetic.main.act_qq_test.*

/**
 * Created by Administrator on 2017/8/31.
 */
@Route(path="/test/xxx")
class WechatAct: DelegateAct(){
    override fun getLayoutId(): Int {

        return act_qq_test
    }

    override fun initView() {
        tv_test.RxClick(this){
            ARouter.getInstance().build("/tent/sss").navigation(this)
        }

        registerRxBus<String>(100){
            showToast("我收到了")
        }
    }

    override fun requestData() {
    }

}