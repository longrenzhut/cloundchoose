package com.haiqi.base.rx.rxbus

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

/**
 * Created by zhutao on 2017/8/23.
 * 通信
 */
class RxBus2{

    companion object{

        private val rxBus2 by lazy {
            RxBus2()
        }

        fun instance(): RxBus2 = rxBus2

    }

     val mBus by lazy {
        PublishProcessor.create<Any>().toSerialized()
    }


    fun post(code: Int,clz: Any){
        mBus.onNext(RxMessage(code,clz))
    }

    fun <T> post(t: T){
        mBus.onNext(t)
    }


    fun toFlowable(code: Int): Flowable<RxMessage>{
        return  mBus.ofType(RxMessage::class.java)
                .filter{
                    it.code == code
                }

    }

   inline fun <reified T> toFlowable(): Flowable<T> {
       return mBus.ofType(T::class.java)
               .onBackpressureBuffer()

   }
}