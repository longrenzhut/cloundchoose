package com.haiqi.base.utils.permission

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.yanzhenjie.permission.*
import kotlin.collections.ArrayList

/**
 * Created by zhutao on 2017/9/12.
 * 兼容android6.0权限管理器
 */

object PermissionHelper{

    fun apply(ctx: Context,code: Int,
              vararg p: Array<String>){

        val list = ArrayList<String>()
       for(i in p.indices){
           p[i].map {
               list.add(it)
           }
       }
        if(!AndPermission.hasPermission(ctx,list)){
            AndPermission.with(ctx)
                    .requestCode(code)
                    .permission(*p)
                    .rationale(RationalePrompt())
                    .callback(PermissionCallBack(code,{}))
                    .start()
        }
    }

    /**
     * 提示
     */
//    private fun showPrompt(ctx: Context) {
//        val builder = AlertDialog.Builder(ctx)
//        builder.setTitle("帮助")
//        builder.setMessage(R.string.string_help_text)
//
//        // 拒绝, 退出应用
//        builder.setNegativeButton(R.string.quit,
//                DialogInterface.OnClickListener {
//                    dialog, which -> finish() })
//
//        builder.setPositiveButton(R.string.settings,
//                DialogInterface.OnClickListener {
//                    dialog, which -> startAppSettings() })
//
//        builder.setCancelable(false)
//
//        builder.show()
//    }

    /**
     * 启动应用的设置
     */
     fun startAppSettings(ctx: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + ctx.packageName)
        ctx.startActivity(intent)
    }
}
