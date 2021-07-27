package com.example.rxjavastudy.never

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

fun never() {
    Observable.never<Any>().subscribe(object : Observer<Any> {
        override fun onSubscribe(d: Disposable) {
            Log.d(Constants.TAG, "onSubscribe: ")
        }

        override fun onNext(t: Any) {
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