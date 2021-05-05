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

public class MagCuboid extends Fragment implements View.OnClickListener{

    private final Double G = 6.67259*10, pi = 3.14159, mu = 4*pi*Math.pow(10,-7);

    View view = null;

    TextView tv_peak, tv_length, barprogress;
    EditText value_o_width,value_o_magnetization,value_o_Depth, value_o_Is;
    Button calBtn3, calBtn4;
    SeekBar depthBar, angleBar;
    Double width, magnetization, depth, Is;
    Integer length;

    public MagCuboid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.activity_mag_cuboid, container, false);
        }

        calBtn3=(Button)view.findViewById(R.id.calButton5);
        calBtn4=(Button)view.findViewById(R.id.calButton6);

        value_o_width=(EditText)view.findViewById(R.id.Textinput_o_width);
        value_o_magnetization=(EditText)view.findViewById(R.id.Textinput_o_density);
        value_o_Depth=(EditText)view.findViewById(R.id.Textinput_o_Depth);
        value_o_Is= (EditText) view.findViewById(R.id.Textinput_o_Is);

        tv_length=(TextView)view.findViewById(R.id.Textinput_o_Is);
        tv_peak=(TextView)view.findViewById(R.id.textView6);
        barprogress=(TextView)view.findViewById(R.id.textView7);

        depthBar = (SeekBar)view.findViewById(R.id.seekBar1);
        depthBar.setMin(1);
        depthBar.setMax(30);

        angleBar = (SeekBar)view.findViewById(R.id.seekBar2);
        angleBar.setMin(0);
        angleBar.setMax(90);

        calBtn3.setOnClickListener((View.OnClickListener) this);
        calBtn4.setOnClickListener((View.OnClickListener) this);

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
        depthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                barprogress.setText("Depth: " + progress + "/30 ");
                value_o_Depth.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getContext(), "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getContext(), "Releasing SeekBar", Toast.LENGTH_SHORT).show();
            }
        });

        angleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar lengthBar, int progress, boolean fromUser) {
                value_o_Is.setText("Magnetic Inclination angle: " + progress + "/90 ");
                tv_length.setText(String.valueOf(progress));
                length=Integer.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getContext(), "Touching SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getContext(), "Releasing SeekBar", Toast.LENGTH_SHORT).show();
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
        String inputText1=value_o_width.getText().toString();  //Convert
        String inputText2=value_o_magnetization.getText().toString();
        String inputText3=value_o_Depth.getText().toString();
        String inputText4=value_o_Is.getText().toString();
        Double out_o_peak;
        String Peak;

        width=Double.valueOf(inputText1);
        magnetization=Double.valueOf(inputText2)*10e3;
        depth=Double.valueOf(inputText3);
        Is=Double.valueOf(inputText4);
        Is=(Is*pi)/180;

        out_o_peak=(4/3)*pi;

        Peak=String.valueOf(out_o_peak);   //Convert integer to String. Works for all types of values.
        tv_peak.setText(Peak);
        Toast.makeText(getContext(), "Calculation Complete", Toast.LENGTH_SHORT).show();
    }

    public void DrawGraph()
    {
        calculate();
        Intent mag_graph = new Intent(getActivity(), Mag_graph_cuboid.class);


        //putExtra() applies ONLY to String type and therefore the double or integer type must
        //first be converted into String type if wished to be transmitted amongst activities.
        mag_graph.putExtra("is", String.valueOf(Is));
        mag_graph.putExtra("width", String.valueOf(width));
        mag_graph.putExtra("magnetization", String.valueOf(magnetization));
        mag_graph.putExtra("depth", String.valueOf(depth));

        startActivity(mag_graph);
    }


}