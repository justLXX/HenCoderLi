@file:JvmName("MainKotlin")

package com.hecoder.coroutine

import android.os.AsyncTask
import android.os.SystemClock
import android.util.Log
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.wait
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

/**
 * @Author: liyuan
 * @Date: 3/7/21 7:59 PM
 * @Description:
 */

val okHttpClient = OkHttpClient()
val request = Request.Builder().url("https://baidu.com").get().build()


//suspend fun main() {
////    testUndispatched()
////    testCoroutineContext()
//    coroutineJob()
//}


suspend fun main(){
    println("<top>.main")
    var i = 0
    Executors.newFixedThreadPool(10)
        .asCoroutineDispatcher().use { dispatcher ->
            List(1000000) {
                GlobalScope.launch(dispatcher) {
                    i++
                }
            }.forEach {
                it.join()
            }
        }
    log(i)
}

private suspend fun  testJoin(){

    suspend fun main() {
        val deferred = GlobalScope.async<Int> {
            throw ArithmeticException()
        }
        try {
            val joinResult = deferred.join()
            deferred.wait()
            log(1)
        } catch (e: Exception) {
            log("2. $e")
        }
    }


}


suspend inline fun Job.Key.currentJob() = coroutineContext[Job]

suspend fun coroutineJob(){
    GlobalScope.launch {
        log(Job.currentJob())
    }
    log(Job.currentJob())
}


private suspend fun testCoroutineContext(){
    coroutineScope {
        GlobalScope.launch {
            println(coroutineContext[Job]) // "coroutine#1":StandaloneCoroutine{Active}@1ff62014
        }
        println(kotlin.coroutines.coroutineContext[Job]) // null，suspend main 虽然也是协程体，但它是更底层的逻辑，因此没有 Job 实例
    }
 }

suspend fun testUndispatched(){
    coroutineScope {
        log(1)
        val job = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            log(2)
            delay(100)
            log(3)
        }
        log(4)
        job.join()
        log(5)
    }
}


val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(msg: Any?) = kotlin.io.println("${now()} [${Thread.currentThread().name}] $msg")


fun main1() {
    printCurrentThread()
    runBlocking {
        printCurrentThread("runBlocking")
        coroutineScopeTest()
        println("runBlocking Finish")
    }

    println("Thread Finish")
}


fun printCurrentThread(extra: String = "") {
    println("当前线程 = ${Thread.currentThread().name}}  $extra")
}

suspend fun coroutineScopeTest() {
    coroutineScope {
        printCurrentThread("coroutineScope")

        val job2 = launch {
            printCurrentThread("job2")

            println("job2 1" )
            delay(2000)
            println("job2 2")
        }

        val job1 = launch {
            println("job1 1")
//            val file = File("./build.gradle")
//            val str = file.readText()
//            println(str)
            delay(1000)
            println("job1 2")
        }

    }
}

suspend fun sequenceTest(): Sequence<String> {
    return sequence<String> {
        repeat(10) {
            yield("==$it")
        }
    }
}


suspend fun getHtml(): String {
    return withContext(SelfDispatcher) {
        println("getHtml 线程名称 " + Thread.currentThread().name)
        okHttpClient.newCall(request).execute().body?.string()
    }.toString()
}

object SelfDispatcher : CoroutineDispatcher() {
    private var thread: Thread? = null

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        thread = Thread(block, "self")
        thread?.start()
    }

}

fun println(msg:String){
    Log.e("========",msg + SystemClock.uptimeMillis())
}