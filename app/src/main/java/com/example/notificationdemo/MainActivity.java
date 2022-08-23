package com.example.notificationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btn_show,btn_cancel;
    private final int id = new Random().nextInt(999)+1;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_show = findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
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