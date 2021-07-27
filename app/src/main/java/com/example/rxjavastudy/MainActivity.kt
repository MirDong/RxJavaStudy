package com.example.rxjavastudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjavastudy.create.just
import com.example.rxjavastudy.create.justNumber
import com.example.rxjavastudy.defer.defer
import com.example.rxjavastudy.empty.empty
import com.example.rxjavastudy.from.*
import com.example.rxjavastudy.never.never

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        just()
//        justNumber()
//        fromArray()
//        fromIterable()
//        fromCallable()
//        fromFuture()
//        fromFutureDelay(1000)
//        cancelTask()
//        defer()
//        empty()
        never()
    }
}