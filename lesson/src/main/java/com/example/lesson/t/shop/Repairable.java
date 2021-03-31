package com.example.lesson.t.shop;

interface Repairable<T> extends Shop<T>{
    void repair(T item);
}
