package com.haiqi.yx.entry

import android.os.Environment
import com.alibaba.android.arouter.launcher.ARouter
import com.haiqi.base.common.activity.BaseAct
import com.haiqi.base.rx.rxsql.DBManager
import com.haiqi.base.rx.rxbinding.RxClick
import com.haiqi.base.rx.rxsql.RxSql
import com.haiqi.base.utils.glide.GlideHelper
import com.haiqi.yx.R.layout.act_main
import com.haiqi.yx.entry.presenter.IMain
import com.haiqi.yx.entry.presenter.MainPt
import kotlinx.android.synthetic.main.act_main.*
import com.haiqi.base.rx.rxsql.RxSqlQueryHelper
import com.haiqi.base.utils.showToast
import com.haiqi.yx.entry.db.ProviceImpl
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RationaleListener
import io.reactivex.Observable
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.haiqi.base.service.ServiceMananger
import com.haiqi.base.service.model.VersionModel
import com.haiqi.base.utils.Mylogger
import com.haiqi.base.utils.permission.PermissionHelper
import com.haiqi.yx.entry.model.TestModel
import com.yanzhenjie.permission.PermissionNo
import com.yanzhenjie.permission.PermissionYes
import com.yanzhenjie.permission.PermissionListener
import java.util.concurrent.TimeUnit


/**
 * Created by zhutao on 2017/8/3.
 */
class MainAct: BaseAct<MainPt, IMain>(), IMain {

    override fun getPresenter(): MainPt {
        setSwipeBackNoEnable()
        val pt = MainPt()
        pt.attach(this)
        return pt
    }



    override fun getLayoutId(): Int =
            act_main

    override fun initView() {
        PermissionHelper.apply(this,100,Permission.STORAGE)

        setCommonHeader("云选")
        setUILoadLayout()
        requestData()

//        loop@ for (i in 1..100) {
//            for (j in 1..100) {
//                if (j == 1) break@loop
//            }
//        }
//        val a = intArrayOf(1,2)
//        a.forEach(fun(value: Int) {
//            if (value == 0) return
//            print(value)
//        })

    }

    override fun requestData() {
        mPrsenter.requestData()
        mPrsenter.getTest()
        val version = VersionModel("http://iwant-u.cn/document/","highup20170505.apk",2017061201)
        ServiceMananger.updataVersion(this,version)
//        val list = listOf(1,2)
//        Observable.from(list).filter(object : Func1<Int,Boolean>{
//            override fun call(t: Int?): Boolean? {
//                return false
//            }
//
//        }).subscribe {  }
        tv_input.RxClick(this){
//            this@MainAct.startActivity(Intent(this@MainAct,TestMDAct::class.java))
            ARouter.getInstance().build("/tent/sss").navigation(this)
//            ARouter.getInstance().build("/tent/testmd").navigation(this)
//            Router(this@MainAct).go<TestAct>()
        }
        val url = "http://haiqihuocang.oss-cn-hangzhou.aliyuncs.com/product_img/20170712149986128464639794.jpg"
        GlideHelper.instance().load(iv_icon,url,40,40)
        glide_test.setUrl(url)
//        glide_s.setUrl(url)

//        val testmodel = Class.forName("com.haiqi.yx.entry.model.TestModel")
//        val textMol = testmodel.newInstance()
//        textMol.test()
        val dpath = Environment.getExternalStorageDirectory().toString() + "/highup/"
        DBManager.get().setPath(dpath)
        RxSql.execute(this,RxSqlQueryHelper(ProviceImpl()).setOpen(true)){
            showToast("我收到了")
            glide_s.setUrl(url)
        }

//        Observable.interval(100, TimeUnit.MILLISECONDS)
//                .subscribe {
//                    var i = 0
//                    i++
//                    Mylogger.d("" + i)
//                }
//        Observable.create(RxSqlQueryHelper(ProviceImpl()).setOpen(true))
//                .subscribe({},{},{
//                    showToast("我收到了")
//                    glide_s.setUrl(url)
//                },{})

//        observable.subscribe(observer)

    }


}