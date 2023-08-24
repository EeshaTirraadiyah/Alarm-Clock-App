package com.example.mainactivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TimePicker alarmTimer;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTimer = findViewById(R.id.timer);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, alarmTimer.getHour());
            calendar.set(Calendar.MINUTE, alarmTimer.getMinute());
            calendar.set(Calendar.SECOND, 0);

            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            long time = calendar.getTimeInMillis();

            if (System.currentTimeMillis() > time) {
                time += AlarmManager.INTERVAL_DAY;
            }

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
            );
        } else {
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
            }
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }
}
