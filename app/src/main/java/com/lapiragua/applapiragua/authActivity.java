package com.lapiragua.applapiragua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class authActivity extends AppCompatActivity {
    private Button btnRegister;
    private Button btnLogin;
    // initiate a Switch
    private Switch btnSwitch;
    private Boolean switchState;


    private TextInputLayout editTLayoutName;
    private EditText editTName;
    private TextInputLayout editTLayoutEmail;
    private EditText editTEmail;
    private TextInputLayout editTLayoutMovile;
    private EditText editTMovile;
    private TextInputLayout editTLayoutPass;
    private EditText editTPass;
    private TextInputLayout editTLayoutPassPass;
    private EditText editTPassPass;
    private Button btn_AuthBack;

    private ConstraintLayout constraintLayoutView;
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
        event();
    }
    private void init(){
        btnRegister = findViewById(R.id.button_register);
        btnLogin = findViewById(R.id.button_login);
        btn_AuthBack = findViewById(R.id.button_AuthBack);
        btnSwitch = (Switch) findViewById(R.id.switch_register_login);
        editTLayoutName = findViewById(R.id.edit_name);
        editTName = editTLayoutName.getEditText();
        editTLayoutEmail= findViewById(R.id.edit_Email);
        editTEmail = editTLayoutEmail.getEditText();
        editTLayoutMovile= findViewById(R.id.edit_Mobile);
        editTMovile = editTLayoutMovile.getEditText();
        editTLayoutPass= findViewById(R.id.edit_Pass);
        editTPass = editTLayoutPass.getEditText();
        editTLayoutPassPass = findViewById(R.id.edit_PassPass);
        editTPassPass = editTLayoutPassPass.getEditText();

        btnRegister.setVisibility(View.GONE);
        editTLayoutName.setVisibility(View.GONE);
        editTLayoutMovile.setVisibility(View.GONE);
        editTLayoutPassPass.setVisibility(View.GONE);

        constraintLayoutView = findViewById(R.id.constraintLayout);



        mAuth = FirebaseAuth.getInstance();

    }
    private void event(){
        btn_AuthBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAuthUI();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTName.getText().toString();
                String email = editTEmail.getText().toString();
                String mobile = editTMovile.getText().toString();
                String password = editTPass.getText().toString();
                String passwordConfirmation = editTPassPass.getText().toString();
                signup(name, email, mobile, password, passwordConfirmation);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTEmail.getText().toString();
                String password = editTPass.getText().toString();
                login(email, password);
            }
        });

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchState = btnSwitch.isChecked();
                if (switchState){
                    //registrase
                    btnSwitch.setText("¿Quieres iniciar sesión?");
                    btnLogin.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    editTLayoutName.setVisibility(View.VISIBLE);
                    editTLayoutMovile.setVisibility(View.VISIBLE);
                    editTLayoutPassPass.setVisibility(View.VISIBLE);
                }else{
                    //iniciar sesión
                    btnSwitch.setText("¿Quieres regístrate?");
                    btnLogin.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.GONE);
                    editTLayoutName.setVisibility(View.GONE);
                    editTLayoutMovile.setVisibility(View.GONE);
                    editTLayoutPassPass.setVisibility(View.GONE);

                }
            }
        });


    }
    private boolean login(String email, String pass){
        if (email.isEmpty() || pass.isEmpty()){
            msn("Todos los campos son obligatorios");
        }else{
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        showHomeUI();
                    } else{
                        showAlert();
                    }
                }
            });
        }
        return true;
    }
    public boolean signup(String name, String email, String movile, String pass, String passPass ){

        if (name.isEmpty() || email.isEmpty() || movile.isEmpty() || pass.isEmpty() || passPass.isEmpty()){
            msn("Todos los campos son obligatorios");
        }else{
            if (pass.equals(passPass)){
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            showHomeUI();

                        } else{
                            showAlert();
                        }
                    }
                });


            }else{
                msn("Confirmación de contraseña incorrecta");
            }
        }
        return true;
    }
    private void showAuthUI(){
        Intent intentAuth = new Intent(this, MainActivity.class);
        startActivity(intentAuth);
    }
    private void msn(String data){
        Snackbar snackbar = Snackbar.make(
                constraintLayoutView,
                data,
                Snackbar.LENGTH_LONG
        );
        snackbar.show();

    }
    private void showHomeUI(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticación");
        builder.setMessage("Se ha producido un error autenticando el usuario.");
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alert = builder.create();
        alert.show();

    }
}