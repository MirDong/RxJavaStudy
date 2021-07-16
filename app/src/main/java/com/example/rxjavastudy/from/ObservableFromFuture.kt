package com.example.rxjavastudy.from

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import kotlin.concurrent.thread

fun fromFuture() {
    // 第二步： 创建一个FutureTask
    val call = TaskCallable()
    val future = FutureTask<String>(call)

    // 第三步：执行Callable
    Observable.fromFuture(future)
        .doOnSubscribe { future.run() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Constants.TAG, "onSubscribe: ")
            }

            override fun onNext(t: String) {
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

/**
 * 第一步， 创建一个Callable
 */
private class TaskCallable : Callable<String> {
    override fun call(): String {
        Log.d(Constants.TAG, "任务开始...")
        Thread.sleep(1000)
        Log.d(Constants.TAG, "任务结束...")
        return "TaskCallable"
    }

}