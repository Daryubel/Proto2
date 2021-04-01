package com.example.proto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraGraph1 extends AppCompatActivity {

    Double G = 6.67259*10, pi = 3.14159;

    Double radius, density, depth;
    TextView xV, rV, rhoV, DV;
    Integer length;
    LineChart OrbProfile;

    int[] x= null;
    float[] g = null;
    List<Entry> deltaG = new ArrayList<Entry>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra_graph1);

        //receiving values via getStringExtra(). likewise the received values are String type and
        //should be converted into intended type before utilized.
        length = Integer.valueOf(getIntent().getStringExtra("xLength"));
        radius = Double.valueOf(getIntent().getStringExtra("radius"));
        density = Double.valueOf(getIntent().getStringExtra("density"));
        depth = Double.valueOf(getIntent().getStringExtra("depth"));

        xV=(TextView)this.findViewById(R.id.textView9);
        rV=(TextView)this.findViewById(R.id.textView10);
        rhoV=(TextView)this.findViewById(R.id.textView11);
        DV=(TextView)this.findViewById(R.id.textView12);

        OrbProfile=(LineChart)this.findViewById(R.id.OrbitProfileLineChart1);


        xV.setText("x length:" + length);
        rV.setText("radius:" + String.valueOf(radius));
        rhoV.setText("density:" + String.valueOf(density));
        DV.setText("depth:" + String.valueOf(depth));

        x = new int[length];
        for (int i=0; i<length; i++){
            x[i] = -length/2 + i;
        }

        g = new float[length];
        for (int i=0; i<length; i++){
            g[i] = (float) ((G*depth*4*pi*Math.pow(radius,3)/3)
                                        /Math.pow((Math.pow(x[i],2)+Math.pow(depth,2)), 1.5));
        }


        DrawProfile();

    }



    public void DrawProfile(){

        for (int i=0; i<length; i++){
            deltaG.add(new Entry(x[i], g[i]));
        }

        // 2. 创建一个数据集 DataSet ，用来添加 Entry。一个图中可以包含多个数据集
        LineDataSet set1 = new LineDataSet(deltaG, "DataSet 1");

        // 3. 通过数据集设置数据的样式，如字体颜色、线型、线型颜色、填充色、线宽等属性
        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // 4.将数据集添加到数据 ChartData 中
        LineData data = new LineData(set1);

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