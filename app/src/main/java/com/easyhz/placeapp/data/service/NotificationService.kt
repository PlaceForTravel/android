package com.easyhz.placeapp.data.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.easyhz.placeapp.R
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationService
@Inject constructor(
    @ApplicationContext private val context: Context
){
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: RemoteMessage) {
        val title = message.notification?.title
        val body = message.notification?.body
        val boardId = message.data["boardId"]
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = "wooyeojung://board/${boardId}".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.fold_map)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(0, notification)
    }

    companion object {
        const val CHANNEL_ID = "fcm_channel_1"
        const val CHANNEL_NAME = "댓글 및 저장"
    }
}