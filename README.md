# RxJavaStudy

## 一、 概念

- RxJava是ReactiveX在Java开源的实现， 用于使用可观察序列来进行异步编程和基于事件的程序的库。 核心点在于异步编程，链式调用， 事件序列。

- 引入RxJava

```java
implementation "io.reactivex.rxjava2:rxjava:2.2.3"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
```

- 重要概念：

  观察者(Observer), 被观察者(Observable), 事件序列。 事件序列完全由被观察者自己控制，在需要时被观察者通知观察者，这就需要观察者与被观察者建立订阅关系。当被观察者发生变化时，观察者就可以立即接收到被观察者变化。

- 观察者事件回调：

  onSubscribe: 建立订阅时回调， 常用于执行loading动画

  onNext:  发送事件时观察者回调该方法表示接收发送的事件序列

  onError: 发送事件时观察者回调表示发送事件序列异常，将不再允许发送事件

  onComplete: 发送事件时观察者回调表示发送事件序列发送完毕， 允许发送事件

  > 注意：
  >
  > 1. onError 调用后不允许继续发送事件，onComplete调用后允许继续发送事件，无论是否可以继续发送，观察者都不会接收消息。
  > 2. onError  和 onComplete互斥只允许调用其中一个，在onComplete之后调用onError程序必然崩溃。相反， onError之后调用onComplete则不会崩溃，原因是onError之后不允许发送事件。
  > 3. 观察者与被观察者一旦建立订阅关系，onSubscribe必然会被调用，而onNext， onError， onComplete回调完全由被观察者决定是否触发。

  ## 二、 基本实现

  1. 创建观察者Observer， 决定事件发生时该如何处理

     ```java
     // 观察者
     Observer<String> observer = new Observer<String> {
         @Override
         public void onSubscribe(Disposable d) {
             //解除订阅
             Log.i(TAG, "onSubscribe--->");
         }
     
         @Override
         public void onNext(String s) {
             //发送事件时观察者回调
             Log.i(TAG, "onNext--->"+s);
         }
     
         @Override
         public void onError(Throwable e) {
             //发送事件时观察者回调(事件序列发生异常)
             Log.i(TAG, "onError--->");
         }
     
         @Override
         public void onComplete() {
             //发送事件时观察者回调(事件序列发送完毕)
             Log.i(TAG, "onComplete--->");
         }
     };
     ```

     

  2. 创建被观察者Observable， 被观察者决定什么时候触发事件及触发哪种事件

     ```java
     // 被观察者
     Observable<String> obserable = Observable.create(new ObservableOnSubscribe<String>() {
         @Override
         public void subscribe(ObservableEmitter<String> emitter) throws Exception {
             emitter.onNext("Event1");
             emitter.onNext("Event2");
             emitter.onComplete();
             emitter.onNext("Event3");
         }
     });
     ```

     

  3. 订阅, 被观察者和观察者之间建立订阅关系

     ```java
     obserable.subscribe(observer);
     ```

     

   Create操作符示意图：

  ![Create操作符](https://github.com/MirDong/PictureGo/blob/master/blog/observable_create.png?raw=true)

  
  
  

## 三、 操作符

### Just操作符

使用just可以创建一个发送指定事件的Observable，just上限为10，也即最多发送10个事件，在Create基础上简化了处理流程。

```java
Observable.just("Event1", "Event2", "Event3")
    	  .subscribe(new Observer<String>() {
               @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe--->");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext--->" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError--->");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete--->");
            }
}
```

just操作符示意图

![just操作符](https://github.com/MirDong/PictureGo/blob/master/blog/observbale_just.png?raw=true)

### from操作符

使用from相关操作符可以创建发送数组(Array)、集合(Iterable)、异步任务(future)的Observable，可将from相关的操作符分为以下几类：

```java
// 数组
public static <T> Observable<T> fromArray(T... items)
// 集合
public static <T> Observable<T> fromIterable(Iterable<? extends T> source)
// 异步任务
public static <T> Observable<T> fromFuture(Future<? extends T> future)
// 异步任务 + 超时时间
public static <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit)
// 异步任务 + 超时时间 + 线程调度器
public static <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit, Scheduler scheduler)
// 异步任务 + 线程调度器
public static <T> Observable<T> fromFuture(Future<? extends T> future, Scheduler scheduler)
//Reactive Streams中的发布者，使用方式类似create操作符，事件的发送由发布者(被观察者)自行决定
public static <T> Observable<T> fromPulisher(Publisher<? extends T> pulisher)
```

1.  fromArray / fromIterable

   ```kotlin
   // fromArray简单使用  
   val events: Array<String> = arrayOf("1", "2", "3")
       Observable.fromArray(*events)
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
   ```

   ![fromArray操作符](https://github.com/MirDong/PictureGo/blob/master/blog/observable_from_array.png?raw=true)

2. fromIterable

   ```kotlin
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
   ```

   ![fromIterable示意图](https://github.com/MirDong/PictureGo/blob/master/blog/observable_from_iterable.png?raw=true)

3. fromCallable

   Callable位于java.util.concurrent包下，和Runnable类似， 但是有返回值，使用fromCallable 发出的事件时从主线程发出的，不订阅则不会执行call里面的操作，使用fromCallable注意一下三点：

   (1). 涉及耗时任务使用subscribeOn切换订阅线程

   (2). 执行耗时任务时， Observable发射值使用ObserveOn切换到主线程接收

   (3). 为避免内存泄漏，在onDestroy()中取消订阅

   ![fromCallable操作符](https://github.com/MirDong/PictureGo/blob/master/blog/observable_from_callable.png?raw=true)

   ```kotlin
   Observable.fromCallable(object : Callable<String> {
           override fun call(): String {
               // 其他操作
   
               return "Callable"
           }
   
       })
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
   ```

   

4. fromFuture

   fromFuture有4个重载方法，可以指定异步任务，超时时间，线程调度器。Future接口位于java.util.concurrent包下,主要作用是对Runnable和Callable异步任务判断是否执行，以及任务结果获取与取消。Runnable和Callable伴随线程执行，意味着fromFuture发出的事件从子线程发出。

   创建fromFuture

   ```kotlin
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
   
   // 第二步： 创建一个FutureTask
       val call = TaskCallable()
       val future = FutureTask<String>(call)
   
   // 第三步：执行Callable
       Observable.fromFuture(future)
   		// 执行future
   		.doOnSubscribe { future.run() }
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
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
   ```

   fromFuture操作符示意图：

   ![fromFuture操作符](https://github.com/MirDong/PictureGo/blob/master/blog/observale_from_future.png?raw=true)

5. 