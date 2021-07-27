package com.example.rxjavastudy.defer

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun defer() {
    val observable = Observable.defer {
        Log.d(Constants.TAG, "defer ")
        Observable.just("one")
    }
    Log.d(Constants.TAG, "observer ")
    observable.subscribe(object : Observer<String> {
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