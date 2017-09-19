package com.haiqi.base.rx.rxbinding

/**
 * Created by Administrator on 2017/8/10.
 */
import android.view.View
import android.widget.TextView
import com.haiqi.base.common.activity.AbsAct
import com.haiqi.base.common.fragment.DelegateFra
import com.haiqi.base.utils.ObservableSet
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//import com.jakewharton.rxbinding.view.RxView
//import com.jakewharton.rxbinding.widget.RxTextView
//import com.trello.rxlifecycle.ActivityEvent
//import com.trello.rxlifecycle.FragmentEvent
//import rx.Observable
//import rx.android.schedulers.AndroidSchedulers
//import rx.schedulers.Schedulers
//import java.util.concurrent.TimeUnit
//
//
///**
// * 点击事件
// */
//fun <T> Observable<T>.ObservableSet(): Observable<T>{
//    subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    return this
//}
//
//inline fun View.Rxclick(act: AbsAct): Observable<Void>{
//    return  RxView.clicks(this)
//            .ObservableSet()
//            .throttleFirst(500, TimeUnit.MILLISECONDS)
//            .compose(act.bindUntilEvent<Void>(ActivityEvent.DESTROY))
//}
//
//inline fun View.Rxclick(fra: DelegateFra): Observable<Void>{
//    return  RxView.clicks(this)
//            .ObservableSet()
//            .throttleFirst(500, TimeUnit.MILLISECONDS)
//            .compose(fra.bindUntilEvent<Void>(FragmentEvent.DESTROY))
//}
//
//inline fun TextView.RxtextChange(act: AbsAct): Observable<CharSequence>{
//    return RxTextView.textChanges(this)
//            .ObservableSet()
//            .compose(act.bindUntilEvent<CharSequence>(ActivityEvent.DESTROY))
//}
//
//inline fun TextView.RxtextChange(fra: DelegateFra): Observable<CharSequence>{
//    return RxTextView.textChanges(this)
//            .ObservableSet()
//            .compose(fra.bindUntilEvent<CharSequence>(FragmentEvent.DESTROY))
//}

/*RxTextView.textChanges(searchTextView)
.filter(new Func1<String, Boolean> (){
    @Override
    public Boolean call(String s) {
        return s.length() > 2;
    }
})
.debounce(100, TimeUnit.MILLISECONDS)
.switchMap(new Func1<String, Observable<List<Result>>>() {
    makeApiCall(s);
})
.subscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
.subscribe(*//* attach observer *//*);*/

/*
RxCompoundButton.checkedChanges(checkBox2)
.subscribe(new Action1<Boolean>() {
    @Override
    public void call(Boolean aBoolean) {
        btn_login.setClickable(aBoolean);
        btn_login.setBackgroundResource(aBoolean ? R.color.can_login : R.color.not_login);
    }
});*/



inline fun View?.RxClick(act: AbsAct, crossinline onNext:()-> Unit){
    this?.let{
        Observable.create(ViewClickOnSubscribe(it))
                .ObservableSet()
                .compose(act.bindUntilEvent<Int>(ActivityEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe{
                    onNext()
                }
    }
}

inline fun View?.RxClick(fra: DelegateFra, crossinline onNext:()-> Unit){
    this?.let{
        Observable.create(ViewClickOnSubscribe(it))
                .ObservableSet()
                .compose(fra.bindUntilEvent<Int>(FragmentEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe{
                    onNext()
                }
    }
}

inline fun TextView?.RxTextChange(ui: Any, crossinline onNext:()-> Unit){
    this?.let{
        Observable.create(TextChangeOnSubscribe(it))
                .ObservableSet()
                .compose(
                        if(ui is AbsAct)
                            ui.bindUntilEvent<String>(ActivityEvent.DESTROY)
                        else
                            (ui as DelegateFra).bindUntilEvent<String>(FragmentEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe{
                    onNext()
                }
    }
}


