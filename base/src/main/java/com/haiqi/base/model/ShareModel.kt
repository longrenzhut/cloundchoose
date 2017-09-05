package com.haiqi.base.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by zhutao on 2017/8/31.
 */
data class ShareModel(val title: String,
                      val content: String,
                      val linkUrl: String,
                      val imgurl: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(content)
        writeString(linkUrl)
        writeString(imgurl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ShareModel> = object : Parcelable.Creator<ShareModel> {
            override fun createFromParcel(source: Parcel): ShareModel = ShareModel(source)
            override fun newArray(size: Int): Array<ShareModel?> = arrayOfNulls(size)
        }
    }
}