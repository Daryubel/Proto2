package com.example.proto2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GraMain extends AppCompatActivity implements View.OnClickListener{

    Double G = 6.67259*10, pi = 3.14159;

    TextView tv_peak, tv_length, barprogress, fieldLength;;
    EditText value_o_Radius,value_o_Density,value_o_Depth;
    Button calBtn, calBtn2;
    Context mContext;
    SeekBar seekBar, lengthBar;
    Double radius, density, depth;
    Integer length;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra_main);
        Log.d("MainActivity","onCreate execute");

        calBtn=(Button)this.findViewById(R.id.calButton);
        calBtn2=(Button)this.findViewById(R.id.calButton2);

        value_o_Radius=(EditText)this.findViewById(R.id.Textinput_o_Radius);
        value_o_Density=(EditText)this.findViewById(R.id.Textinput_o_Density);
        value_o_Depth=(EditText)this.findViewById(R.id.Textinput_o_Depth);

        tv_length=(TextView)this.findViewById(R.id.textView4);
        tv_peak=(TextView)this.findViewById(R.id.textView6);
        barprogress=(TextView)this.findViewById(R.id.textView7);
        fieldLength=(TextView)this.findViewById(R.id.textView8);

        seekBar = (SeekBar)this.findViewById(R.id.seekBar1);
        seekBar.setMin(10);
        seekBar.setMax(100);

        lengthBar = (SeekBar)this.findViewById(R.id.seekBar2);
        lengthBar.setMin(50);
        lengthBar.setMax(500);

        calBtn.setOnClickListener((View.OnClickListener) this);
        calBtn2.setOnClickListener((View.OnClickListener) this);

        mContext = GraMain.this;

        barTracking();

    }




    //Void, just a function running background and is started in Protected void onCreate
    private void barTracking()
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                barprogress.setText("Depth: " + progress + "/100 ");
                value_o_Depth.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mContext, "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mContext, "Releasing SeekBar", Toast.LENGTH_SHORT).show();
            }
        });

        lengthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar lengthBar, int progress, boolean fromUser) {
                fieldLength.setText("Length: " + progress + "/50 ");
                tv_length.setText(String.valueOf(progress));
                length=Integer.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mContext, "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(mContext, "Releasing SeekBar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.calButton:
                calculate();           // Show some vital values
                break;
            case R.id.calButton2:
                graGraph1();       // Show the charts
                break;
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


    //Functions that are called by click
    public void calculate()
    {
        String inputText1=value_o_Radius.getText().toString();  //Convert
        String inputText2=value_o_Density.getText().toString();
        String inputText3=value_o_Depth.getText().toString();
        Double out_o_peak;
        String Peak;

        radius=Double.valueOf(inputText1);
        density=Double.valueOf(inputText2);
        depth=Double.valueOf(inputText3);

        out_o_peak=(4/3)*pi*radius*radius*radius*density*G/(depth*depth);

        Peak=String.valueOf(out_o_peak);   //Convert integer to String. Works for all types of values.
        tv_peak.setText(Peak);
        Toast.makeText(mContext, "Calculation Complete", Toast.LENGTH_SHORT).show();
    }

    public void graGraph1()
    {
        calculate();
        Intent GraGraph1 = new Intent(GraMain.this, GraGraph1.class);


        //putExtra() applies ONLY to String type and therefore the double or integer type must
        //first be converted into String type if wished to be transmitted amongst activities.
        GraGraph1.putExtra("xLength", String.valueOf(length));
        GraGraph1.putExtra("radius", String.valueOf(radius));
        GraGraph1.putExtra("density", String.valueOf(density));
        GraGraph1.putExtra("depth", String.valueOf(depth));

        startActivity(GraGraph1);
    }


}


