package com.example.lesson.t;

import com.example.lesson.t.fruit.Apple;
import com.example.lesson.t.fruit.Banana;
import com.example.lesson.t.fruit.Fruit;

import java.util.ArrayList;

class LiList<T> {
    Object[] list = new Object[0];

    T getIndex(int index) {
        return (T) list[index];
    }


    void main() {

        ArrayList<Apple> apples = new ArrayList();
        ArrayList<? extends Fruit> fruits = apples;
        Apple apple = new Apple();
        Banana banana = new Banana();
//        fruits.add(apple);
//        fruits.add();

    }
}
