package com.lapiragua.applapiragua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class RegistroSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_success);
        TimerTask registro = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(RegistroSuccess.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(registro, 2000);
    }
}