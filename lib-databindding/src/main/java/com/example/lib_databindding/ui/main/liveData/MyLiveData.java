package com.example.lib_databindding.ui.main.liveData;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

class MyLiveData<T> {
    public static final String TAG = "MyLiveData";
    //持有的数据
    T mPendingData;
    //观察者集合
    public List<ObserverWrapper<T>> mObservers = new ArrayList<>();

    public void observer(LifecycleOwner owner, Observer<T> observer) {
        if (owner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        ObserverWrapper wrap = new ObserverWrapper(observer, owner.getLifecycle());
        mObservers.add(wrap);
        owner.getLifecycle().addObserver(new MyLifecycleBound());
        dispatchValue();
    }


    /**
     * 绑定数据 发送通知
     *
     * @param value
     */
    public void postValue(T value) {
        mPendingData = value;
        //触发观察者会调
        dispatchValue();
    }

    /**
     * 会调观察者
     */
    private void dispatchValue() {
        for (ObserverWrapper mObserver : mObservers) {
            toChange(mObserver);
        }
    }

    private void toChange(ObserverWrapper wrapper) {
        if (wrapper.lifecycle.getCurrentState() == Lifecycle.State.RESUMED) {
            wrapper.observer.onChanged(mPendingData);
        }
    }


    class MyLifecycleBound implements LifecycleEventObserver {
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            Log.d(TAG, "event = " + event);
            if (mPendingData != null) {
                dispatchValue();
            }
        }
    }

    private class ObserverWrapper<T> {
        Observer<T> observer;
        Lifecycle lifecycle;

        public ObserverWrapper(Observer<T> observer, Lifecycle lifecycle) {
            this.observer = observer;
            this.lifecycle = lifecycle;
        }
    }

}
