package com.hecoder.coroutine;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;

class MainJava {

    public void javaGetHtml(){
        MainKotlin.getHtml(new Continuation<String>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return Dispatchers.getIO();
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println("resumeWith = " + o.toString());
            }
        });
    }
}

