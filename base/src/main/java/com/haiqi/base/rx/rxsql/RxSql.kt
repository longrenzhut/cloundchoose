package com.haiqi.base.rx.rxsql

import com.haiqi.base.common.activity.AbsAct
import com.haiqi.base.utils.ObservableSet
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by zhuao on 2017/9/12.
 */
object RxSql{

    fun <T> execute(act: AbsAct,source: ObservableOnSubscribe<T>
                    ,result: ((T) -> Unit),onComplete:(() -> Unit)){
        Observable.create(source)
                .ObservableSet()
                .compose(act.bindUntilEvent<T>(ActivityEvent.DESTROY))
                .subscribe(result,{},
                        { onComplete},
                        {})
    }

    fun <T> execute(act: AbsAct,source: ObservableOnSubscribe<T>
                    ,result: ((T) -> Unit)){
        Observable.create(source)
                .ObservableSet()
                .compose(act.bindUntilEvent<T>(ActivityEvent.DESTROY))
                .subscribe(result,{},
                        { },
                        {})
    }

}