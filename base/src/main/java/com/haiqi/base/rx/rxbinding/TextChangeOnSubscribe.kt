package com.haiqi.base.rx.rxbinding

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable

/**
 * Created by zhutao on 2017/9/19.
 * 监听EditTextChange
 */

class TextChangeOnSubscribe(val view: TextView): ObservableOnSubscribe<String> {


    override fun subscribe(e: ObservableEmitter<String>) {
        view.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                e.onNext(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        e.setDisposable(object : Disposable {
            override fun isDisposed(): Boolean {

                return true
            }

            override fun dispose() {
                view.addTextChangedListener(null)
            }

        })
    }

}
