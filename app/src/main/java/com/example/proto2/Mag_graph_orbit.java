package com.example.proto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import id.web.nanangmaxfi.contourplot.ColorScale;
import id.web.nanangmaxfi.contourplot.Contour2DMap;

public class Mag_graph_orbit extends AppCompatActivity {


    private final Double G = 6.67259*10, pi = Math.PI, mu = 4*pi*Math.pow(10,-7);
    private ImageView drawImageView;

    Double radius, magnetization, depth;
    TextView xV, rV, MV, DV;
    Integer length;
    Float Is = (float) 90;
    Float A = (float) 0;

    Contour2DMap contour2DMap;
    Bitmap bitmap;
    LineChart OrbProfile;

    int[] x, y= null;
    double[][] ha2D, za2D = null;
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
        setContentView(R.layout.activity_mag_graph_orbit);

        //receiving values via getStringExtra(). likewise the received values are String type and
        //should be converted into intended type before utilized.
        length = Integer.valueOf(getIntent().getStringExtra("xLength"));
        radius = Double.valueOf(getIntent().getStringExtra("radius"));
        magnetization = Double.valueOf(getIntent().getStringExtra("magnetization"));
        depth = Double.valueOf(getIntent().getStringExtra("depth"));

        drawImageView = findViewById(R.id.drawImageView);
        bitmap = Bitmap.createBitmap(380,250, Bitmap.Config.ARGB_8888);
        contour2DMap = new Contour2DMap(bitmap,380,250);

        xV=(TextView)this.findViewById(R.id.textView9);
        rV=(TextView)this.findViewById(R.id.textView10);
        MV=(TextView)this.findViewById(R.id.textView11);
        DV=(TextView)this.findViewById(R.id.textView12);

        OrbProfile=(LineChart)this.findViewById(R.id.OrbitProfileLineChart1);


        xV.setText("x length:" + length);
        rV.setText("radius:" + String.valueOf(radius));
        MV.setText("magnetization:" + String.valueOf(magnetization));
        DV.setText("depth:" + String.valueOf(depth));

        x = new int[length];
        y = new int[length];
        for (int i=0; i<length; i++){
            x[i] = -length/2 + i;
            y[i] = -length/2 + i;
        }

        ha = new float[length];
        za = new float[length];
        for (int i=0; i<length; i++){
            ha[i] = (float) ((mu*magnetization*((2*Math.pow(x[i],2)-
                    Math.pow(depth,2))*Math.cos(Is)-3*depth*x[i]*Math.sin(Is)+
                    3*depth*x[i]*Math.sin(Is)))
                    /(4*pi*Math.pow(Math.pow(x[i],2)+Math.pow(depth,2),2.5)));

            za[i] = (float) ((mu*magnetization*((2*Math.pow(depth,2)-
                    Math.pow(x[i],2))*Math.sin(Is)-3*depth*x[i]*Math.cos(Is)))
                    /(4*pi*Math.pow(Math.pow(x[i],2)+Math.pow(depth,2),2.5)));
        }


        ha2D = new double[length][length];
        za2D = new double[length][length];
        for (int i=0; i<length; i++){
            for (int j=0; j<length; j++){
                za2D[i][j] = (double) 23;

            }
        }



        DrawProfile();
        DrawContour();

    }


    public void DrawContour(){
        contour2DMap.setData(za2D);
        contour2DMap.setIsoFactor(1);
        contour2DMap.setInterpolationFactor(1);
        contour2DMap.setMapColorScale(ColorScale.MONOCHROMATIC);
        contour2DMap.draw(drawImageView);
        Log.d("GraGraph","draw");
    }

    public void DrawProfile(){

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
        LineData data = new LineData(dataSets);
        OrbProfile.setData(data);
        OrbProfile.invalidate();


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