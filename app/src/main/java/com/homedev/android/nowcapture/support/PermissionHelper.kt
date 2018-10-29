package com.homedev.android.nowcapture.support

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.homedev.android.nowcapture.Components
import com.homedev.android.nowcapture.ui.MainActivity
import java.util.ArrayList

class PermissionHelper(val context: Context) {
    private val permissionListener = object: PermissionListener {
        override fun onPermissionGranted() {
//            Toast.makeText(context, "권한 허가", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            Toast.makeText(context, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun requestPermssionIfNeed() {
        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("캡쳐 이미지를 저장하기 위해 저장 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [애플리케이션] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }
}