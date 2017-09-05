package com.haiqi.base.rx.rxbus

import io.reactivex.Observable
import io.reactivex.functions.BooleanSupplier
import android.R.attr.delay
import java.util.concurrent.TimeUnit


/**
 * Created by Administrator on 2017/9/1.
 */
object RxjavaTest{

    fun alay(){
//        Observable  被观察者 消息发射者
//        subscribeOn  消息发送的路径
//        observeOn    观察的路径
//        Observer    观察者，获取消息

//      Observer --> observeOn -->  subscribe 订阅  ---> Observable
//        Observable --> subscribeOn 发送 --> Observer
    }


    /**
     * 循环3次
     */
    fun repeat(){
        Observable.just("Love", "For", "You!")
                .repeat(3)//重复三次
                .subscribe{
                    s -> System.out.println(s)
                }
    }


    /**
     * 无线循环
     * getAsBoolean 如果返回 true则不repeat false则repeat.主要用于动态控制
     */
    fun repeatUntil(){
        Observable.just("Love", "For", "You!")
                .repeatUntil(object : BooleanSupplier{
                    override fun getAsBoolean(): Boolean {

                        return true
                    }
                }).subscribe{
            s -> System.out.println(s)
        }
    }

    /**
     * 延迟发送
     */
    fun delay(){
        Observable.range(0, 3)
                .delay(1400, TimeUnit.MILLISECONDS)
                .subscribe {
                    o -> println("===>" + o + "\t") }
    }

    fun delaySubscription(){

        Observable.just(1)
                .delaySubscription(2000, TimeUnit.MILLISECONDS)
                .subscribe(
                        { o -> println("===>" + o + "\t") },
                        { throwable -> println("===>throwable") },
                        { println("===>complete") })
                { disposable -> println("===>订阅") }

    }

    /**
     * 注册一个回调，它产生的Observable每发射一项数据就会调用它一次
     * doOnEach 执行以及 回调一次
     */
    fun doOnEach(){
        Observable.range(0,3)
                .doOnEach {
                }.subscribe {

        }
    }

    /**
     * 注类似doOnEach 不是接受一个 Notification 参数，而是接受发射的数据项。
     */
    fun doOnNext(){
        Observable.range(0,3)
                .doOnNext {

                }
                .subscribe {  }
    }

    /**
     * 注册一个动作，在观察者订阅时使用
     */
    fun doOnSubscribe(){

        Observable.range(0, 3)
                .doOnSubscribe{
                    System.out.print("开始订阅")}
                .subscribe{
                    o -> System.out.print("===>" + o + "\t")
                }
    }

    /**
     * forEach 方法是简化版的 subscribe ，你同样可以传递一到三个函数给它，解释和传递给 subscribe 时一样.
        不同的是，你无法使用 forEach 返回的对象取消订阅。也没办法传递一个可以用于取消订阅 的参数
        range(12,14) 范围 从12 到14  12 13
     */
    fun forEach(){
        Observable.range(0,3)
                .doOnEach {
                    //处理
                }
    }

    /**
     * 它将一个发射T类型数据的Observable转换为一个发射类型 为Timestamped
     * 的数据的Observable，每一项都包含数据的原始发射时间
     * take(3) 从0 到3
     */
    fun Timestamp(){
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(3)
                .timestamp()
                .subscribe{

                }
//        3次每隔100毫秒发送当前时间戳
//        ===>Timed[time=1501224256554, unit=MILLISECONDS, value=0]
//        ===>Timed[time=1501224256651, unit=MILLISECONDS, value=1]
//        ===>Timed[time=1501224256751, unit=MILLISECONDS, value=2]
    }

    /**
     * 一个发射数据的Observable转换为发射那些
     * 数据发射时间间隔的Observable
     */
    fun timeInterval(){
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(3)
                //   把发送的数据 转化为  相邻发送数据的时间间隔实体
                .timeInterval()
                .subscribe {

                }
    }


    /**
     * 过了一个指定的时长仍没有发射数据(
     * 不是仅仅考虑第一个)，它会发一个错误
     */
    fun timeout(){
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .timeout(50, TimeUnit.MILLISECONDS)
                .subscribe(
                        {
                          // sucess
                        },
                        {
                           //onErrer
                        },
                        {
                            //OnComplete
                        },
                        {
                            //start
                        }
                )
    }

    /**
     * 对Observable发射的每一项数据应用一个函数，
     * 执行变换操作,就是方形过渡到圆形
     * just(1, 2) //发送 真实的数组 1,2
     */
    fun map(){
        Observable.just(1, 2)
                .map<Any> {
                    integer -> "This is result " + integer
                }
                .subscribe {
                    s -> println(s)
                }
    }
}