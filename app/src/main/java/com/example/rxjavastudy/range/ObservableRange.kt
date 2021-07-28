package com.example.rxjavastudy.range

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun range() {
    Observable.range(1, 5)
        .subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Constants.TAG, "onSubscribe: ")
            }

            override fun onNext(t: Int) {
                Log.d(Constants.TAG, "onNext: value = $t, ${Thread.currentThread().name}")
            }

            override fun onError(e: Throwable) {
                Log.d(Constants.TAG, "onError: ")
            }

            override fun onComplete() {
                Log.d(Constants.TAG, "onComplete: ")
            }
        })
}