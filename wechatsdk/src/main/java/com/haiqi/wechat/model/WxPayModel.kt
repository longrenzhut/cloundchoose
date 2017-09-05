package com.haiqi.wechat.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by zhutao on 2017/9/1.
 */


data class WxPayModel(
        var appid: String,
        val noncestr: String,
        val packageValue: String,
        val partnerid: String,
        val time: String,
        val sign: String,
        val prepayid: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(appid)
        writeString(noncestr)
        writeString(packageValue)
        writeString(partnerid)
        writeString(time)
        writeString(sign)
        writeString(prepayid)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WxPayModel> = object : Parcelable.Creator<WxPayModel> {
            override fun createFromParcel(source: Parcel): WxPayModel = WxPayModel(source)
            override fun newArray(size: Int): Array<WxPayModel?> = arrayOfNulls(size)
        }
    }
}