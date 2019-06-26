package com.example.notificationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTIFICATION_ID = 1001;
    private Button sendNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pi = (PendingIntent) PendingIntent.getActivities(this,0, new Intent[]{intent},0);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("你好这是通知")
                        .setContentText("这个是通知的内容")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setVibrate(new long[]{0,2000,1000,2000})
                        .build();
                //判断版本号,8.0以上不支持上面的通知
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("001","mychannel",NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableLights(true);//是否在桌面上展示小红点;
                    channel.setLightColor(Color.GREEN);
                    channel.setShowBadge(true);
                    manager.createNotificationChannel(channel);
                    builder.setChannelId("001");
                }

                Notification n = builder.build();
                manager.notify(NOTIFICATION_ID,n);

                break;
            default:
                break;
        }
    }
}
