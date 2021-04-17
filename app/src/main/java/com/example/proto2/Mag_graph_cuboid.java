package com.example.proto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Mag_graph_cuboid extends AppCompatActivity{


    private final Double G = 6.67259*10, pi = Math.PI, mu = 4*pi*Math.pow(10,-7);

    Double width, magnetization, depth;
    Float Is = (float) 90;
    TextView xV, bV, MV, DV, IsV;
    SeekBar seekBarIs;
    Integer length;
    LineChart OrbProfile;

    int[] x= null;
    float[] ha = null;
    List<Entry> deltaHa = new ArrayList<Entry>();
    float[] za = null;
    List<Entry> deltaZa = new ArrayList<Entry>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate( R.menu.operations , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case  R.id.JPG :
//                QuickShot.of(OrbProfile).setResultListener(this).toJPG().save();
                Toast.makeText(this, "Saved as .jpg in /pictures.", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.PNG :
//                QuickShot.of(OrbProfile).setResultListener(this).toPNG().save();
                Toast.makeText(this, "Saved as .png in /pictures.", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mag_graph_cuboid);

        //receiving values via getStringExtra(). likewise the received values are String type and
        //should be converted into intended type before utilized.
        length = Integer.valueOf(getIntent().getStringExtra("xLength"));
        width = Double.valueOf(getIntent().getStringExtra("width"));
        magnetization = Double.valueOf(getIntent().getStringExtra("magnetization"));
        depth = Double.valueOf(getIntent().getStringExtra("depth"));

        xV=(TextView)this.findViewById(R.id.textView9);
        bV=(TextView)this.findViewById(R.id.textView10);
        MV=(TextView)this.findViewById(R.id.textView11);
        DV=(TextView)this.findViewById(R.id.textView12);
        IsV=(TextView)this.findViewById(R.id.textView13);

        seekBarIs=(SeekBar)this.findViewById(R.id.seekBarIs);
        seekBarIs.setMin(0);
        seekBarIs.setMax(90);

        OrbProfile=(LineChart)this.findViewById(R.id.OrbitProfileLineChart1);


        xV.setText("x length:" + length);
        bV.setText("1/2 width:" + String.valueOf(width));
        MV.setText("magnetization:" + String.valueOf(magnetization));
        DV.setText("depth:" + String.valueOf(depth));

        x = new int[length];
        for (int i=0; i<length; i++){
            x[i] = -length/2 + i;
        }

        ha = new float[length];
        za = new float[length];

        barTracking();
        DrawProfile();

    }

    private void barTracking()
    {
        seekBarIs.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Is = (float) progress;
                IsV.setText("Magnetization inclination: " + progress + "/90");
                DrawProfile();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    public void DrawProfile(){

        LineData data = null;
        if (data != null) {
            OrbProfile.clearValues();
        }


        for (int i=0; i<length; i++){
            za[i] = (float) (mu*magnetization*((Math.pow(depth,2)+Math.pow(width,2)-Math.pow(x[i],2))*
                    Math.sin(Is)-2*depth*x[i]*Math.cos(Is))
                    /(2*pi*(Math.pow(x[i]-width,2)+Math.pow(depth,2))*
                    (Math.pow(x[i]+width,2)+Math.pow(depth,2))));

            ha[i] = (float) (-mu*magnetization*((2*depth*x[i])*Math.sin(Is)+
                    (Math.pow(depth,2)+Math.pow(width,2)-Math.pow(x[i],2))*Math.cos(Is))
                    /(2*pi*(Math.pow(x[i]-width,2)+Math.pow(depth,2))*
                    (Math.pow(x[i]+width,2)+Math.pow(depth,2))));
        }

        for (int i=0; i<length; i++){
            deltaHa.add(new Entry(x[i], ha[i]));
            deltaZa.add(new Entry(x[i], za[i]));
        }

        // 2. 创建一个数据集 DataSet ，用来添加 Entry。一个图中可以包含多个数据集
        LineDataSet set1 = new LineDataSet(deltaHa, "H_a");
        LineDataSet set2 = new LineDataSet(deltaZa, "Z_a");

        // 3. 通过数据集设置数据的样式，如字体颜色、线型、线型颜色、填充色、线宽等属性


        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.RED);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(1f);
        set2.setLineWidth(1f);
        set2.setCircleRadius(1f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);
        set2.setDrawCircleHole(false);

        //使用接口ILineDataSet
        List<ILineDataSet> dataSets = new ArrayList<>() ;
        //添加数据
        dataSets.add(set1);
        dataSets.add(set2);

        // 4.将数据集添加到数据 ChartData 中
        data = new LineData(dataSets);
        OrbProfile.setData(data);


        // 背景色
        OrbProfile.setBackgroundColor(Color.WHITE);

        // 图表的文本描述
        OrbProfile.getDescription().setEnabled(false);

        // 手势设置
        OrbProfile.setTouchEnabled(true);

        // 添加监听器
//        OrbProfile.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        OrbProfile.setDrawGridBackground(false);

//        // 自定义 MarkView，当数据被选择时会展示
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(chart);
//        chart.setMarker(mv);

        // 设置拖拽、缩放等
        OrbProfile.setDragEnabled(true);
        OrbProfile.setScaleEnabled(true);
        OrbProfile.setScaleXEnabled(true);
        OrbProfile.setScaleYEnabled(true);

        // 设置双指缩放
        OrbProfile.setPinchZoom(true);

    }

}