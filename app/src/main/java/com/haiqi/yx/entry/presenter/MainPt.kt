package com.haiqi.yx.entry.presenter

import com.haiqi.base.common.presenter.BasePt
import com.haiqi.base.http.*
import com.haiqi.base.model.ShareModel
import com.haiqi.base.utils.ObservableSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject


/**
 * Created by zhutao on 2017/8/3.
 */
class MainPt: BasePt<IMain>(){


    fun requestData(){
        val p = Params()
        post("category/index",p,object : ReqCallBack<JSONArray>(){
            override fun OnSuccess(model: JSONArray?) {
                model?.let{
                    val value = it
                }
            }

        })
    }

    fun getTest(){
        val p = Params(true)
        p.put("inviteCode","yunxuan")

        request(HttpProvider2.createRetrofit("http://182.92.204.193:8081/v1/")
                .create(BaseService::class.java)
                .get("home",p.getParams()),
                ReqSubscriber(object : ReqCallBack<JSONObject>(){
                    override fun OnSuccess(model: JSONObject?) {
                        model?.let{
                            val value = it
                        }
                    }

                })
                )
    }


}