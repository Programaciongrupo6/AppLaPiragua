package com.lapiragua.applapiragua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LugarPatrimonialActivity extends AppCompatActivity {
    private String code;
    private TextView tv_codeQR;
    private TextView tv_nombre;
    private TextView tv_palabrasClaves;
    private TextView tv_tipo;
    private TextView tv_ubicacion;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    private Button btn_LPQRBack;



    private FirebaseFirestore db;
    private Task<DocumentSnapshot> docRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_patrimonial);

        code = getIntent().getExtras().getString("codeQR");

        tv_codeQR = findViewById(R.id.textView_codeQR);
        tv_nombre = findViewById(R.id.textView_Nombre);
        tv_palabrasClaves = findViewById(R.id.textView_palabraClave);
        tv_tipo = findViewById(R.id.textView_tipo);
        tv_ubicacion = findViewById(R.id.textView_ubicacion);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayout_container);
        btn_LPQRBack= findViewById(R.id.button_lugarQr_Back);

        linearLayout.setVisibility(View.GONE);
        tv_codeQR.setText(code);
        db = FirebaseFirestore.getInstance();

        btn_LPQRBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRUI();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBase(code);
    }

    private void dataBase(String codeQR){

        docRef  = db.collection("lugaresPatrimoniales").document(codeQR).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nombre = documentSnapshot.getString("nombre");
                    String palabraClave = documentSnapshot.getString("palabraClave");
                    String tipo = documentSnapshot.getString("tipo");
                    String ubicacion = documentSnapshot.getString("ubicacion");

                    tv_nombre.setText("Nombre: "+nombre);
                    tv_palabrasClaves.setText("Palabra clave: "+palabraClave);
                    tv_tipo.setText("Tipo: "+tipo);
                    tv_ubicacion.setText("Ubicación: "+ubicacion);

                    linearLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LugarPatrimonialActivity.this, "Lugar ("+codeQR+") no encontrado", Toast.LENGTH_LONG).show();
                    showQRUI();

                }
            }
        });
        /*
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        String data = document.getData().toString();
                        tv_codeQR.setText(data);

                    }else {
                        Toast.makeText(LugarPatrimonialActivity.this, "2", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LugarPatrimonialActivity.this, "1", Toast.LENGTH_SHORT).show();
                }
            }
        });

         */

        /*
        // recupera todos los lugares
        db.collection("lugaresPatrimoniales")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String data = document.getId().toString();
                                data += document.getData().toString();
                                tv_codeQR.setText(data);
                            }
                            //msn(String.format("Numero de lugares: %s",numeroDeLugares));
                        } else {
                            //msn("Error al obtener el lugar");
                        }
                    }
                });

         */


    }
    private void showQRUI(){
        Intent intent = new Intent(this, CodigoQR.class);
        startActivity(intent);
    }
}