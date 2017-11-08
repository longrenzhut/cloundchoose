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



    interface OnItemClickListener<T> {
        fun onItemClick(itemView: View, pos: Int, model: T?)
    }

    interface OnItemLongClickListener<T> {
        fun onItemLongClick(itemView: View, pos: Int, model: T?)
    }

    private var mClickListener: OnItemClickListener<T>? = null
    private var mLongClickListener: OnItemLongClickListener<T>? = null

    open fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        mClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<T>) {
        mLongClickListener = listener
    }



    private val datas by lazy(LazyThreadSafetyMode.NONE) { mutableListOf<T?>() }


    fun getData(): MutableList<T?> = datas

    fun getItem(position: Int): T?{
        return datas[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {

        return BaseViewHolder(
                LayoutInflater.from(parent?.context).inflate(inflaterItemLayout(viewType), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {

        holder?.let{
            with(it){
                itemView.setOnClickListener(getOnClickListener(position))
                itemView.setOnLongClickListener(getOnLongClickListener(position))
            }
        }
        holder?.let{
            bindData(it,position,datas[position])
        }
    }

    fun notifyItems(list: MutableList<T>?){
        val size = itemCount
        list?.let{
            datas.addAll(it)
            notifyItemChanged(size, it.size)
        }
    }


    fun clear(){
        datas.clear()
    }

    fun remove(item: T?){
        datas.remove(item)
    }

    fun remove(i: Int){
        datas.removeAt(i)
    }

    fun add(item: T?){
        datas.add(item)
    }

    fun addAll(list: MutableList<T>?){
        list?.let {
            datas.addAll(it)
        }
    }

    fun notifyItem(item: T?){
        item?.let {
            datas.add(it)
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

    fun notifyRemoveItems(list: MutableList<T?>?){
        list?.let {
            it.forEach {
                remove(it)
            }
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int {

        return datas.size
    }


    abstract fun inflaterItemLayout(viewType: Int): Int

    abstract fun bindData(holder: BaseViewHolder, position: Int, model: T?)


    abstract fun onItemClickListener(itemView: View, pos: Int, model: T?)

    open fun onItemLongClickListener(itemView: View, pos: Int, model: T?) {

    }

    private fun getOnClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener { v ->
            mClickListener?.let{
                it.onItemClick(v, position, datas[position])
            } ?:
                    onItemClickListener(v, position,datas[position])
        }
    }

    private fun getOnLongClickListener(position: Int): View.OnLongClickListener {
        return View.OnLongClickListener {v->

            mLongClickListener?.let{
                it.onItemLongClick(v, position, datas[position])
            }?:
                    onItemLongClickListener(v, position, datas[position])
            true
        }
    }

    override fun onCreateLayoutHelper(): LayoutHelper? {
        return LinearLayoutHelper()
    }
}
