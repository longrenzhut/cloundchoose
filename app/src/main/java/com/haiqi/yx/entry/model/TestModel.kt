package com.haiqi.yx.entry.model

import android.os.Parcel
import android.os.Parcelable
import com.haiqi.base.utils.showToast

/**
 * Created by Administrator on 2017/9/1.
 */
class TestModel(val test: Int) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(test)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TestModel> = object : Parcelable.Creator<TestModel> {
            override fun createFromParcel(source: Parcel): TestModel = TestModel(source)
            override fun newArray(size: Int): Array<TestModel?> = arrayOfNulls(size)
        }
    }

    fun test(){
        showToast("成功了")
    }
}