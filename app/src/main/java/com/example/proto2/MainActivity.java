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

        gravityNav=(Button)this.findViewById(R.id.gravityBtn);
        magneticNav=(Button)this.findViewById(R.id.magneticBtn);
        DiaFAP= (FloatingActionButton) this.findViewById(R.id.floatingActionButton);

        gravityNav.setOnClickListener((View.OnClickListener)this);
        magneticNav.setOnClickListener((View.OnClickListener)this);
        DiaFAP.setOnClickListener((View.OnClickListener)this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
         * (只会在第一次初始化菜单时调用) Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate( R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
         * method stub
         */
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) //得到被点击的item的itemId
        {
            case  R.id.add_item :  //对应的ID就是在add方法中所设定的Id
                Toast.makeText(this, "1st item clicked", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.remove_item :
                Toast.makeText(this, "2st item clicked", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.about_app :
                FAPDialog();
                break;
        }
        return true;
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.gravityBtn:
                gotoGravityMain();                //"GRAVITY" btn
                break;
            case R.id.magneticBtn:
                gotoMagneticMain();           //"MAGNETIC" btn
                break;
            case R.id.floatingActionButton:
                FAPDialog();       //"FAP" btn
//                break;
//            case R.id.btn_multi_choice:
//                multiChoiceDialog();        //多选对话框
//                break;
//            case R.id.btn_custom_dialog:
//                customDialog();             //自定义对话框
//                break;
            default:
                break;
        }
    }






    public void FAPDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("About this app");
        builder.setMessage("Proto app v0.1 by DarYubel");
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "OK clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Back clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置中立按钮
        builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Neutral clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();                              //显示对话框
    }
    public void gotoGravityMain()
    {
        Intent GraMain = new Intent(MainActivity.this, GraMain.class);
        startActivity(GraMain);
    }
    public void gotoMagneticMain()
    {
        Intent MagMain = new Intent(MainActivity.this, MagMain.class);
        startActivity(MagMain);
    }

}