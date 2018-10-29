package com.homedev.android.nowcapture.capture

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import android.text.format.DateFormat
import android.view.Window
import com.homedev.android.nowcapture.Components
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CaptureHelper {
    fun takeScreenshot(window: Window): File {
        val bitmap = captureRoot(window)
        val imageFile = createImageFile()
        writeBitmapToFile(imageFile, bitmap)
        return imageFile
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

    fun scanImageFiles(imageFile: File, onCompleted: () -> Unit) {
        MediaScannerConnection.scanFile(Components.getAppContext(),
                arrayOf(imageFile.toString()), arrayOf("image/*")
        ) { path, uri ->
            onCompleted()
        }
    }

    fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        Components.getAppContext().startActivity(intent)
    }

    private fun fromFile(file: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {        // 24 이상
            FileProvider.getUriForFile(Components.getAppContext(), Components.getAppContext().packageName + ".fileprovider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}