package com.example.rxjavastudy.create

import android.annotation.SuppressLint
import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

fun create() {
    Observable.create(ObservableOnSubscribe<String> { emitter ->
        emitter.onNext("1")
        emitter.onNext("2")
        emitter.onComplete()
    }).subscribe(object : Observer<String> {
        override fun onComplete() {
            Log.d(Constants.TAG, "onComplete: ")
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: String) {
            Log.d(Constants.TAG, "onNext: $t")
        }

        override fun onError(e: Throwable) {
            Log.d(Constants.TAG, "onError: ${e.message}")
        }

    })
}

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
    Observable.just(1 ,2, 3, 4)
        .map {
            when(it) {
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