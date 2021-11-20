package com.lapiragua.applapiragua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de La Piragua App?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    public  boolean onCreateOptionsMenu(Menu mi_menu){
        getMenuInflater().inflate(R.menu.menu_home, mi_menu);

        return true;
    }
    /*
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.item_Home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                Init_activityClass2();
                return true;
            case R.id.item_Config:
                Toast.makeText(this, "config", Toast.LENGTH_SHORT).show();
                Init_activityClass2_1();
                return true;
            case R.id.item_About:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                Init_activityClass2_2();
                return true;
        }
        return true;
    }
    public void Init_activityClass2(){
        Intent intent = new Intent(this, clase_2.class);
        startActivity(intent);
    }

    public void Init_activityClass2_1(){
        Intent intent = new Intent(this, clase_2_1.class);
        startActivity(intent);
    }
    public void Init_activityClass2_2(){
        Intent intent = new Intent(this, clase_2_2.class);
        startActivity(intent);
    }

     */

}