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

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btn_show,btn_cancel;
    private final int id = new Random().nextInt(999)+1;
    private NotificationManager notificationManager;

    public final String CHANNEL_ID = "channel"+String.valueOf(id);
    public final String CHANNEL_NAME = "my notification";
    public final String CHANNEL_DESCRIPTION = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(notificationManager!= null)
                    notificationManager.cancel(id);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    NotificationChannel getNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(CHANNEL_DESCRIPTION);
        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationWithChannel(){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification test")
                .setContentText("This is notification message for notify")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        /*
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification test2")
                .setContentText("This is notification message for notify2")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
         */

        NotificationChannel channel = getNotificationChannel();
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(id,builder.build());
        //notificationManagerCompat.notify(id+1,builder2.build());

    }

    private void showNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(createNotificationStyle());

        Notification notification = builder.build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id,notification);
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