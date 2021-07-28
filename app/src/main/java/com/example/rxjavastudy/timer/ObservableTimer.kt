package com.example.rxjavastudy.timer

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun timer() {
    Observable.timer(2, TimeUnit.SECONDS, Schedulers.io())
        .subscribe(object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Constants.TAG, "onSubscribe: ")
            }

            override fun onNext(t: Long) {
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