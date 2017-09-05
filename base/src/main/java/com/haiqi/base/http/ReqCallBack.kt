package com.haiqi.base.http

import com.google.gson.Gson
import com.haiqi.base.common.UILoadLayout
import com.haiqi.base.utils.showToast
import com.haiqi.base.widget.dialog.LoadingDialog
import org.json.JSONArray
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by zhutao on 2017/5/27.
 *
 * 请求数据封装
 */
abstract class ReqCallBack<T>: IReqCallBack<T>{


    private var loadView: LoadingDialog? = null
    private var mUiLoadLayout: UILoadLayout? = null

    fun setLoading(load: LoadingDialog?){
        load?.let {
            val weak = WeakReference<LoadingDialog>(it)
            loadView = weak.get()
        }
    }


    fun setUILayout(uiload: UILoadLayout?){
        uiload?.let {
            val weak = WeakReference<UILoadLayout>(it)
            mUiLoadLayout = weak.get()
        }
    }

    private var modelType: Type? = null


    //    val result: BaseResult<String> = Gson().fromJson(str,
    //                    object : TypeToken<BaseResult<T>>(){}.type)

    fun setType(model: Type): ReqCallBack<T> {
        this.modelType = model
        return this
    }


    fun OnNext(str: String) {
        if (modelType == null) {
            //以下代码是通过泛型解析实际参数,泛型必须传
            val genType = javaClass.getGenericSuperclass();
            val params = (genType as ParameterizedType).getActualTypeArguments();
            modelType = params[0];
        }
        if(modelType == null){
            showToast("模型没有序列化")
        }
        else if(modelType ==  Unit::class.java){
            OnSuccess(null)
        }
        else if(modelType == JSONObject::class.java){
            val json = JSONObject(str)
            OnSuccess(json as T)
        }
        else if(modelType == JSONArray::class.java){
            val json = JSONArray(str)
            OnSuccess(json as T)
        }
        else if(modelType == String::class.java){
            OnSuccess(str as T)
        }
        else {
            val result: T = Gson().fromJson(str, modelType)
            OnSuccess(result)
        }
        mUiLoadLayout?.let {
            it.loadok()
        }
    }

    override fun OnFailed(retcode: Int) {
        //请求数据成功
        mUiLoadLayout?.let {
            it.loadok()
        }
    }

    override fun onError(e: Throwable?) {
        mUiLoadLayout?.let {
            it.loadFailed()
        }
    }

    override fun onCompleted() {
        loadView?.let{
            it.dismiss()
        }
    }

}
