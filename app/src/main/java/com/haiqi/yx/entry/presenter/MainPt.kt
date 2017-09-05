package com.haiqi.yx.entry.presenter

import com.haiqi.base.common.presenter.BasePt
import com.haiqi.base.http.Params
import com.haiqi.base.http.ReqCallBack
import com.haiqi.base.model.ShareModel
import org.json.JSONArray
import org.json.JSONObject


/**
 * Created by zhutao on 2017/8/3.
 */
class MainPt: BasePt<IMain>(){


    fun requestData(){
        val p = Params()
        setRequest("category/index",p,object : ReqCallBack<JSONArray>(){
            override fun OnSuccess(model: JSONArray?) {
                model?.let{
                    val value = it
                }
            }

        })
    }

}