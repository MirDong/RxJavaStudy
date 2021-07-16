package com.example.rxjavastudy.from

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun fromIterable() {
    val events: List<String> = listOf("4", "5", "6")
    Observable.fromIterable(events)
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