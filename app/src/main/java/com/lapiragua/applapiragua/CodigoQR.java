package com.lapiragua.applapiragua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CodigoQR extends AppCompatActivity {
    private Button btn_QRBack;
    private Button btn_AbrirScanner;
    private Button btn_Buscar;
    private EditText et_QR;
    private IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);
        init();
        event();




    }

    private void init(){
        btn_QRBack = findViewById(R.id.button_QR_Back);
        btn_AbrirScanner = findViewById(R.id.AbrirScanner);
        btn_Buscar= findViewById(R.id.button_buscar);
        et_QR = findViewById(R.id.editTextQR);
    }
    private void event(){
        btn_QRBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeUI();
            }
        });
        btn_AbrirScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewQRUI();
                /*
                intentIntegrator = new IntentIntegrator(
                        CodigoQR.this);
                intentIntegrator.setPrompt("Coloca el código QR en el interior del rectángulo del viso para escanear ");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

                 */
            }
        });
        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeQR;
                codeQR = et_QR.getText().toString();
                codeQR = codeQR.toUpperCase();
                Intent intent = new Intent(CodigoQR.this, LugarPatrimonialActivity.class);
                intent.putExtra("codeQR", codeQR);
                startActivity(intent);
            }
        });
    }
    /*
    //metodo onclick
    public void onclick(View view) {
        if(view.getId() == R.id.AbrirScanner){
            new IntentIntegrator(this).initiateScan();
        }
    }

     */

    // llamar al result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // llamar a la informacion

        IntentResult result = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        if (result.getContents()!=null) {
            //obtener la información en un String
            String dato = result.getContents();
            et_QR.setText(dato);
        }
        /*
        Uri link = Uri.parse(dato);
        Intent i = new Intent(Intent.ACTION_VIEW,link);
        startActivity(i);
        */

    }


    private void showHomeUI(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private void showViewQRUI(){
        Intent intent = new Intent(this, viewQRActivity.class);
        startActivity(intent);
    }


}