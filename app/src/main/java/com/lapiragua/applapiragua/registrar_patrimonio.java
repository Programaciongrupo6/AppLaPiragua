package com.lapiragua.applapiragua;

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

import androidx.appcompat.app.AppCompatActivity;

import com.lapiragua.applapiragua.ValidacionCampos;

public class registrar_patrimonio extends AppCompatActivity {
    private EditText nombreLugar;
    private Spinner tipoPatrimonio;
    private EditText palabrasClave;
    private TextView etiqueta;
    private EditText ubicacion;
    private Button enviarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_patrimonio);

        nombreLugar = findViewById(R.id.nombreLugar);
        nombreLugar.getText();
        tipoPatrimonio = findViewById(R.id.tipoPatrimonio);
        tipoPatrimonio.getSelectedItem();
        palabrasClave = findViewById(R.id.palabrasClave);
        palabrasClave.getText();
        etiqueta = findViewById(R.id.etiqueta);
//        etiqueta.getText();
        ubicacion = findViewById(R.id.ubicacion);
        ubicacion.getText();
        enviarRegistro = findViewById(R.id.btn_sendRegistro);


        tipoPatrimonio = (Spinner) findViewById(R.id.tipoPatrimonio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tiposP, android.R.layout.simple_list_item_1);
        tipoPatrimonio.setAdapter(adapter);

        ValidacionCampos validar = new ValidacionCampos();
        String ponerEtiqueta = validar.generadorEtiqueta(nombreLugar.toString(),tipoPatrimonio.toString());
        etiqueta.setText(ponerEtiqueta);



        nombreLugar.addTextChangedListener(new TextWatcher() {
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
                String nameWrite = nombreLugar.getText().toString();
                String tipoSelect = tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length()>2){
                String ponerEtiqueta = validar.generadorEtiqueta(nameWrite,tipoSelect);
                etiqueta.setText(ponerEtiqueta);}
            }
        });
        tipoPatrimonio.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nameWrite = nombreLugar.getText().toString();
                String tipoSelect = tipoPatrimonio.getSelectedItem().toString();
                if (nameWrite.length()>2){
                    String ponweEtiqueta = validar.generadorEtiqueta(nameWrite,tipoSelect);
                    System.out.println("asdasdasdqwasfeqasd");
                    etiqueta.setText(ponweEtiqueta);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );



        // INICIO DE EVENTO DE ENVIAR REGISTROS
        enviarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}