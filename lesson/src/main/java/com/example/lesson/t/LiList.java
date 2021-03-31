package com.example.lesson.t;

class LiList<T> {
    Object[] list = new Object[0];

    T getIndex(int index){
        return (T) list[index];
    }
}
