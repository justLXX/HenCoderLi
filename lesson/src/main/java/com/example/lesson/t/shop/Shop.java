package com.example.lesson.t.shop;

interface Shop<T> {
    T buy();

    float refund(T item);
}
