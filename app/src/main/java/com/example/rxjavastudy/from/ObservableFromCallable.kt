package com.example.rxjavastudy.from

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable

fun fromCallable() {
    Observable.fromCallable(object : Callable<String> {
        override fun call(): String {
            // 其他操作

            return "Callable"
        }

    })
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Constants.TAG, "onSubscribe: ")
            }

            override fun onNext(t: String) {
                Log.d(Constants.TAG, "onNext: value = $t")
            }

            override fun onError(e: Throwable) {
                Log.d(Constants.TAG, "onError: ")
            }

            override fun onComplete() {
                Log.d(Constants.TAG, "onComplete: ")
            }
        })
}