package com.example.proto2;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class GraStairs extends Fragment implements View.OnClickListener{

    private final Double G = 6.67259*10, pi = 3.14159, mu = 4*pi*Math.pow(10,-7);

    View view = null;

    TextView tv_central, tv_length, barprogress, fieldLength;;
    EditText value_o_height,value_o_density,value_o_Depth;
    Button calBtn5, calBtn6;
    SeekBar seekBar, lengthBar;
    Double height, density, depth;
    Integer length;

    public GraStairs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.activity_gra_stairs, container, false);
        }


        calBtn5=(Button)view.findViewById(R.id.calButton5);
        calBtn6=(Button)view.findViewById(R.id.calButton6);

        value_o_height=(EditText)view.findViewById(R.id.Textinput_o_width);
        value_o_density=(EditText)view.findViewById(R.id.Textinput_o_density);
        value_o_Depth=(EditText)view.findViewById(R.id.Textinput_o_Depth);

        tv_length=(TextView)view.findViewById(R.id.Textinput_o_Is);
        tv_central=(TextView)view.findViewById(R.id.textView6);
        barprogress=(TextView)view.findViewById(R.id.textView7);
        fieldLength=(TextView)view.findViewById(R.id.textView8);

        seekBar = (SeekBar)view.findViewById(R.id.seekBar1);
        seekBar.setMin(10);
        seekBar.setMax(100);

        lengthBar = (SeekBar)view.findViewById(R.id.seekBar2);
        lengthBar.setMin(10);
        lengthBar.setMax(500);

        calBtn5.setOnClickListener((View.OnClickListener) this);
        calBtn6.setOnClickListener((View.OnClickListener) this);

        barTracking();


        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","onCreate execute");
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
                Toast.makeText(getContext(), "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Releasing SeekBar", Toast.LENGTH_SHORT).show();
            }
        });

        lengthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar lengthBar, int progress, boolean fromUser) {
                fieldLength.setText("Length: " + progress + "/500 ");
                tv_length.setText(String.valueOf(progress));
                length=Integer.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Releasing SeekBar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.calButton5:
                calculate();           // Show some vital values
                break;
            case R.id.calButton6:
                DrawGraph();       // Show the charts
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
        String inputText1=value_o_height.getText().toString();  //Convert
        String inputText2=value_o_density.getText().toString();
        String inputText3=value_o_Depth.getText().toString();
        Double out_o_central;
        String Peak;

        height=Double.valueOf(inputText1);
        density=Double.valueOf(inputText2);
        depth=Double.valueOf(inputText3);

        out_o_central=pi*G*density*height;

        Peak=String.valueOf(out_o_central);   //Convert integer to String. Works for all types of values.
        tv_central.setText(Peak);
        Toast.makeText(getContext(), "Calculation Complete", Toast.LENGTH_SHORT).show();
    }

    public void DrawGraph()
    {
        calculate();
        Intent gra_graph = new Intent(getActivity(), Gra_graph_stairs.class);


        //putExtra() applies ONLY to String type and therefore the double or integer type must
        //first be converted into String type if wished to be transmitted amongst activities.
        gra_graph.putExtra("xLength", String.valueOf(length));
        gra_graph.putExtra("height", String.valueOf(height));
        gra_graph.putExtra("density", String.valueOf(density));
        gra_graph.putExtra("depth", String.valueOf(depth));

        startActivity(gra_graph);
    }


}