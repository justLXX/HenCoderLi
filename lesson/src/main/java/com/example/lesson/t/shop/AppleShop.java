package com.example.lesson.t.shop;


import com.example.lesson.t.shop.bean.Apple;

class AppleShop implements Shop<Apple> {
    @Override
    public Apple buy() {
        return null;
    }

    @Override
    public float refund(Apple item) {
        return 0;
    }
}
