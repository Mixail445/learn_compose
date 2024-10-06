package com.example.learn_compose.service

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.learn_compose.R
import com.example.learn_compose.ui.MainActivity

class NotificationPermissionManager(
    private val activity: MainActivity,
) {
    private val requestNotificationPermission =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showNotification(activity, true)
            } else {
                Log.e("Notification", activity.getString(R.string.permission_three))
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                activity,
                permission,
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d("notification", "accept")
            }

            activity.shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationaleDialog()
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionRationaleDialog() {
        AlertDialog
            .Builder(activity)
            .setTitle(activity.getString(R.string.permision_one))
            .setMessage(activity.getString(R.string.permision_two))
            .setPositiveButton(activity.getString(R.string.ok)) { _, _ ->
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }.setNegativeButton("Отмена", null)
            .create()
            .show()
    }
}
