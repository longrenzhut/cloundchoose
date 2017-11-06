package com.haiqi.base.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper

/**
 * Created by zhutao on 2017/5/27.
 */
abstract class BaseAdapter<T>: DelegateAdapter.Adapter<BaseViewHolder>(){



    public interface OnItemClickListener<T> {
        fun onItemClick(itemView: View, pos: Int, model: T)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(itemView: View, pos: Int, model: T)
    }

    private var mClickListener: OnItemClickListener<T>? = null
    private var mLongClickListener: OnItemLongClickListener<T>? = null

    open fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>) {
        mLongClickListener = listener
    }



    private val datas by lazy { mutableListOf<T>() }


    fun getData(): MutableList<T> = datas

    fun getItem(position: Int): T{
        return datas.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {

        val holder = BaseViewHolder(
                LayoutInflater.from(parent?.context).inflate(inflaterItemLayout(viewType), parent, false))

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {

        holder!!.itemView.setOnClickListener(getOnClickListener(position))
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position))

        bindData(holder,position,datas[position])

    }

    fun notifyItems(list: MutableList<T>?){
        val size = itemCount
        list?.let{
            datas.addAll(it);
            notifyItemChanged(size, it.size)
        }
    }


    fun clear(){
        datas.clear()
    }

    fun remove(item: T?){
        item?.let {
            datas.remove(it)
        }
    }

    fun remove(i: Int){
        datas.removeAt(i)
    }

    fun add(item: T?){
        item?.let {
            datas.add(it)
        }
    }

    fun addAll(list: MutableList<T>?){
        list?.let {
            datas.addAll(it)
        }
    }

    fun notifyItem(item: T?){
        item?.let {
            datas.add(it);
        }
        notifyItemChanged(itemCount - 1)
    }

    fun notifyRemoveItem(index: Int){
        remove(index)
        notifyItemRemoved(index)
    }

    fun notifyRemoveItem(item: T?){
        val index = datas.indexOf(item)
        remove(item)
        notifyItemRemoved(index)
    }


    override fun getItemCount(): Int {

        return datas.size
    }


    abstract fun inflaterItemLayout(viewType: Int): Int

    abstract fun bindData(holder: BaseViewHolder, position: Int, model: T)


    abstract fun onItemClickListener(itemView: View, pos: Int, model: T)

    open fun onItemLongClickListener(itemView: View, pos: Int, model: T) {

    }

    fun getOnClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener { v ->
            if(null != mClickListener)
                mClickListener?.onItemClick(v, position, datas[position])
            else
                onItemClickListener(v, position,datas[position])
        }
    }

    fun getOnLongClickListener(position: Int): View.OnLongClickListener {
        return View.OnLongClickListener { v ->
            if(mLongClickListener != null) {
                mLongClickListener?.onItemLongClick(v, position, datas[position])
            }
            else{
                onItemLongClickListener(v, position, datas[position])
            }
            true
        }
    }

    override fun onCreateLayoutHelper(): LayoutHelper? {
        return LinearLayoutHelper()
    }
}
