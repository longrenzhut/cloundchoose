package com.haiqi.base.utils.permission

import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RationaleListener

/**
 * Created by Administrator on 2017/9/12.
 *
 */
class RationalePrompt : RationaleListener{


    override fun showRequestPermissionRationale(requestCode: Int, rationale: Rationale?) {
        rationale?.resume()
    }

}