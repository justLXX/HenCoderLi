package com.hecoder.li

import android.net.Uri
import android.os.Bundle
import android.os.RecoverySystem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.activityresult.PictureObserver
import com.example.app.R
import com.example.lesson.LessonAdapter
import com.example.lesson.entity.Lesson
import com.view.SqWindowManagerFloatView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var observer: PictureObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observer = PictureObserver(activityResultRegistry)
        lifecycle.addObserver(observer)

    }


}

