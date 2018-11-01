package com.homedev.android.nowcapture.support

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

object PermissionHelper {
    fun requestPermissionIfNeed(context: Context, permissionListener: PermissionListener) {
        TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("캡쳐 이미지를 저장하기 위해 저장 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [애플리케이션] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }
}