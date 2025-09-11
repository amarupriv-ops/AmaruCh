package com.example.amaruch

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val REQ_MEDIA_PROJ = 1001
    private val REQ_OVERLAY = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn = Button(this).apply { text = "Start AmaruCh" }
        setContentView(btn)

        btn.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(
                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")),
                    REQ_OVERLAY)
            } else {
                requestScreenCapture()
            }
        }
    }

    private fun requestScreenCapture() {
        val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val intent = mgr.createScreenCaptureIntent()
        startActivityForResult(intent, REQ_MEDIA_PROJ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_OVERLAY) {
            if (Settings.canDrawOverlays(this)) requestScreenCapture()
            else toast("Perlu izin overlay")
        } else if (requestCode == REQ_MEDIA_PROJ) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val svc = Intent(this, ScreenCaptureService::class.java)
                svc.putExtra("resultCode", resultCode)
                svc.putExtra("resultData", data)
                startForegroundService(svc)
                finish()
            } else {
                toast("Izin screen capture ditolak")
            }
        }
    }

    private fun toast(s:String) = Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}