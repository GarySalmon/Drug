package android_serialport_api.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android_serialport_api.sample.MainMenu;
import android_serialport_api.sample.R;

public class HelpMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_main);



        final Button buttonAppOperate = (Button)findViewById(R.id.ButtonAppOperate);
        buttonAppOperate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // MainMenu.this.finish();

                Intent intentSetup = new Intent(HelpMain.this, AppOperatePDF.class);
//                intentSetup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSetup);
            }
        });

        final Button buttonStandard = (Button)findViewById(R.id.ButtonStandard);
        buttonStandard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // MainMenu.this.finish();

                Intent intentSetup = new Intent(HelpMain.this, StandardSamplingPDF.class);
//                intentSetup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSetup);
            }
        });



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
