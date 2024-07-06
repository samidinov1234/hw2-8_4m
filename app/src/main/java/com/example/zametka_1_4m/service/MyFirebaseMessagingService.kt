package com.example.zametka_1_4m.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.zametka_1_4m.R
import com.example.zametka_1_4m.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){
    companion object{
        const val  CHANNEL_ID = "notification_channel"
        const val  CHANNEL_NAME = "Notification Channel"
        const val NOTIFICATION_ID = 0
    }
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let { notification ->
            val title = notification.title ?: ""
            val body = notification.body ?: ""
            showNotification(title, body)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(title: String, body: String) {
        val intent = Intent(this , MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pending  = PendingIntent.getActivity(this , 0 , intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val notificationLayout = getNotificationLayout(title , body)
        val builder = NotificationCompat.Builder(this , CHANNEL_ID )
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setContentIntent(pending)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)

        createNotification()
        with(NotificationManagerCompat.from(this)){
            notify(NOTIFICATION_ID , builder.build())
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun getNotificationLayout(title: String, body: String): RemoteViews? {
        val remoteViews = RemoteViews(packageName , R.layout.item_messege)
        remoteViews.setTextViewText(R.id.txt_title , title)
        remoteViews.setTextViewText(R.id.txt_message , body)
        remoteViews.setImageViewResource(R.id.image_logo , R.drawable.ic_stat_ic_notification)
        return remoteViews
    }

    private fun createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
