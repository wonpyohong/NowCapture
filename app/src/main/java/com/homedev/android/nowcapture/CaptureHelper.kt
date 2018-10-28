package com.homedev.android.nowcapture

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.text.format.DateFormat
import android.util.Log
import android.view.Window
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.concurrent.schedule

class CaptureHelper {
    fun takeScreenshot(window: Window) {
        try {
            val bitmap = captureRoot(window)
            val imageFile = createImageFile()
            writeBitmapToFile(imageFile, bitmap)
            scanImageFiles(imageFile)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun captureRoot(window: Window): Bitmap {
        val rootView = window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun createImageFile(): File {
        val nowString = DateFormat.format("yyyy-MM-dd_hh:mm:ss", Date())
        val dir = "${Environment.getExternalStorageDirectory()}/NowCapture"
        val fileName = "$nowString.jpg"
        File(dir).mkdirs()
        val imageFile = File("$dir/$fileName")
        return imageFile
    }

    private fun writeBitmapToFile(imageFile: File, bitmap: Bitmap) {
        val outputStream = FileOutputStream(imageFile)
        val quality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun scanImageFiles(imageFile: File) {
        MediaScannerConnection.scanFile(NowApplication.applicationContext,
                arrayOf(imageFile.toString()), arrayOf("image/*")
        ) { path, uri ->
            openScreenshot(imageFile)
        }
    }

    private fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        NowApplication.applicationContext.startActivity(intent)
    }

    private fun fromFile(file: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {        // 24 이상
            FileProvider.getUriForFile(NowApplication.applicationContext, NowApplication.applicationContext.packageName + ".fileprovider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}