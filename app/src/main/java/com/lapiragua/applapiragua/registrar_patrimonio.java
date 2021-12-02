package com.lapiragua.applapiragua;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.HashMap;
import java.util.Map;

public class registrar_patrimonio extends AppCompatActivity {
    private EditText ET_nombreLugar;
    private Spinner S_tipoPatrimonio;
    private EditText ET_palabrasClave;
    private TextView TE_etiqueta;
    private EditText ET_ubicacion;

    private Button btnRegistrar;
    private Button btn_RPBack;
    private ValidacionCampos validar;

    private FirebaseFirestore db;

    private int numeroDeLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_patrimonio);
        init();
        event();
        dataBase();



    }
    private void init(){
        ET_nombreLugar = findViewById(R.id.editText_nombreLugar);
        S_tipoPatrimonio = findViewById(R.id.spinner_tipoPatrimonio);
        ET_palabrasClave = findViewById(R.id.editText_palabrasClave);
        TE_etiqueta = findViewById(R.id.textView_etiqueta);
        ET_ubicacion = findViewById(R.id.editText_ubicacion);

        btnRegistrar = findViewById(R.id.btn_sendRegistro);
        btn_RPBack= findViewById(R.id.button_RP_Back);


        S_tipoPatrimonio = (Spinner) findViewById(R.id.spinner_tipoPatrimonio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tiposP, android.R.layout.simple_list_item_1);
        S_tipoPatrimonio.setAdapter(adapter);

        validar = new ValidacionCampos();
        //String ponerEtiqueta = validar.generadorEtiqueta(ET_nombreLugar.toString(),S_tipoPatrimonio.toString());
        //TE_etiqueta.setText(ponerEtiqueta);

        db = FirebaseFirestore.getInstance();
    }

    private void event(){
        btn_RPBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeUI();
            }
        });

        // INICIO DE EVENTO DE ENVIAR REGISTROS
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etiqueta = TE_etiqueta.getText().toString();
                String nombre = ET_nombreLugar.getText().toString();
                String tipo = S_tipoPatrimonio.getSelectedItem().toString();
                String palabraClave = ET_palabrasClave.getText().toString();
                String ubicacion = ET_ubicacion.getText().toString();
                String usuario = "Falta que guarde el usuario";

                Map<String, Object> lugarP = new HashMap<>();
                lugarP.put("usuario", usuario);
                lugarP.put("etiqueta", etiqueta);
                lugarP.put("nombre", nombre);
                lugarP.put("tipo", tipo);
                lugarP.put("palabraClave", palabraClave);
                lugarP.put("ubicacion", ubicacion);
                if (!etiqueta.isEmpty() && !nombre.isEmpty() && !tipo.isEmpty() && !palabraClave.isEmpty() && !ubicacion.isEmpty()){
                    db.collection("lugaresPatrimoniales")
                            .document(etiqueta)
                            .set(lugarP);
                    msn("Lugar agregado correctamente");
                    TE_etiqueta.setText("");
                    ET_nombreLugar.setText("");
                    ET_palabrasClave.setText("");
                    ET_ubicacion.setText("");
                    dataBase();
                    /*
                    db.collection("lugaresPatrimoniales")
                            .add(lugarP)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    msn("Lugar agregado correctamente");
                                    //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    TE_etiqueta.setText("");
                                    ET_nombreLugar.setText("");
                                    ET_palabrasClave.setText("");
                                    ET_ubicacion.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Log.w(TAG, "Error adding document", e);
                                    msn("Error al agregar el lugar");
                                }
                            });

                     */

                }else{
                    msn("Todos lo campos son obligatorios");
                }


            }
        });


        ET_nombreLugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //System.out.println(s.toString() + " " + start + " " + count + " " + after);
                //msg("Before");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //msg("onChanged");
                //System.out.println(s.toString() + " " + start + " " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameWrite = ET_nombreLugar.getText().toString();
                String tipoSelect = S_tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length()>2){
                    String ponerEtiqueta = validar.generadorEtiqueta(nameWrite,tipoSelect, numeroDeLugares+1);
                    TE_etiqueta.setText(ponerEtiqueta);
                }else{
                    TE_etiqueta.setText("");
                }
            }
        });

        S_tipoPatrimonio.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nameWrite = ET_nombreLugar.getText().toString();
                String tipoSelect = S_tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length()>2){
                    String ponweEtiqueta = validar.generadorEtiqueta(nameWrite,tipoSelect, numeroDeLugares+1);
                    System.out.println("asdasdasdqwasfeqasd");
                    TE_etiqueta.setText(ponweEtiqueta);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


    }

    private void dataBase(){
        db.collection("lugaresPatrimoniales")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            numeroDeLugares=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                //msn(document.getId() + " => " + document.getData());
                                //String data = String.format("%s  =>  %s", document.getId(), document.getData());
                                //TE_etiqueta.setText(data);
                                //TE_etiqueta.getText(document.getId() + " => " + document.getData());
                                numeroDeLugares += 1;
                            }

                            //msn(String.format("Numero de lugares: %s",numeroDeLugares));
                        } else {
                            msn("Error al obtener el lugar");
                        }
                    }
                });
    }
    private void showHomeUI(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private void msn(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}