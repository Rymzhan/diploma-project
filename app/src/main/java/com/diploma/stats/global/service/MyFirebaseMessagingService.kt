package com.diploma.stats.global.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.diploma.stats.R
import com.diploma.stats.views.main.presentation.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.diploma.stats.global.utils.LocalStorage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val TAG = "FMService"
    }

    private lateinit var notificationManager: NotificationManager
    private val ADMIN_CHANNEL_ID = "BOSCH"


    override fun onNewToken(p0: String) {

    }


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val permAudio = LocalStorage.getSound()
        val permPush = LocalStorage.getPush()

        if (p0.data.isNotEmpty()) {
            p0.let { message ->
                try {
                    val title = message.data["title"]!!
                    val body = message.data["body"]!!
                    val gameId = message.data["game_id"]!!
                    Log.d("Game Id Notification", message.data["game_id"]!!)
                    setNotification(title, body, permPush, permAudio, gameId)
                } catch (e: Exception) {
                    try {
                        val title = message.data["title"]!!
                        val body = message.data["body"]!!
                        setNotification(title, body, permPush, permAudio)
                    } catch (e: Exception) {
                    }
                }
                return
            }
        }

    }

    private fun setNotification(
        title: String,
        body: String,
        permPush: Boolean,
        permAudio: Boolean,
        gameId: String
    ) {
        if (permPush) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("title", title)
            intent.putExtra("body", body)
            intent.putExtra("game_id", gameId)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannels()
            }

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationId = 1000

            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.bosch_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(permPush)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(pendingIntent)
                .setPriority(4)

            if (permAudio) {
                notificationBuilder.setSound(defaultSoundUri)
            }

            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }


    /* @SuppressLint("ServiceCast")
     private fun setNotification(
         title: String,
         body: String,
         executer_id: String,
         order_id:String,
         orderStatus:String,
         permPush: Boolean,
         permAudio: Boolean
     ) {
         if (permPush) {
             val intent = Intent(this, MainActivity::class.java)
             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
             intent.putExtra("title", title)
             intent.putExtra("executor_id", executer_id)
             intent.putExtra("order_id", order_id)
             intent.putExtra("orderStatus", orderStatus)

             val pendingIntent = PendingIntent.getActivity(
                 this,
                 0,
                 intent,
                 PendingIntent.FLAG_UPDATE_CURRENT
             )

             notificationManager =
                 getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 setupNotificationChannels()
             }

             val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
             val notificationId = 1000

             val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                 .setSmallIcon(R.mipmap.ic_launcher)
                 .setContentTitle(title)
                 .setContentText(body)
                 .setAutoCancel(permPush)
                 .setSound(defaultSoundUri)
                 .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                 .setContentIntent(pendingIntent)
                 .setPriority(4)

             if (permAudio) {
                 notificationBuilder.setSound(defaultSoundUri)
             }

             notificationManager.notify(notificationId, notificationBuilder.build())
         }
     }*/

    @SuppressLint("ServiceCast")
    private fun setNotification(
        title: String,
        body: String,
        permPush: Boolean,
        permAudio: Boolean
    ) {
        if (permPush) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("title", title)
            intent.putExtra("body", body)

            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannels()
            }

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationId = 1000

            val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.bosch_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(permPush)
                .setSound(defaultSoundUri)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setContentIntent(pendingIntent)
                .setPriority(4)

            if (permAudio) {
                notificationBuilder.setSound(defaultSoundUri)
            }

            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels() {
        val adminChannelName = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(
            ADMIN_CHANNEL_ID,
            adminChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.DKGRAY
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }
}