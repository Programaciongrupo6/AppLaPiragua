package com.lapiragua.applapiragua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class CodigoQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr);
    }
    //metodo onclick
    public void onclick(View view) {
        if(view.getId() == R.id.AbrirScanner){
            new IntentIntegrator(this).initiateScan();
        }
    }
    // llamar al result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // llamar a la informacion

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        //obtener la información en un String
        String dato = result.getContents();
        Uri link = Uri.parse(dato);
        Intent i = new Intent(Intent.ACTION_VIEW,link);
        startActivity(i);
    }
}