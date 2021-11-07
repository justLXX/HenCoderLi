package com.activityresult

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class PictureObserver(private val registry: ActivityResultRegistry) : LifecycleEventObserver {

    val TAG = "PictureObserver";

    lateinit var getContent: ActivityResultLauncher<String>

    fun selectImage() {
        getContent.launch("image/*")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            getContent = registry.register(
                "key",
                source,
                ActivityResultContracts.GetContent()
            ) { uri ->
                Log.e(TAG, "onActivityResult = $uri")
            }
        }
    }
}