package com.example.amaruch

import android.graphics.Bitmap
import android.graphics.PointF
import java.util.concurrent.Executors

class ImageProcessor(private val onResult: (PointF?, PointF?) -> Unit) {
    private val pool = Executors.newSingleThreadExecutor()

    fun processFrame(bmp: Bitmap) {
        pool.execute {
            val cue = PointF(bmp.width*0.25f, bmp.height*0.5f)
            val target = PointF(bmp.width*0.75f, bmp.height*0.5f)
            onResult(cue, target)
        }
    }
}