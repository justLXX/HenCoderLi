package com.example.lib_databindding.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _liveData by lazy {
        MutableLiveData<Int>(0)
    }
    val liveData get() = _liveData

    var number = 0
    private set

    fun add() {
//        _liveData.value = _liveData.value?.apply {
//            this.age += n
//        }
        number ++
        _liveData.value = number
        println(number)
    }
}