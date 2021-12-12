package com.lapiragua.applapiragua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.lapiragua.applapiragua.Home.HomeAppActivity;

public class MainActivity extends AppCompatActivity {
    //Elementos
    private ConstraintLayout constraintLayoutView;
    private Button btngoogle;
    private Button btnmail;

    //google analytics
    private FirebaseAnalytics mFirebaseAnalytics;
    //Auth firebase
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        event();
        // Google analytics
        runAnalytics();
    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           showHomeUI();
        }
    }
    private void init(){
        btngoogle = findViewById(R.id.button_authGoogle);
        btnmail = findViewById(R.id.button_authMail);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        constraintLayoutView = findViewById(R.id.constraintLayout);


    }
    private void event(){
        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authGoogle();
            }
        });
        btnmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, authActivity.class);
                startActivity(intent);
            }
        });
    }
    private void authGoogle(){
        //Configuracion de goohle signin
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //init firebase auth
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //recuperar cuenta si existe
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //msn("firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //se sale de la seleccion de cuenta
                msn("Google sign in failed");
                msn(e.toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            msn("signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            showHomeUI();
                        } else {
                            msn("signInWithCredential:failure");
                        }
                    }
                });
    }


    private void runAnalytics(){
        Bundle bundle = new Bundle();
        String id = "000";
        String name = "APPLP";
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
    private void showHomeUI(){
        Intent intent = new Intent(this, HomeAppActivity.class);
        startActivity(intent);
    }
    private void msn(String data){
        Snackbar snackbar = Snackbar.make(
                constraintLayoutView,
                data,
                Snackbar.LENGTH_LONG
        );
        snackbar.show();

        //Log.w(TAG, "signInWithCredential:failure", task.getException());
    }

}