package com.example.notificationdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class NotificationData{
    static NotificationManager notificationManager;

    final static String CHANNEL_ID = "channel";
    final static String CHANNEL_NAME = "my notification";
    final static String CHANNEL_DESCRIPTION = "Test";

    final int id = new Random().nextInt(999)+1;
}

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> list = new ArrayList<Integer>();

    private Button btn_show,btn_cancel,btn_cancel_all;
    //private final int id = new Random().nextInt(999)+1;
    //private NotificationManager notificationManager;

    NotificationData noti1 = new NotificationData();
    NotificationData noti2 = new NotificationData();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add(noti1.id);
        list.add(noti2.id);

        btn_show = findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    showNotificationWithChannel();
                }
                else{
                    showNotification();
                }
            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NotificationData.notificationManager!= null){
                    NotificationData.notificationManager.cancel(noti1.id);
                }

            }
        });

        btn_cancel_all = findViewById(R.id.btn_cancel_all);
        btn_cancel_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NotificationData.notificationManager!= null){
                    for(Integer i : list){
                        NotificationData.notificationManager.cancel(i);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    NotificationChannel getNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(NotificationData.CHANNEL_ID,NotificationData.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(NotificationData.CHANNEL_DESCRIPTION);
        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationWithChannel(){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),NotificationData.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification test")
                .setContentText("This is notification message for notify")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));


        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext(),NotificationData.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification test2")
                .setContentText("This is notification message for notify2")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationChannel channel = getNotificationChannel();
        NotificationData.notificationManager = getSystemService(NotificationManager.class);
        NotificationData.notificationManager.createNotificationChannel(channel);



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(noti1.id,builder.build());
        notificationManagerCompat.notify(noti2.id,builder2.build());


    }

    private void showNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(createNotificationStyle());

        Notification.Builder builder2 = new Notification.Builder(getApplicationContext());
        builder2.setContentTitle("TEST")
                .setContentText("This is a test notification text")
                .setSmallIcon(R.mipmap.ic_launcher);


        Notification notification = builder.build();
        Notification notification2 = builder2.build();

        NotificationData.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationData.notificationManager.notify(noti1.id,notification);
        NotificationData.notificationManager.notify(noti2.id,notification2);



    }

    Notification.InboxStyle createNotificationStyle(){
        Notification.InboxStyle style = new Notification.InboxStyle()
                .setBigContentTitle("แจ้งเตือนการใช้พลังงาน")
                .addLine("การใช้พลังงานเพิ่มขึ้น 10 %")
                .addLine("เมื่อเทียบกับเดือนที่ผ่านมา")
                .addLine("คิดเป็นจำนวนเงิน 543.21")
                .setSummaryText("แตะเพื่อเข้าสู่หน้าจอรายงานการใช้พลังงานไฟฟ้า");
        return style;
    }
}