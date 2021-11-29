package com.lapiragua.applapiragua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class buscar_patrimonio extends AppCompatActivity {
    private Button btn_BBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_patrimonio);

        btn_BBack = findViewById(R.id.button_B_Back);

        btn_BBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeUI();
            }
        });
    }
    private void showHomeUI(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}