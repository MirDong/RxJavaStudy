package com.example.rxjavastudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjavastudy.create.just
import com.example.rxjavastudy.create.justNumber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        just()
        justNumber()
    }

}