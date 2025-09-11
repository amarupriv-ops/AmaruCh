package com.example.amaruch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.Gravity
import android.view.View
import android.view.WindowManager

class OverlayView(private val ctx: Context) : View(ctx) {
    private val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        android.graphics.PixelFormat.TRANSLUCENT).apply {
        gravity = Gravity.TOP or Gravity.START
    }

    private val paint = Paint().apply {
        strokeWidth = 6f
        style = Paint.Style.STROKE
    }

    private var cue: PointF? = null
    private var target: PointF? = null

    fun attach() { wm.addView(this, params) }
    fun detach() { wm.removeView(this) }

    fun updateTargets(cuePoint: PointF?, targetPoint: PointF?) {
        cue = cuePoint
        target = targetPoint
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val c = cue; val t = target
        if (c != null && t != null) {
            canvas.drawLine(c.x, c.y, t.x, t.y, paint)
        }
    }
}