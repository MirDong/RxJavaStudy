package com.example.rxjavastudy.create

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun just() {
    val disposable: Disposable = Observable.just("one", "two","three", "four")
        .subscribeOn(Schedulers.io())
        .subscribe {
            Log.d(Constants.TAG, "just: $it")
        }
    /*if (!disposable.isDisposed) {
        disposable.dispose()
    }*/
}

fun justNumber() {
    Observable.just(1, 2, 3, 4)
        .map {
            when (it) {
                1 -> "One"
                2 -> "Two"
                3 -> "Three"
                4 -> "Four"
                else -> "other"
            }
        }.subscribe {
            Log.d(Constants.TAG, "justNumber: $it")
        }
}