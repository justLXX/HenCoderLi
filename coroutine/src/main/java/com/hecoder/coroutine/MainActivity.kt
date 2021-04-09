package com.hecoder.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main1()
    }


}


fun main() {
    val kot = Kot()
    print("age = ${kot.age}")
}


class Kot() {

    val name = "111"

    val age by lazy {
        name.toInt()
    }

    val msg = "我是 msg$age"

}
