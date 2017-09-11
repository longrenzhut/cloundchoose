package com.haiqi.yx.entry

import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.common.activity.BaseAct
import com.haiqi.base.rx.rxdb.DBManager
import com.haiqi.base.rx.rxbinding.RxClick
import com.haiqi.base.utils.glide.GlideHelper
import com.haiqi.yx.R.layout.act_main
import com.haiqi.yx.entry.presenter.IMain
import com.haiqi.yx.entry.presenter.MainPt
import kotlinx.android.synthetic.main.act_main.*
import com.haiqi.base.rx.rxdb.RxSqlQueryHelper
import com.haiqi.base.utils.showToast
import com.haiqi.yx.entry.db.AddrModel
import com.haiqi.yx.entry.db.ProviceImpl
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


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

        DBManager.get().init(this)

        val observer =  object : Observer<List<AddrModel>> {
            override fun onComplete() {
            }

            override fun onNext(t: List<AddrModel>) {
                showToast("fsdf")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

        }

        Observable.create(RxSqlQueryHelper(ProviceImpl()))
                .subscribe(observer)

//        observable.subscribe(observer)

    }


}