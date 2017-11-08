package com.haiqi.base.common.adapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.haiqi.base.R
import com.haiqi.base.utils.setVisible

/**
 * Created by Administrator on 2017/7/10.
 */
class LoadMoreAdapter: BaseAdapter<Int>(){

    var state = 1

    init {
        add(1)
    }

    override fun inflaterItemLayout(viewType: Int): Int {
        return R.layout.adapter_load_more
    }

    override fun bindData(holder: BaseViewHolder, position: Int, model: Int?) {
       val mPbLoading = holder.getView<ProgressBar>(R.id.pb_loading)
       val mTvLoading = holder.getView<TextView>(R.id.tv_loading)

        when(state){
            1->{
                mPbLoading.setVisible(1)
                mTvLoading.text = ""
            }
            2->{
                mPbLoading.setVisible(-1)
                mTvLoading.text = "网络有点问题,点击重新加载"
            }
            else->{
                mPbLoading.setVisible(-1)
                mTvLoading.text = "已全部加载完毕"
            }

        }
    }

    override fun onItemClickListener(itemView: View, pos: Int, model: Int?) {
    }

}