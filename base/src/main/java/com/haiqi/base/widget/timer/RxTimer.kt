package com.haiqi.base.widget.timer

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2017/9/7.
 * Rx 定时器
 */
class RxTimer{


    fun timer(time: Long){
        Observable.interval(time
        ,TimeUnit.SECONDS)
                .subscribe {

                }
    }
}