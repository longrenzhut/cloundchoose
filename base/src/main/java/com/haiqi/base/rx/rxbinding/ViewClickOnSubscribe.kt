package com.haiqi.base.rx.rxbinding

import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/8/24.
 */
class ViewClickOnSubscribe(val view: View): ObservableOnSubscribe<Int> {
    override fun subscribe(e: ObservableEmitter<Int>) {
        view.setOnClickListener{
            if(!e.isDisposed){
                e.onNext(1)
            }
        }

        e.setDisposable(object : Disposable{
            override fun isDisposed(): Boolean {

                return true
            }

            override fun dispose() {
                view.setOnClickListener(null)
            }

        })
    }

}