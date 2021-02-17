package com.example.app.entity

import kotlin.reflect.KProperty


fun main() {
    val user = User("李锦灿","password","234")
    user.update()
}

data class User(var username: String?, var password: String?, var code: String?)
    :Notification by NotificationImpl()
{

    var token: String by Saver()

}

interface Notification {

    fun update()

}

class NotificationImpl():Notification{
    override fun update() {
        println("update")
    }

}

class Saver {
    operator fun getValue(user: User, property: KProperty<*>): String {
        TODO("Not yet implemented")
    }

    operator fun setValue(user: User, property: KProperty<*>, s: String) {

    }

}
