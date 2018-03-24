package com.github.kiolk.hrodnaday.data.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.github.kiolk.hrodnaday.DayNoteModel
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.data.database.DBOperations
import com.github.kiolk.hrodnaday.getCurrentDay
import com.github.kiolk.hrodnaday.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {


    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        val notificationBuilder = NotificationCompat.Builder(this, "one")
        notificationBuilder.setSmallIcon(R.drawable.ic_herb_of_hrodna)

        if (p0?.notification == null) {
            notificationBuilder.setContentTitle(p0?.data?.get("title"))
            notificationBuilder.setContentText(p0?.data?.get("text"))
        } else {
            notificationBuilder.setContentTitle(p0.notification?.title)
            notificationBuilder.setContentText(p0.notification?.body)
        }

//        notificationBuilder.setColor(resources.getColor(R.color.BUTTON_COLOR))
        val note : DayNoteModel = DBOperations().getAll().filter { it.day < getCurrentDay() }.last()

        notificationBuilder.setContentText(note.title)
//        notificationBuilder.setColor(Color.RED)

        notificationBuilder.priority = NotificationCompat.PRIORITY_MAX

        val intent: Intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        notificationBuilder.setContentIntent(pendingIntent)
        notificationBuilder.setAutoCancel(true)

        val notificationMannager = NotificationManagerCompat.from(this)
        notificationMannager.notify(0, notificationBuilder.build())
    }
}