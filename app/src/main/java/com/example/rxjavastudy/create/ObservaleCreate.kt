package com.example.rxjavastudy.create

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

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
