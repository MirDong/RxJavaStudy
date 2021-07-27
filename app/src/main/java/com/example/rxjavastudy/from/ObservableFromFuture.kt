package com.example.rxjavastudy.from

import android.util.Log
import com.example.rxjavastudy.constant.Constants
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.*
import kotlin.concurrent.thread

fun fromFuture(): Future<String>{
    // 第二步： 创建一个FutureTask
    val call = TaskCallable()
    val future: Future<String> = Executors.newSingleThreadExecutor().submit(call)
    // 第三步：执行Callable
    Observable.fromFuture(future)
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
    return future
}

/**
 * 第一步， 创建一个Callable
 */
private class TaskCallable : Callable<String> {
    override fun call(): String {
        Log.d(Constants.TAG, "任务开始...")
        Thread.sleep(2000)
        Log.d(Constants.TAG, "任务结束...")
        return "TaskCallable"
    }

}
fun fromFutureDelay(delayMillis: Long) {
    val call = TaskCallable()
    val future: Future<String> = Executors.newSingleThreadExecutor().submit(call)
    Observable.fromFuture(future, delayMillis, TimeUnit.MILLISECONDS, Schedulers.io())
        .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d(Constants.TAG, "onSubscribe: ")
            }

            override fun onNext(t: String) {
                Log.d(Constants.TAG, "onNext: value = $t, ${Thread.currentThread().name}")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Log.d(Constants.TAG, "onError: ${e.cause}")
            }

            override fun onComplete() {
                Log.d(Constants.TAG, "onComplete: ")
            }
        })
}

fun cancelTask() {
    val futureTask = fromFuture()
    Thread.sleep(500)
    if (futureTask.isDone) {
        Log.d(Constants.TAG," 任务已经完成")
    } else {
        Log.d(Constants.TAG," 任务正在进行")
        val cancel = futureTask.cancel(true)
        Log.d(Constants.TAG," 任务是否取消-->cancel = $cancel")
        Log.d(Constants.TAG," 任务是否取消-->isCancel = ${futureTask.isCancelled}")
    }
}