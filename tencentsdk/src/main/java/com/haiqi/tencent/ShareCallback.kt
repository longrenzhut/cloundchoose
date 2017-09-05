package com.haiqi.tencent

import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError

/**
 * Created by zhutao on 2017/8/31.
 * qq分享的回调
 * type 0 表示分享  1 表示登陆
 */

class ShareCallback(val onComplete: ((Any?)-> Unit)? = null,
                    val onError: ((UiError?)-> Unit)? = null,
                    val type: Int = 0): IUiListener{


    override fun onComplete(p0: Any?) {
        onComplete?.let {
            it(p0)
        }
    }

    override fun onCancel() {
//        "取消分享"
    }

    override fun onError(p0: UiError?) {
        onError?.let {
            it(p0)
        }
    }

}
