package com.example.proto2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button gravityNav, magneticNav;
    FloatingActionButton DiaFAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gravityNav = (Button) this.findViewById(R.id.gravityBtn);
        magneticNav = (Button) this.findViewById(R.id.magneticBtn);
//        DiaFAP= (FloatingActionButton) this.findViewById(R.id.floatingActionButton);

        gravityNav.setOnClickListener((View.OnClickListener) this);
        magneticNav.setOnClickListener((View.OnClickListener) this);
//        DiaFAP.setOnClickListener((View.OnClickListener)this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
//            case  R.id.add_item :
//                Toast.makeText(this, "1st item clicked", Toast.LENGTH_SHORT).show();
//                break;
            case  R.id.remove_item :
                gotoInstructions();
                break;
            case R.id.about_app:
                FAPDialog();
                break;
        }
        return true;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gravityBtn:
                gotoGravityMain();                //"GRAVITY" btn
                break;
            case R.id.magneticBtn:
                gotoMagneticMain();           //"MAGNETIC" btn
                break;
//            case R.id.floatingActionButton:
//                FAPDialog();       //"FAP" btn
//                break;
//            case R.id.btn_multi_choice:
//                multiChoiceDialog();        //???????????????
//                break;
//            case R.id.btn_custom_dialog:
//                customDialog();             //??????????????????
//                break;
            default:
                break;
        }
    }


    public void FAPDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("About this app");
        builder.setMessage(R.string.main_content);
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "OK clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //??????????????????
//        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                Toast.makeText(MainActivity.this, "Back clicked", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
        //??????????????????
//        builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                Toast.makeText(MainActivity.this, "Neutral clicked", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        AlertDialog dialog = builder.create();

        dialog.show();                              //???????????????
    }

    public void gotoGravityMain() {
        Intent GraMain = new Intent(MainActivity.this, GraHome.class);
        startActivity(GraMain);
    }

    public void gotoMagneticMain() {
        Intent MagMain = new Intent(MainActivity.this, MagHome.class);
        startActivity(MagMain);
    }

    public void gotoInstructions() {
        Intent Instruct = new Intent(MainActivity.this, InstructionPage.class);
        startActivity(Instruct);
    }

}