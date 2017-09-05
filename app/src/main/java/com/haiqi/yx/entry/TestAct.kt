package com.haiqi.yx.entry

import com.haiqi.base.common.activity.DelegateAct
import com.haiqi.base.common.adapter.viewpager.BaseVpAdapter
import com.haiqi.base.common.adapter.viewpager.FraModel
import com.haiqi.base.widget.shape.AnimPieCircleView
import com.haiqi.yx.R.layout.act_test
import kotlinx.android.synthetic.main.act_test.*

/**
 * Created by zhutao on 2017/8/15.
 */

class TestAct: DelegateAct(){
    override fun getLayoutId(): Int {
        return act_test
    }

    override fun initView() {
        setCommonHeader("test")
//        setUILoadLayout()
        val list = mutableListOf(
                FraModel<String>("A",TestFra(),"dsfds"),
                FraModel<String>("B",TestFra(),"dsfds"),
                FraModel<String>("C",TestFra(),"dsfds")
        )
        val mAdapter = BaseVpAdapter(supportFragmentManager,list)
        vp_test.adapter = mAdapter

        tab_test.setViewPage(vp_test)

        tab_test_1.setViewPage(vp_test)

        apb_test.setProgress(100,20)
        apb_test_1.setProgress(100,60)
        apb_test_2.setProgress(100,45)
        apb_test_3.setProgress(100,100)

        pie_test.setPies(mutableListOf(
                AnimPieCircleView.Pie(0xffff0000.toInt(),50,"dsf"),
                AnimPieCircleView.Pie(0xff00ff00.toInt(),120,"dsf"),
                AnimPieCircleView.Pie(0xff0000ff.toInt(),70,"dsf"),
                AnimPieCircleView.Pie(0xffff0000.toInt(),176,"dsf"),
                AnimPieCircleView.Pie(0xff00ff00.toInt(),240,"dsf"),
                AnimPieCircleView.Pie(0xff0000ff.toInt(),60,"dsf")
        ))
    }

    override fun requestData() {
    }

}
