package com.easyhz.placeapp.data.service

import android.util.Log
import com.easyhz.placeapp.domain.model.user.UserManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM SERVICE ", "Refreshed token: $token")
        UserManager.user?.copy(fcmToken = token)?.let {
            UserManager.setUser(it)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM SERVICE ", "messge: ${message.data}")
        val service = NotificationService(applicationContext)
        service.showNotification(message)
    }

}