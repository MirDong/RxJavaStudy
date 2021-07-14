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

  ![Create操作符](app\image\observable_create.png)

  

