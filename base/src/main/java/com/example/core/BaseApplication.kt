
package com.example.core

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        currentApplication = this
    }

    companion object {
        @JvmStatic
        @get:JvmName("currentApplication")
        lateinit var currentApplication: Context
            private set


        fun getCompanionName() = "companionName"

    }
}

const val name = "BaseApplication"

val age = 1

fun getMine(){

}