package com.haiqi.base.common.adapter

import android.support.v7.util.DiffUtil

/**
 * Created by Administrator on 2017/8/22.
 */
class MyDiffCallback: DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        throw UnsupportedOperationException()
    }

    override fun getOldListSize(): Int {
        throw UnsupportedOperationException()
    }

    override fun getNewListSize(): Int {
        throw UnsupportedOperationException()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        throw UnsupportedOperationException()
    }
//
//    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldDatas, newDatas), true);
//    diffResult.dispatchUpdatesTo(mAdapter);

}