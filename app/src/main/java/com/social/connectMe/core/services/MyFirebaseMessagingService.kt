//package com.social.connectMe.core.services
//
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    @Inject
//    lateinit var notificationHandler: NotificationHandler
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//
//        val title = message.notification?.title ?: message.data["title"] ?: "New Notification"
//        val body = message.notification?.body ?: message.data["body"] ?: ""
//
//        notificationHandler.showNotification(
//            title = title,
//            message = body,
//            data = message.data
//        )
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        // Handle token refresh, e.g., send to server
//    }
//}
