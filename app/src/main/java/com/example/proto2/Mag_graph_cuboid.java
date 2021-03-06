package com.example.proto2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import id.web.nanangmaxfi.contourplot.ColorScale;
import id.web.nanangmaxfi.contourplot.Contour2DMap;

public class Mag_graph_cuboid extends AppCompatActivity{


    private final Double G = 6.67259*10, pi = Math.PI, mu = 4*pi;

    private ImageView drawImageView;
    Double width, magnetization, depth, Is;
    TextView IV, bV, MV, DV;

    Contour2DMap contour2DMap;
    Bitmap bitmap;
    LineChart OrbProfile;

    Integer length = 500;
    Integer meshLength = 50;
    Integer meshDensity = 500/meshLength;

    int[] x= null;
    float[] ha, za = null;
    double[][] Ha2D, Za2D = null;
    List<Entry> deltaHa = new ArrayList<Entry>();
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
        Is = Double.valueOf(getIntent().getStringExtra("is"));
        width = Double.valueOf(getIntent().getStringExtra("width"));
        magnetization = Double.valueOf(getIntent().getStringExtra("magnetization"));
        depth = Double.valueOf(getIntent().getStringExtra("depth"));

        IV=(TextView)this.findViewById(R.id.textView9);
        bV=(TextView)this.findViewById(R.id.textView10);
        MV=(TextView)this.findViewById(R.id.textView11);
        DV=(TextView)this.findViewById(R.id.textView12);

        drawImageView = findViewById(R.id.drawImageView);
        Log.d("Contour", "ImageView initialized");
        bitmap = Bitmap.createBitmap(380,250, Bitmap.Config.ARGB_8888);
        contour2DMap = new Contour2DMap(bitmap,380,250);
        OrbProfile=(LineChart)this.findViewById(R.id.OrbitProfileLineChart1);


        IV.setText("inclination:" + Is);
        bV.setText("1/2 width:" + String.valueOf(width));
        MV.setText("magnetization:" + String.valueOf(magnetization));
        DV.setText("depth:" + String.valueOf(depth));

        x = new int[length];
        for (int i=0; i<length; i++){
            x[i] = -length/2 + i;
        }

        ha = new float[length];
        za = new float[length];
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

        Ha2D = new double[meshLength][meshLength];
        Za2D = new double[meshLength][meshLength];
        for (int i=0; i<meshLength; i++){
            for (int j=0; j<meshLength; j++){
                Ha2D[i][j] = (-mu*magnetization*((2*depth*x[i*meshDensity])*Math.sin(Is)+
                        (Math.pow(depth,2)+Math.pow(width,2)-Math.pow(x[i*meshDensity],2))*Math.cos(Is)))
                        /(2*pi*(Math.pow(x[i*meshDensity]-width,2)+Math.pow(depth,2))*
                        (Math.pow(x[i*meshDensity]+width,2)+Math.pow(depth,2)));
                Za2D[i][j] = (mu*magnetization*((Math.pow(depth,2)+Math.pow(width,2)-
                        Math.pow(x[i*meshDensity],2))*Math.sin(Is)-2*depth*x[i*meshDensity]*Math.cos(Is)))
                        /(2*pi*(Math.pow(x[i*meshDensity]-width,2)+Math.pow(depth,2))*
                        (Math.pow(x[i*meshDensity]+width,2)+Math.pow(depth,2)));
            }
        }

        DrawProfile();
        Toast.makeText(this,String.valueOf(Ha2D[10][10]),Toast.LENGTH_LONG).show();
        DrawContour();
        Log.d("benchmark", "END");

    }

    public void DrawContour(){
        contour2DMap.setData(Ha2D);
        contour2DMap.setIsoFactor(1);
        contour2DMap.setInterpolationFactor(1);
        contour2DMap.setMapColorScale(ColorScale.COLOR);
        contour2DMap.draw(drawImageView);
    }

    public void DrawProfile(){

        LineData data = null;
        if (data != null) {
            OrbProfile.clearValues();
        }


        for (int i=0; i<length; i++){
            deltaHa.add(new Entry(x[i], ha[i]));
            deltaZa.add(new Entry(x[i], za[i]));
        }

        // 2. ????????????????????? DataSet ??????????????? Entry??????????????????????????????????????????
        LineDataSet set1 = new LineDataSet(deltaHa, "H_a");
        LineDataSet set2 = new LineDataSet(deltaZa, "Z_a");

        // 3. ????????????????????????????????????????????????????????????????????????????????????????????????????????????


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

        //????????????ILineDataSet
        List<ILineDataSet> dataSets = new ArrayList<>() ;
        //????????????
        dataSets.add(set1);
        dataSets.add(set2);

        // 4.??????????????????????????? ChartData ???
        data = new LineData(dataSets);
        OrbProfile.setData(data);


        // ?????????
        OrbProfile.setBackgroundColor(Color.WHITE);

        // ?????????????????????
        OrbProfile.getDescription().setEnabled(false);

        // ????????????
        OrbProfile.setTouchEnabled(true);

        // ???????????????
//        OrbProfile.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
        OrbProfile.setDrawGridBackground(false);

//        // ????????? MarkView?????????????????????????????????
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(chart);
//        chart.setMarker(mv);

        // ????????????????????????
        OrbProfile.setDragEnabled(true);
        OrbProfile.setScaleEnabled(true);
        OrbProfile.setScaleXEnabled(true);
        OrbProfile.setScaleYEnabled(true);

        // ??????????????????
        OrbProfile.setPinchZoom(true);

    }

}