package com.example.rxjavastudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjavastudy.create.just
import com.example.rxjavastudy.create.justNumber
import com.example.rxjavastudy.from.fromArray
import com.example.rxjavastudy.from.fromCallable
import com.example.rxjavastudy.from.fromFuture
import com.example.rxjavastudy.from.fromIterable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        just()
//        justNumber()
//        fromArray()
//        fromIterable()
//        fromCallable()
        fromFuture()
    }
}