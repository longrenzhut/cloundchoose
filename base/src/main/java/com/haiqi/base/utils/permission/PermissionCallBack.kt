package com.haiqi.base.utils.permission

import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener

/**
 * Created by Administrator on 2017/9/12.
 */
class PermissionCallBack(val code: Int,
                         val onSucceed: (()-> Unit),
                         val onFailed: (()-> Unit)? = null): PermissionListener {


    override fun onSucceed(requestCode: Int, grantPermissions: MutableList<String>) {
        if(code == requestCode)
            onSucceed()
    }

    override fun onFailed(requestCode: Int, deniedPermissions: MutableList<String>) {
        if(code == requestCode) {
            onFailed?.let {
                it()
            } ?:
                    0
        }
    }

}