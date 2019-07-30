package android_serialport_api.sample;

import android_serialport_api.Login.SSLSocketClient;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SampleFinishActivity extends AppCompatActivity {
    SampleData sampleData;
    private LineChart chart;
    private XAxis xAxis;
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_finish);
        sampleData = (SampleData) getIntent().getSerializableExtra("sampleData");
        Intent intent = getIntent();
        float[] finishData = intent.getFloatArrayExtra("finishData");
        plot(finishData);

        textView = (TextView) findViewById(R.id.txt_sampleFinish);
        String KEY = "Drug";
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        int count = spref.getInt("sampleCount" , 1);
        textView.setText("取樣" + sampleData.getTargetEnglishName() + "，第" + count + "次");



        final Button buttonSamplingClick = (Button)findViewById(R.id.buttonSamplingClick);
        buttonSamplingClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

        final Button buttonCancleSampling = (Button)findViewById(R.id.buttonCancleSampling);
        buttonCancleSampling.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SampleFinishActivity.this , MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });



    }
    private void plot(float[] data)
    {
        chart = findViewById(R.id.sampleFinishChart);

        List<Entry> drug_Entries = new ArrayList<>(); // List to store data-points of sine curve

        for( int i = 0; i < data.length; i ++ ){
            drug_Entries.add(new Entry((float) 950+1550*i/data.length-1, data[i]));
        }

        List<ILineDataSet> dataSets = new ArrayList<>(); // for adding multiple plots

        LineDataSet drug_Set = new LineDataSet(drug_Entries,"street drug curve");
        xAxis = chart.getXAxis();
        leftYAxis = chart.getAxisLeft();
        rightYaxis = chart.getAxisRight();
        rightYaxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        drug_Set.setColor(Color.BLUE);
        drug_Set.setCircleColor(Color.BLUE);
        drug_Set.setCircleRadius(1);
        drug_Set.setFormSize(20);
        dataSets.add(drug_Set);
        chart.getDescription().setEnabled(false);
        chart.setData(new LineData(dataSets));
        chart.invalidate();
    }











    class BackgroundTask extends AsyncTask<Void,Void,String> {
        String json_url;
        @Override
        protected void onPreExecute() {
            json_url = "https://nir.shu.edu.tw/NIRDetection/api/Sample";
        }
        @Override
        protected String doInBackground(Void... voids) {

            String KEY = "Drug";
            String userID = "";
            long connectTimeout = 30;
            long writeTimeout = 30;
            long readTimeout = 30;

            SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
            userID = spref.getString("userID" , "police");
            //String GPSLocate = String.valueOf(longitude) + "," +  String.valueOf(latitude);
            Log.d("OKHttp3","Post Fuction");
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(connectTimeout , TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout , TimeUnit.SECONDS)
                    .readTimeout(readTimeout , TimeUnit.SECONDS)
                    .build();

            RequestBody formBody = new FormBody.Builder()
                    .add("userID", userID)
                    .add("deviceCode", sampleData.getDeviceCode())
                    .add("modelName", sampleData.getModelName())
                    .add("nationalCode", sampleData.getNationalCode())
                    .add("GPSLocation", "null")
                    .add("address", "null")
                    .add("softwareVersion", "V1.0")
                    .add("firmwareVersion", sampleData.getFirmwareVersion())
                    .add("tiSerialNumber" , sampleData.getTiSerialNumber())
                    .add("siSerialNumber" , sampleData.getSiSerialNumber())
                    .add("tiPoint" , sampleData.getTiPoint())
                    .add("siPoint" , sampleData.getSiPoint())
                    .add("detectorTemperature" , sampleData.getDetectorTemperature())
                    .add("temperature", sampleData.getTemperature())
                    .add("humidity", sampleData.getHumidity())
                    .add("sampleLocation" , sampleData.getSampleLocation())
                    .add("targetChineseName" , sampleData.getTargetChineseName())
                    .add("targetEnglishName" , sampleData.getTargetEnglishName())
                    .add("commonName" , sampleData.getCommonName())
                    .add("targetPurity",sampleData.getTargetPurity())
                    .add("rowTi", sampleData.getRowTi())
                    .add("rowSi", sampleData.getRowSi())
                    .add("interpolationTiSi" , sampleData.getInterpolationTiSi())
                    .build();
            Request request = new Request.Builder()
                    .url(json_url)
                    .post(formBody)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                String body = response.body().string();
                if (!response.isSuccessful()) {
                    return null;
                }
                if(!body.equals(""))
                {
                    return body;
                }
                return null;
            }catch (SocketTimeoutException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Socket Timeout", Toast.LENGTH_LONG).show();
                    }
                });
                return e.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            String deleteString = "";
            char abc = '\\' ;
            Log.d("result" , result);
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) != abc) {
                    deleteString += result.charAt(i);
                }
            }
            String newStr = deleteString.substring(1, deleteString.length()-1);
            Log.d("testdeleteString" , newStr);
            try {
                JSONObject jsonObject = new JSONObject(newStr);
                String status = jsonObject.getString("status");
                String description = jsonObject.getString("description");
                Log.d("status" , status);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if(status.equals("1")){
                    Toast.makeText(getApplicationContext(), "錯誤:" + description, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SampleFinishActivity.this , MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if(status.equals("0")){
                    String measuringCode = "";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        measuringCode = jsonArray.getJSONObject(i).getString("Column1");
                    }
                    Toast.makeText(getApplicationContext(), "寫入雲端資料庫成功 流水號為: " + measuringCode , Toast.LENGTH_LONG).show();
                    String KEY = "Drug";
                    SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();
                    int count = spref.getInt("sampleCount" , 1);
                    count++;
                    editor.putInt("sampleCount", count);
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(SampleFinishActivity.this , MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "寫入失敗" + e.toString() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SampleFinishActivity.this , MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

}
