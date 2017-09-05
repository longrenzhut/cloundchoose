package com.haiqi.base.widget.dialog

import android.content.Context
import com.haiqi.base.R
import com.haiqi.base.common.dialog.BaseDialog

/**
 * Created by zhtuao on 2017/8/2.
 * 加载动画
 */
class LoadingDialog(ctx: Context): BaseDialog(ctx){

    init {
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
    }

}