package com.haiqi.tencent

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.rx.rxbinding.RxClick
import kotlinx.android.synthetic.main.act_tencent_test.*

/**
 * Created by Administrator on 2017/8/31.
 */
@Route(path = "/tent/sss")
class TencentAct: DelegateAct(){
    override fun getLayoutId(): Int {

        return R.layout.act_tencent_test
    }

    override fun initView() {
        tv_test.RxClick(this){
            ARouter.getInstance().build("/test/xxx").navigation(this)
        }
    }

    override fun requestData() {
    }


    override fun onBackPressed() {
        super.onBackPressed()
        post(100,"dsf")
    }
}