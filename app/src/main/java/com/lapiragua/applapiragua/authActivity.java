package com.lapiragua.applapiragua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class authActivity extends AppCompatActivity {
    private Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
        event();
    }
    private void init(){
        btnregister = findViewById(R.id.button_register);


    }
    private void event(){
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( authActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

    }
}